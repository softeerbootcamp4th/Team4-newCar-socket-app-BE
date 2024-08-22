package newCar.socket_app.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixedSizeCacheTest {

    private FixedSizeCache<Integer, String> cache;
    private int MAX_SIZE = 30;
    private int LAST_VALUE = 100000;

    @BeforeEach
    void setUp() {
        cache = new FixedSizeCache<>(MAX_SIZE);
        for(int i = 0; i < LAST_VALUE; i++){
            cache.put(i, String.valueOf(i));
        }
    }

    @Test
    @DisplayName("사이즈는 항상 일정하게 유지되어야한다.")
    public void cacheSizeTest(){
        assertEquals(MAX_SIZE, cache.size());
    }

    @Test
    @DisplayName("삭제 기준은 가장 오래된 원소이다.")
    public void cacheRemoveTest(){
        assertEquals(String.valueOf(LAST_VALUE - MAX_SIZE), cache.peek());
    }

}