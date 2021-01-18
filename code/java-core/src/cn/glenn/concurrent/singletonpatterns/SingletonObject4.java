package cn.glenn.concurrent.singletonpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 12:07
 *
 * 双重锁检查
 * 解决了多线程问题，性能问题，实现单利模式
 * 不推荐使用：会出现NullPointerException(对象实例化不是原子性)
 **/
public class SingletonObject4 {

    private static SingletonObject4 instance;

    private SingletonObject4() {
        // empty
    }

    public static SingletonObject4 getInstance() {
        if (instance == null) {
            synchronized (SingletonObject1.class) {
                if (instance == null) {
                    instance = new SingletonObject4();
                }
            }
        }

        return instance;
    }
}
