package cn.glenn.thread;

import java.util.stream.Stream;

/**
 * @author: wxg
 * @date: 2019-11-23 20:44
 *
 * Thread.sleep()和LOCk.wait()区别？
 * 1。sleep是Thread类里面的方法，wait是Object类里面的方法
 * 2。sleep不需要synchronized，wait需要synchronized包裹
 * 3。sleep不会释放锁资源，wait会释放锁资源
 * 4。sleep不需要wakeup，wait需要wakeup队列(执行wait后，线程会被加入同步队列，等待被唤醒，同时释放锁资源)
 **/
public class DifferenceOfWaitAndSleep {

    private final static Object LOCK = new Object();

    public static void main(String[] args) {
        Stream.of("T1", "T2").forEach(t -> {
            new Thread(() -> {
                DifferenceOfWaitAndSleep.m2();
            }, t).start();
        });
    }

    public static void m1() {
        synchronized (LOCK) {
            try {
                System.out.println("The Thread " + Thread.currentThread().getName() + " enter.");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m2() {
        synchronized (LOCK) {
            try {
                System.out.println("The Thread " + Thread.currentThread().getName() + " enter.");
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
