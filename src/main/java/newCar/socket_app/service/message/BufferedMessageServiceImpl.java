package newCar.socket_app.service.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newCar.socket_app.model.FixedSizeCache;
import newCar.socket_app.model.chat.ChatMessage;
import newCar.socket_app.model.entity.ChatMessageEntity;
import newCar.socket_app.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BufferedMessageServiceImpl implements BufferedMessageService {

    private final ChatMessageRepository chatMessageRepository;

    private final int BUFFER_SIZE = 10; // 버퍼 사이즈만큼 차면 배치 저장
    private final int HISTORY_SIZE = 30;

    private final BlockingQueue<ChatMessage> chatMessageSaveQueue = new LinkedBlockingQueue<>();

    private final LinkedHashMap<String, ChatMessage> chatMessageHistory = new FixedSizeCache<>(HISTORY_SIZE);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void addMessage(String message) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);

            chatMessageSaveQueue.add(chatMessage);
            chatMessageHistory.put(chatMessage.getId(), chatMessage);

            if(chatMessageSaveQueue.size() > BUFFER_SIZE) {
                flushBuffer();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void flushBuffer() {
        ArrayList<ChatMessage> list = new ArrayList<>();
        chatMessageSaveQueue.drainTo(list);

        chatMessageRepository.saveAll(
                list.stream().map(ChatMessageEntity::from).collect(Collectors.toList())
        );
    }

    @Override
    public ArrayList<ChatMessage> getChatMessages() {
        return new ArrayList<>(chatMessageHistory.values());
    }
}
