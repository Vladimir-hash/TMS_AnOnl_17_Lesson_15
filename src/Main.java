import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.interrupted;

/*
Напишите многопоточный ограниченный буфер с использованием
ReentrantLock.
3. Напишите многопоточный ограниченный буфер с использованием
synchronized

 */
public class Main {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition newCondition = lock.newCondition();

        Buffer buffer = new Buffer();
        new Producer(buffer, lock, newCondition).start();
        new Consumer(buffer, lock, newCondition).start();
    }
}

