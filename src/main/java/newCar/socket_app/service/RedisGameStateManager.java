package newCar.socket_app.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class RedisGameStateManager implements GameStateService {

    private final RedisTemplate<String, String> stringRedisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    private final String redisGameStateKeyString = "GameState";

    @PostConstruct
    public void init() {
        Boolean isExist = stringRedisTemplate.hasKey(redisGameStateKeyString);
        if (isExist != null && isExist) {
            return;
        }

        Map<String, String> gameState = new HashMap<>();
        gameState.put(Team.PET.getCode(), "0");
        gameState.put(Team.TRAVEL.getCode(), "0");
        gameState.put(Team.SPACE.getCode(), "0");
        gameState.put(Team.LEISURE.getCode(), "0");

        stringRedisTemplate.opsForHash().putAll(redisGameStateKeyString, gameState);
    }

    @Override
    public void updateGameState(GameData gameData) {
        Arrays.stream(Team.values())
                .filter(team -> gameData.getGameData(team) > 0L)
                .forEach(team -> stringRedisTemplate.opsForHash().increment("GameState", team.getCode(), gameData.getGameData(team)));
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void pushGameState() {
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(redisGameStateKeyString);

        GameState gameState = new GameState();

        Arrays.stream(Team.values())
                .forEach(team -> {
                    // 가져온 엔트리에서 해당 팀의 값을 문자열로 가져온 후 Long으로 변환
                    String valueStr = (String) entries.get(team.getCode());
                    if (valueStr != null) {
                        Long value = Long.valueOf(valueStr);
                        gameState.setGameState(team, value);
                    }
                });

        // 메시지 브로커로 gameState 전송
        messagingTemplate.convertAndSend("/topic/game", gameState);
    }
}
