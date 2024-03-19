
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Producer extends Thread{
  private final Buffer buffer;
  private final  ReentrantLock lock;
  private final Condition condition;
    public Producer(Buffer buffer, ReentrantLock lock, Condition condition) {
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
             int[] NewArray = buffer.getMyArray();
             if (currentIndex != buffer.getMyArray().length - 1) {
                 NewArray[currentIndex] = (int) (Math.random() * 30);
                 buffer.addIndex();
                 System.out.println("Add value" + NewArray[currentIndex]);
                 condition.signal();
             }  else {
                 condition.await();
             }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        };
    }
}
