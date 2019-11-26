package cn.glenn.concurrent.guardedsuspensionpatterns;

import java.util.Optional;
import java.util.Random;

/**
 * @author: glenn wang
 * @date: 2019-11-27 00:01
 **/
public class ClientThread extends Thread {

    private Random random;

    private RequestQueue requestQueue;

    public ClientThread(RequestQueue requestQueue, String name, long seed) {
        super(name);
        this.requestQueue = requestQueue;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Request request = new Request("No." + i);
            Optional.of(Thread.currentThread().getName() + " requests " + request)
                    .ifPresent(System.out::println);
            requestQueue.putRequest(request);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
