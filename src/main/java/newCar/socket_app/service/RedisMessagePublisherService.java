package newCar.socket_app.service;

import lombok.RequiredArgsConstructor;
import newCar.socket_app.model.ChatMessage;
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
        System.out.println("publish to topic : " + topic);
        System.out.println("message : " + message);
        redisTemplate.convertAndSend(topic, message);
    }
}
