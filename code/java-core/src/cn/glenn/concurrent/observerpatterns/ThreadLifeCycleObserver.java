package cn.glenn.concurrent.observerpatterns;

import java.util.List;

/**
 * @author: glenn wang
 * @date: 2019-11-24 16:27
 **/
public class ThreadLifeCycleObserver implements LifeCycleListener {

    private final Object LOCK = new Object();

    public void concurrentQuery(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        ids.forEach(id -> new Thread(new ObserverRunnable(this) {
            @Override
            public void run() {
                try {
                    notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                    System.out.println("query for the id " + id);
                    Thread.sleep(1000);
                    notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                } catch (Exception e) {
                    notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                }
            }
        }, id).start());
    }

    @Override
    public void onEvent(ObserverRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println("The runable [" + event.getThread().getName() + "] data changed and state is " + event.getState());
            if (event.getCause() != null) {
                System.out.println("The runable [" + event.getThread().getName() + "] process failed.");
                event.getCause().printStackTrace();
            }
        }
    }
}
