package cn.glenn.concurrent.singletonpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 12:03
 *
 * 饿汉模式
 * 不推荐使用：实例是静态常量，会一直存在占用内存
 **/
public class SingletonObject1 {

    private static final SingletonObject1 instance = new SingletonObject1();

    private SingletonObject1() {
        // empty
    }

    public static SingletonObject1 getInstance() {
        return instance;
    }
}
