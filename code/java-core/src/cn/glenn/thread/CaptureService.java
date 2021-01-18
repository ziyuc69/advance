package cn.glenn.thread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author: wxg
 * @date: 2019-11-23 21:10
 * 通过一个限定一个集合的大小，来控制同时只能有固定的线程数量在工作
 **/
public class CaptureService {

    private final static LinkedList<Control> CONTROLS = new LinkedList<>();
    private final static int MAX_WORKER = 5;

    public static void main(String[] args) {

        List<Thread> worker = new ArrayList<>();
        Stream.of("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8")
                .map(CaptureService::createThread)
                .forEach(t -> {
                    t.start();
                    worker.add(t);
                });

        // main线程等待所有线程都执行结束
        worker.stream().forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Optional.of("All of capture work finished")
                .ifPresent(System.out::println);
    }

    public static Thread createThread(String name) {
        return new Thread(() -> {
            Optional.of("The worker [" + Thread.currentThread().getName() + "] BEGIN capture data.")
                    .ifPresent(System.out::println);
            synchronized (CONTROLS) {
                while (CONTROLS.size() > MAX_WORKER) {
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CONTROLS.addLast(new Control());
            }

            Optional.of("The worker [" + Thread.currentThread().getName() + "] is working...")
                    .ifPresent(System.out::println);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (CONTROLS) {
                Optional.of("The worker [" + Thread.currentThread().getName() + "] END capture data.")
                        .ifPresent(System.out::println);
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }

        }, name);
    }

    private static class Control {

    }
}
