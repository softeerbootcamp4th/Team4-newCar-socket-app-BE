package newCar.socket_app.service.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newCar.socket_app.model.chat.ChatMessage;
import newCar.socket_app.model.chat.Message;
import newCar.socket_app.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class BufferedMessageServiceImpl implements BufferedMessageService {

    private final int BUFFER_SIZE = 30; // 버퍼 사이즈만큼 차면 배치 저장
    private final ChatMessageRepository chatMessageRepository;

    private final BlockingQueue<ChatMessage> chatMessageQueue = new LinkedBlockingQueue<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void addMessage(String message) {
        try {
            ChatMessage chatMessage = objectMapper.convertValue(message, ChatMessage.class);
            chatMessageQueue.add(chatMessage);

            if(chatMessageQueue.size() > BUFFER_SIZE) {
                flushBuffer();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void flushBuffer() {

    }

    @Override
    public BlockingQueue<ChatMessage> getChatMessages() {
        return chatMessageQueue;
    }
}
