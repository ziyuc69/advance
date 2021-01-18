package cn.glenn.thread;

/**
 * @author: wangxg
 * @date: 2019-11-14 21:22
 **/
public class DaemonThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " running...");
            try {
                Thread.sleep(100_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " done.");
        });
        t1.setDaemon(true);
        t1.start();

        Thread.sleep(5_000);
        System.out.println(Thread.currentThread().getName());
    }
}
