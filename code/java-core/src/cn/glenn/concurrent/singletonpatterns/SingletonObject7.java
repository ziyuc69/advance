package cn.glenn.concurrent.singletonpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 12:17
 *
 * 借用枚举来实现单利模式
 **/
public class SingletonObject7 {
    private SingletonObject7() {
        // empty
    }

    private enum Singleton {
        /** 枚举实例 */
        INSTANCE;

        private final SingletonObject7 instance;

        Singleton() {
            this.instance = new SingletonObject7();
        }

        public SingletonObject7 getInstance() {
            return instance;
        }
    }

    public static SingletonObject7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }


}
