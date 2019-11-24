package cn.glenn.thread.customlock;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author: glenn wang
 * @date: 2019-11-23 21:41
 **/
public class LockTest {
    public static void main(String[] args) {
        final BooleanLock lock = new BooleanLock();
        Stream.of("T1", "T2", "T3", "T4").forEach(t -> {
            new Thread(() -> {
                try {
                    lock.lock(2000L);
                    Optional.of(Thread.currentThread().getName() + " have an lock monitor")
                            .ifPresent(System.out::println);
                    work();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Lock.TimeOutException e) {
                    Optional.of("time out").ifPresent(System.out::println);
                } finally {
                    lock.unlock();
                }
            }, t).start();
        });
    }

    private static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is working...")
                .ifPresent(System.out::println);
        Thread.sleep(1000);
    }
}
