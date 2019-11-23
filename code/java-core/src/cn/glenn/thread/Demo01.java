package cn.glenn.thread;

/**
 * @author: wangxg
 * @date: 2019-11-14 20:23
 **/
public class Demo01 {
    public static void main(String[] args) throws InterruptedException {
        //1 NEW状态
//        Thread t1 = new Thread();
//        System.out.println(t1.getState());

        //2 RUNNABLE状态
//        Thread t2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(30);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "thread-1");
//        t2.start();
//        System.out.println(t2.getState());

        //3 TIMED_WAITING
//        Thread t3 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(300000000); // 休眠时间结束，就会醒来
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, "thread-1");
//        t3.start();
//        System.out.println(t3.getState());

        //4 WAITING
//        Object o = new Object();
//        Thread t4 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (o) {
//                    try {
//                        o.wait(); // 需要notity，notifyAll唤醒
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        t4.start();
//        System.out.println(t4.getState());

        //5 BLOCKED，TERMINATED
        Object o = new Object();
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    try {
//                        o.wait();                  // 这里wait，t6 是TERMINATED状态
                        Thread.sleep(300);   // 这里延时，t6 是BLOCKED状态
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        // 延时一段时间，t5线程先执行
        Thread.sleep(10);

        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    System.out.println("hello thread6...");
                }
            }
        });
        t5.start();
        t6.start();
        System.out.println(t5.getState());
        System.out.println(t6.getState());
    }
}
