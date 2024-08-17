package newCar.socket_app.service.message;

import lombok.RequiredArgsConstructor;
import newCar.socket_app.model.chat.ChatMessage;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class RedisMessagePublisherService implements MessagePublisherService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void publish(String topic, ChatMessage message) {
        redisTemplate.convertAndSend(topic, message);
    }
}
