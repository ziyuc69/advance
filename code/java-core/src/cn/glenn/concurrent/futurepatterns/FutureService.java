package cn.glenn.concurrent.futurepatterns;

import java.util.function.Consumer;

/**
 * @author: glenn wang
 * @date: 2019-11-25 11:48
 **/
public class FutureService {

    public <T> Future<T> submit(final FutureTask<T> task) {
        AsyncFuture<T> asyncFuture = new AsyncFuture();
        new Thread(() -> {
            T result = task.call();
            asyncFuture.done(result);
        }).start();
        return asyncFuture;
    }

    public <T> Future<T> submit(final FutureTask<T> task, Consumer<T> consumer) {
        AsyncFuture<T> asyncFuture = new AsyncFuture();
        new Thread(() -> {
            T result = task.call();
            asyncFuture.done(result);
            consumer.accept(result);
        }).start();
        return asyncFuture;
    }
}
