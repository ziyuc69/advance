package cn.glenn.thread;

/**
 * @author: wangxg
 * @date: 2019-11-14 21:07
 **/
public class CreateThread1 {
    private static int count = 1;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(null, new Runnable() {

            @Override
            public void run() {
                add(0);
            }

            private void add(int i) {
                count++;
                add(i + 1);
            }
        }, "test", 1 << 24);
        t1.start();
        t1.join();
        System.out.println(count);
    }
}
