package cn.glenn.juc;

import java.util.Arrays;

/**
 * @author: wangxg
 * @date: 2019-11-14 20:55
 **/
public class CreateThread {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        // 线程组
        System.out.println(t1.getThreadGroup());
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println(threadGroup);

        // 活动线程数量
        int count = Thread.activeCount();
        System.out.println(count);

        // 打印活动的线程
        Thread[] threads = new Thread[count];
        threadGroup.enumerate(threads);
        Arrays.asList(threads).forEach(System.out::println);
    }
}
