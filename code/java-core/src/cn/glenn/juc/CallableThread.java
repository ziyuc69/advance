package cn.glenn.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: wangxg
 * @date: 2019-11-14 21:54
 **/
public class CallableThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int i = 0;
        for (int i1 = 0; i1 <= 900; i1++) {
            i += i1;
        }
        System.out.println(i);
        System.out.println("===========================");

        Callable<Integer> callable1 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = 0;
                for (int i = 0; i <= 500; i++) {
                    result += i;
                }
                return Integer.valueOf(result);
            }
        };

        FutureTask<Integer> task1 = new FutureTask<>(callable1);
        Thread t1 = new Thread(task1);
        t1.start();


        Callable<Integer> callable2 = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = 0;
                for (int i = 501; i <= 900; i++) {
                    result += i;
                }
                return Integer.valueOf(result);
            }
        };

        FutureTask<Integer> task2 = new FutureTask<>(callable2);
        Thread t2 = new Thread(task2);
        t2.start();

        Integer result1 = task1.get();
        Integer result2 = task2.get();
        System.out.println(result1 + result2);

    }
}
