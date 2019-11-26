package cn.glenn.concurrent.guardedsuspensionpatterns;

import java.util.Optional;
import java.util.Random;

/**
 * @author: glenn wang
 * @date: 2019-11-27 00:06
 **/
public class ServerThread extends Thread {

    private Random random;

    private RequestQueue requestQueue;

    public ServerThread(RequestQueue requestQueue, String name, Long seed) {
        super(name);
        this.random = new Random(seed);
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Request request = requestQueue.getRequest();
            Optional.of(Thread.currentThread().getName() + " handles " + request)
                    .ifPresent(System.out::println);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
