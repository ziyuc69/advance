package cn.glenn.thread;

import java.util.stream.Stream;

/**
 * @author: wxg
 * @date: 2019-11-23 20:15
 * 生产者消费者模式：生产者和消费者多个线程
 **/
public class MulThreadProductConsumer {

    private int i = 0;

    final Object LOCK = new Object();

    private volatile boolean isProduced = false;

    public void produce() {
        synchronized (LOCK) {
            while (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            i++;
            System.out.println("p->" + i);
            LOCK.notifyAll();
            isProduced = true;
        }
    }

    public void consume() {
        synchronized (LOCK) {
            while (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("C->" + i);
            isProduced = false;
            LOCK.notifyAll();
        }
    }

    public static void main(String[] args) {
        MulThreadProductConsumer pc = new MulThreadProductConsumer();
        Stream.of("P1", "P2").forEach(p -> {
            new Thread(() -> {
                while (true) {
                    pc.produce();
                }
            }, p).start();
        });

        Stream.of("C1", "C2").forEach(c -> {
            new Thread(() -> {
                while (true) {
                    pc.consume();
                }
            }).start();
        });
    }
}
