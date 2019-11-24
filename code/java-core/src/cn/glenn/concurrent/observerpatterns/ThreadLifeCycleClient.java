package cn.glenn.concurrent.observerpatterns;

import java.util.Arrays;

/**
 * @author: glenn wang
 * @date: 2019-11-24 16:50
 **/
public class ThreadLifeCycleClient {
    public static void main(String[] args) {
        new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList("1", "2"));
    }
}
