package cn.glenn.thread.customlock;

import java.util.Collection;

/**
 * @author: glenn wang
 * @date: 2019-11-23 21:32
 **/
public interface Lock {

    class TimeOutException extends Exception {
        public TimeOutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException,TimeOutException;

    void unlock();

    Collection<Thread> getBlockThread();

    int getBlockSize();
}
