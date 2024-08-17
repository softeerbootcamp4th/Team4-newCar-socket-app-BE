package newCar.socket_app.service.message;

import lombok.RequiredArgsConstructor;
import newCar.socket_app.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BufferedMessageServiceImpl implements BufferedMessageService {

    private final int BUFFER_SIZE = 100; // 버퍼 사이즈만큼 차면 배치 저장
    private final ChatMessageRepository chatMessageRepository;

    //private final BlockingQueue<ChatMessage> chatMessageQueue = new LinkedBlockingQueue<>(BUFFER_SIZE);

    @Override
    public void addMessage(String message) {

    }

    @Override
    public void flushBuffer() {

    }
}
