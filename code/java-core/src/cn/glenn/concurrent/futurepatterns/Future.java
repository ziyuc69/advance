package cn.glenn.concurrent.futurepatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-25 11:46
 **/
public interface Future<T> {

    T get() throws InterruptedException;
}
