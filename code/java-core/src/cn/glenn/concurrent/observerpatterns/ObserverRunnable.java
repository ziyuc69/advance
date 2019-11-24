package cn.glenn.concurrent.observerpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 16:20
 **/
public abstract class ObserverRunnable implements Runnable {

    protected  LifeCycleListener listener;

    public ObserverRunnable(LifeCycleListener listener) {
        this.listener = listener;
    }

    protected  void notifyChange(final RunnableEvent event) {
        listener.onEvent(event);
    }

    public enum RunnableState {
        /** 运行 */
        RUNNING,
        /** 错误 */
        ERROR,
        /** 结束 */
        DONE;
    }

    public static class RunnableEvent {
        private final RunnableState state;
        private final Thread thread;
        private final Throwable cause;

        public RunnableEvent(RunnableState state, Thread thread, Throwable cause) {
            this.state = state;
            this.thread = thread;
            this.cause = cause;
        }

        public RunnableState getState() {
            return state;
        }

        public Thread getThread() {
            return thread;
        }

        public Throwable getCause() {
            return cause;
        }
    }
}
