package cn.glenn.concurrent.singletonpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 12:07
 *
 * 双重锁检查+volatile
 * 推荐使用：完美解决了多线程问题，性能问题，实现单利模式
 **/
public class SingletonObject5 {

    private static volatile SingletonObject5 instance;

    private SingletonObject5() {
        // empty
    }

    public static SingletonObject5 getInstance() {
        if (instance == null) {
            synchronized (SingletonObject1.class) {
                if (instance == null) {
                    instance = new SingletonObject5();
                }
            }
        }

        return instance;
    }
}
