package cn.glenn.concurrent.observerpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 23:12
 **/
public class CustomObserverRunnable extends ObserverRunnable {

    private String id;

    public CustomObserverRunnable(LifeCycleListener listener, String id) {
        super(listener);
        this.id = id;
    }

    @Override
    public void run() {
        try {
            notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
            System.out.println("query for the id " + this.id);
            Thread.sleep(1000);
            notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
        } catch (Exception e) {
            notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
        }
    }
}
