package newCar.socket_app.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FixedSizeQueue<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize;

    public FixedSizeQueue(int maxSize) {
        super(maxSize, 0.75f, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }

    @Override
    public synchronized V put(K key, V value) {
        return super.put(key, value);
    }
}
