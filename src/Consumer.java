import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class Consumer extends Thread {
    private final Buffer buffer;
    private final ReentrantLock lock;
    private final Condition condition;

    public Consumer(Buffer buffer, ReentrantLock lock, Condition condition) {
        this.buffer = buffer;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (!interrupted() && isAlive()) {
            lock.lock();
            try{
                int currentIndex = buffer.getCurrentIndex();
                if (currentIndex != 0) {
                    buffer.removeIndex();
                    int value = buffer.getMyArray()[currentIndex];
                    System.out.println("Receive number: " + value);
                    condition.signal();
                } else {
                    condition.await();
                }
            } catch (InterruptedException e) {
                throw  new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }
}
