package cn.glenn.concurrent.guardedsuspensionpatterns;

import java.util.LinkedList;

/**
 * @author: glenn wang
 * @date: 2019-11-26 23:58
 **/
public class RequestQueue {

    private final LinkedList<Request> queue = new LinkedList();

    public synchronized Request getRequest() {
        while (queue.size() <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return queue.removeFirst();
    }

    public synchronized void putRequest(Request request) {
        queue.addLast(request);
        notify();
    }
}
