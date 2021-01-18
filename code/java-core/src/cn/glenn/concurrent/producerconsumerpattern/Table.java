package cn.glenn.concurrent.producerconsumerpattern;

import java.util.Optional;

/**
 * @author: glenn wang
 * @date: 2019-11-27 14:26
 **/
public class Table {

    private String[] buffer;

    private int tail;

    private int head;

    private int count;

    public Table(int count) {
        this.buffer = new String[count];
        this.count = 0;
        this.tail = 0;
        this.head = 0;
    }

    public synchronized void put(String cake) throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " puts " + cake)
                .ifPresent(System.out::println);
        while (count >= buffer.length) {
            this.wait();
        }
        buffer[tail] = cake;
        tail = (tail + 1) % buffer.length;
        count++;
        this.notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (count <= 0) {
            this.wait();
        }
        String cake = buffer[head];
        head = (head + 1) % buffer.length;
        count--;
        notifyAll();
        Optional.of(Thread.currentThread().getName() + " takes " + cake)
                .ifPresent(System.out::println);
        return cake;
    }
}
