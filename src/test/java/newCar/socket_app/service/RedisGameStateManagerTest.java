package newCar.socket_app.service;

import newCar.socket_app.model.GameData;
import newCar.socket_app.model.Team;
import newCar.socket_app.service.game.RedisGameStateManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class RedisGameStateManagerTest {
    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @InjectMocks
    private RedisGameStateManager gameStateService; // 실제 테스트할 서비스 클래스

    private final String redisGameStateKeyString = "GameState";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    public void testUpdateGameState() {
        // Mock GameData 객체 생성
        GameData gameData = mock(GameData.class);
        when(gameData.getGameData(Team.PET)).thenReturn(3L);
        when(gameData.getGameData(Team.TRAVEL)).thenReturn(0L);
        when(gameData.getGameData(Team.SPACE)).thenReturn(2L);
        when(gameData.getGameData(Team.LEISURE)).thenReturn(0L);

        // 실제 테스트 수행
        gameStateService.updateGameState(gameData);

        // 검증
        verify(hashOperations).increment("GameState", Team.PET.getCode(), 3L);
        verify(hashOperations).increment("GameState", Team.SPACE.getCode(), 2L);
        verify(hashOperations, never()).increment("GameState", Team.TRAVEL.getCode(), 0L);
        verify(hashOperations, never()).increment("GameState", Team.LEISURE.getCode(), 0L);
    }

}