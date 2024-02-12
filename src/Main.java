import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;
/*
Напишите многопоточный ограниченный буфер с использованием
ReentrantLock.
3. Напишите многопоточный ограниченный буфер с использованием
synchronized

 */
public class Main {
    int capacity = 7;
    LinkedBlockingQueue<Integer> buffer = new LinkedBlockingQueue<Integer>(capacity);
    Lock lock = new ReentrantLock();
    public void play () {
        ExecutorService executor = Executors.newCachedThreadPool();

        IntStream.range(0, 3).boxed()
                .map(i -> producer)
                .forEach(executor::submit);

        IntStream.range(0, 3).boxed()
                .map(i -> consumer)
                .forEach(executor::submit);
        try {
            executor.awaitTermination(capacity, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
        Runnable producer = () -> {
            lock.lock();

            Random r = new Random();
            while (true) {
                try {
                    int item = (int) (Math.random()*100);
                    System.out.println("Produced: " + item);
                    buffer.put(item);
                    Thread.sleep(1000 + r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    lock.unlock();
                }

            }

        };

        Runnable consumer = () -> {
            lock.lock();
            while (true) {
                try {
                    Integer consumed = buffer.take();
                    System.out.println("Consumed: " + consumed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    lock.unlock();
                }
            }

        };
    public static void main(String[] args) {
        new Main().play();
    }
}

