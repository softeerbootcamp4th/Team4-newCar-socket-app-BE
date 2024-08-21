package newCar.socket_app.service;

public interface DistributedLockService {
    public boolean getLock(String lockName);
    public void releaseLock(String lockName);
}
