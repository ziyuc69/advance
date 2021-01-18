package cn.glenn.concurrent.workerthreadpattern;

/**
 * @author: glenn wang
 * @date: 2019-11-27 16:12
 **/
public class WorkerThread extends Thread {

    private final Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            Request request = channel.takeRequest();
            request.execute();
        }
    }
}
