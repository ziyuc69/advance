package cn.glenn.thread;

/**
 * @author: wangxg
 * @date: 2019-11-14 21:38
 **/
public class PriorityThread {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(Thread.currentThread().getName() + "-index" + i);
            }
        });
        t1.setPriority(Thread.MAX_PRIORITY);

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(Thread.currentThread().getName() + "-index" + i);
            }
        });
        t1.setPriority(Thread.NORM_PRIORITY);

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println(Thread.currentThread().getName() + "-index" + i);
            }
        });
        t1.setPriority(Thread.MIN_PRIORITY);

        t1.start();
        t2.start();
        t3.start();
    }
}
