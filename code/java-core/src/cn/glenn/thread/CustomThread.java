package cn.glenn.thread;

/**
 * @author: wangxg
 * @date: 2019-11-14 20:38
 **/
public class CustomThread {
    public static void main(String[] args) {
        // 调用start和run的区别
        // 1.start才是开启线程
        // 2.run方法是线程要执行的内容
        new MyThread(new MyRunnable() {
            @Override
            public void run() {
                System.out.println("hello custom Thread...");
            }
        }).start();
    }
}

class MyThread {
    MyRunnable target;

    public MyThread(MyRunnable runnable) {
        this.target = runnable;
    }

    public void start() {
        if (target != null) {
            target.run();
        }
    }
}


interface MyRunnable {
    void run();
}