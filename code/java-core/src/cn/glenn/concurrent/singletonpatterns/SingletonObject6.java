package cn.glenn.concurrent.singletonpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 12:17
 *
 * 使用静态内部类，借用类加载机制，实现单利模式
 * 推荐使用：完美解决了多线程问题，性能问题，实现单利模式
 **/
public class SingletonObject6 {
    private SingletonObject6() {
        // empty
    }

    /** 使用静态内部类，保证了只有在使用的时候才加载 */
    private static class InstanceHolder {
        private final static SingletonObject6 instance = new SingletonObject6();
    }

    public static SingletonObject6 getInstance() {
        return InstanceHolder.instance;
    }
}
