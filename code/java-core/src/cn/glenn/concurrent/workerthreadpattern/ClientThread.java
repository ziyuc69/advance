package cn.glenn.concurrent.workerthreadpattern;

import java.util.Random;

/**
 * @author: glenn wang
 * @date: 2019-11-27 16:12
 **/
public class ClientThread extends Thread {

    private final Channel channel;

    private static final Random random = new Random();

    public ClientThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        for (int i = 0; true; i++) {
            try {
                Request request = new Request(getName(), i);
                channel.putRequest(request);
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
