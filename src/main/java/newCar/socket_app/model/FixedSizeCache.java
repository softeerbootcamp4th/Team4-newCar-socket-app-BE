package newCar.socket_app.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class FixedSizeCache<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize;

    public FixedSizeCache(int maxSize) {
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

    public V peek(){
        if (!isEmpty()) {
            K firstKey = entrySet().iterator().next().getKey();
            return super.get(firstKey);
        }
        return null;
    }


}
