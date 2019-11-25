package cn.glenn.concurrent.futurepatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-25 11:47
 **/
public interface FutureTask<T> {

    T call();
}
