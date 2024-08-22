package newCar.socket_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisDistributedLockService implements DistributedLockService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final long LOCK_EXPIRATION_TIME = 5_000; //5 seconds

    @Override
    public boolean getLock(String lockName) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForValue()
                        .setIfAbsent(lockName, "locked", LOCK_EXPIRATION_TIME, TimeUnit.MILLISECONDS)
        );
    }

    @Override
    public void releaseLock(String lockName) {
        redisTemplate.delete(lockName);
    }
}
