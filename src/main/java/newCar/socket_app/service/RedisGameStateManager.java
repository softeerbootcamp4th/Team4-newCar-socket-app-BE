package newCar.socket_app.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import newCar.socket_app.model.GameData;
import newCar.socket_app.model.GameState;
import newCar.socket_app.model.Team;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisGameStateManager implements GameStateService {

    private final RedisTemplate<String, String> gameStateRedisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    private final String redisGameStateKeyString = "GameState";

    @PostConstruct
    public void init() {
        Boolean isExist = gameStateRedisTemplate.hasKey(redisGameStateKeyString);
        if (isExist != null && isExist) {
            return;
        }

        Map<String, String> gameState = new HashMap<>();
        gameState.put(Team.PET.getCode(), "1");
        gameState.put(Team.TRAVEL.getCode(), "1");
        gameState.put(Team.SPACE.getCode(), "0");
        gameState.put(Team.LEISURE.getCode(), "0");

        gameStateRedisTemplate.opsForHash().putAll(redisGameStateKeyString, gameState);
    }

    @Override
    public void updateGameState(GameData gameData) {
        Arrays.stream(Team.values())
                .filter(team -> gameData.getGameData(team) > 0L)
                .forEach(team -> gameStateRedisTemplate.opsForHash().increment("GameState", team.getCode(), gameData.getGameData(team)));
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void pushGameState() {
        Map<String, String> entries = (Map<String, String>) (Map) gameStateRedisTemplate.opsForHash().entries(redisGameStateKeyString);

        GameState gameState = new GameState();

        Arrays.stream(Team.values())
                .forEach(team -> {
                    String valueStr = entries.get(team.getCode());
                    if (valueStr != null) {
                        Long value = Long.valueOf(valueStr);
                        gameState.setGameState(team, value);
                    }
                });

        // 메시지 브로커로 gameState 전송
        messagingTemplate.convertAndSend("/topic/game", gameState);
    }
}
