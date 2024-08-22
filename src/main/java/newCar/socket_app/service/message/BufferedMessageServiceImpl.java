package newCar.socket_app.service.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newCar.socket_app.model.FixedSizeCache;
import newCar.socket_app.model.chat.BlockMessage;
import newCar.socket_app.model.chat.ChatMessage;
import newCar.socket_app.model.chat.Message;
import newCar.socket_app.model.chat.NoticeMessage;
import newCar.socket_app.model.entity.ChatMessageEntity;
import newCar.socket_app.model.entity.NoticeMessageEntity;
import newCar.socket_app.repository.ChatMessageRepository;
import newCar.socket_app.repository.NoticeMessageRepository;
import newCar.socket_app.service.DistributedLockService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BufferedMessageServiceImpl implements BufferedMessageService {
    private final int CHAT_BATCH_TRIGGER_SIZE = 10;
    private final int NOTICE_BATCH_TRIGGER_SIZE = 1;
    private final int CHAT_HISTORY_SIZE = 30;
    private final String BATCH_STORE_LOCK = "BATCH_STORE_LOCK";

    private final ChatMessageRepository chatMessageRepository;
    private final NoticeMessageRepository noticeMessageRepository;

    private final BlockingQueue<ChatMessage> chatMessageBatchQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<NoticeMessage> noticeMessageBatchQueue = new LinkedBlockingQueue<>();
    private final FixedSizeCache<String, ChatMessage> chatMessageHistory = new FixedSizeCache<>(CHAT_HISTORY_SIZE);
    private NoticeMessage recentNoticeMessage;

    private final DistributedLockService distributedLockService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void addMessage(String channel, String message) {
        switch (channel) {
            case "/topic/chat":
                handleChatMessage(message);
                break;
            case "/topic/notice":
                handleNoticeMessage(message);
                break;
            case "/topic/block":
                handleBlockMessage(message);
                break;
            default:
                break;
        }
    }

    @PostConstruct
    @Override
    public void fetchHistory() {
        /**** Start Fetching Chat History *****/
        Pageable pageable = PageRequest.of(0, CHAT_HISTORY_SIZE);

        List<ChatMessageEntity> list = chatMessageRepository.findAllByOrderByIdDesc(pageable);
        Collections.reverse(list);

        list.stream()
                .map(ChatMessage::from)
                .forEach(M -> chatMessageHistory.put(M.getId(), M));
        /**** END Fetching Chat History *****/

        /**** Start Fetching Recent Notice *****/
        NoticeMessageEntity recentNotice = noticeMessageRepository.findTopByOrderByIdDesc();
        if(recentNotice != null) {
            recentNoticeMessage = NoticeMessage.from(noticeMessageRepository.findTopByOrderByIdDesc());
        }
        /**** END Fetching Recent Notice *****/
    }

    @Override
    public ArrayList<Message> getChatHistory() {
        ArrayList<Message> history = new ArrayList<>(chatMessageHistory.values());
        if(recentNoticeMessage != null){
            history.add(recentNoticeMessage);
        } //채팅 히스토리 리스트의 마지막 원소는 최신 공지사항이다.
        return history;
    }

    private void handleChatMessage(String message){
        try {
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);

            chatMessageBatchQueue.add(chatMessage);
            chatMessageHistory.put(chatMessage.getId(), chatMessage);

            if(chatMessageBatchQueue.size() >= CHAT_BATCH_TRIGGER_SIZE) {
                flushBuffer(chatMessageBatchQueue, chatMessageRepository, ChatMessageEntity::from);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
    private void handleNoticeMessage(String message) {
        try {
            NoticeMessage noticeMessage = objectMapper.readValue(message, NoticeMessage.class);

            recentNoticeMessage = noticeMessage;
            noticeMessageBatchQueue.add(noticeMessage);

            if(noticeMessageBatchQueue.size() >= NOTICE_BATCH_TRIGGER_SIZE) {
                flushBuffer(noticeMessageBatchQueue, noticeMessageRepository, NoticeMessageEntity::from);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    private void handleBlockMessage(String message) {
        try{
            BlockMessage blockMessage = objectMapper.readValue(message, BlockMessage.class);
            String chatMessageId = blockMessage.getBlockId();
            chatMessageHistory.remove(chatMessageId);
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }

    public <T, E> void flushBuffer(BlockingQueue<T> batchQueue, JpaRepository<E, ?> repository, Function<T, E> converter) {
        ArrayList<T> list = new ArrayList<>();
        batchQueue.drainTo(list);

        if (distributedLockService.getLock(BATCH_STORE_LOCK)){
            try {
                repository.saveAll(
                        list.stream().map(converter).collect(Collectors.toList())
                );
            } finally {
                distributedLockService.releaseLock(BATCH_STORE_LOCK);
            }
        }
    }

}
