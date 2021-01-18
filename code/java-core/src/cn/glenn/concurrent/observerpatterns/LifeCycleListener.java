package cn.glenn.concurrent.observerpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 16:25
 **/
public interface LifeCycleListener {

    void onEvent(ObserverRunnable.RunnableEvent event);
}
