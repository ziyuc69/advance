package cn.glenn.concurrent.singletonpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 12:07
 *
 * 懒汉模式
 * 不推荐使用：多线程情况下可能就不是单利了
 **/
public class SingletonObject2 {

    private static SingletonObject2 instance;

    private SingletonObject2() {
        // empty
    }

    public static SingletonObject2 getInstance() {
        if (instance == null) {
            instance = new SingletonObject2();
        }
        return SingletonObject2.instance;
    }
}
