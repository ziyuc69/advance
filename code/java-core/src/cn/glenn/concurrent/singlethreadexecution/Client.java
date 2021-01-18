package cn.glenn.concurrent.singlethreadexecution;

/**
 * @author: glenn wang
 * @date: 2019-11-24 17:38
 *
 * 单线程通过大门案例
 * 同时开启三个线程，Gate类中pass方法被多个线程共享，
 * 为了保证线程安全，必须对pass方法加锁保证串行化，让单线程独占锁，其他线程等待。
 *
 * 这样有个问题，pass方法中有调用verify和toString，而这两个方法都是读操作
 * 为了保证最大性能，这两个方法需要并行处理。可以考虑读写锁分离
 *
 **/
public class Client {
    public static void main(String[] args) {
        Gate gate = new Gate();
        User user1 = new User("Beibao", "Beijing", gate);
        User user2 = new User("Shangbao", "Shanghai", gate);
        User user3 = new User("Guangbao", "Guangzhou", gate);

        user1.start();
        user2.start();
        user3.start();
    }
}
