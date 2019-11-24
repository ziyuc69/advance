package cn.glenn.concurrent.singletonpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 12:07
 *
 * 懒汉模式
 * 不推荐使用：加锁保证了线程安全，但是同步方法会导致读操作被阻塞
 **/
public class SingletonObject3 {

    private static SingletonObject3 instance;

    private SingletonObject3() {
        // empty
    }

    public synchronized static SingletonObject3 getInstance() {
        if (instance == null) {
            instance = new SingletonObject3();
        }
        return instance;
    }
}
