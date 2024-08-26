package newCar.socket_app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RedisDistributedLockServiceTest {
    private RedisDistributedLockService redisDistributedLockService;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        redisDistributedLockService = new RedisDistributedLockService(redisTemplate);
    }

    @Test
    void testGetLock_whenLockIsAvailable_shouldReturnTrue() {
        // Arrange
        String lockName = "testLock";
        when(valueOperations.setIfAbsent(lockName, "locked", 5000, TimeUnit.MILLISECONDS)).thenReturn(true);

        // Act
        boolean result = redisDistributedLockService.getLock(lockName);

        // Assert
        assertTrue(result);
        verify(valueOperations, times(1)).setIfAbsent(lockName, "locked", 5000, TimeUnit.MILLISECONDS);
    }

    @Test
    void testGetLock_whenLockIsNotAvailable_shouldReturnFalse() {
        // Arrange
        String lockName = "testLock";
        when(valueOperations.setIfAbsent(lockName, "locked", 5000, TimeUnit.MILLISECONDS)).thenReturn(false);

        // Act
        boolean result = redisDistributedLockService.getLock(lockName);

        // Assert
        assertFalse(result);
        verify(valueOperations, times(1)).setIfAbsent(lockName, "locked", 5000, TimeUnit.MILLISECONDS);
    }

    @Test
    void testReleaseLock_shouldDeleteLock() {
        // Arrange
        String lockName = "testLock";

        // Act
        redisDistributedLockService.releaseLock(lockName);

        // Assert
        verify(redisTemplate, times(1)).delete(lockName);
    }

}