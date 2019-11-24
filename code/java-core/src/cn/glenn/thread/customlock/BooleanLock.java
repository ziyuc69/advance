package cn.glenn.thread.customlock;

import java.util.*;

/**
 * @author: glenn wang
 * @date: 2019-11-23 21:36
 **/
public class BooleanLock implements Lock {

    /** 如果intValue是true，说明锁已经被拿走了 */
    private boolean intValue;

    /** 用来记录当前是那个线程在使用锁，防止其他线程误释放锁 */
    private Thread currentThread;

    /** 存储等待锁的线程，如果线程没有抢到锁，则进入队列。知道其他线程释放锁后再将队列中的线程唤醒 */
    private Collection<Thread> blockThreadCollection = new HashSet<>();

    public BooleanLock() {
        this.intValue = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        // 如果条件成立，说明锁被其他线程占用，则新进来的线程加入队列等待
        // （这里使用while循环原因：
        //      1。如果前一个线程使用完锁后释放锁，会唤醒队列里面所有线程，并且此时 intValue = false
        //      2。此时，队列的所有线程从wait中醒来，如果用while. 某一个线程抢到锁会立马修改 intValue = true。另一个线程需要重新进行while判断。然后重新加入队列后进入wait
        //      3。如果这里不使用while，就会出现。唤醒的多个线程，同时继续往下执行。
        //  ）
        while (intValue) {
            blockThreadCollection.add(Thread.currentThread());
            this.wait();
        }

        blockThreadCollection.remove(Thread.currentThread());
        this.intValue = true;
        currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        if (mills <= 0) {
            lock();
        }

        long hasRemaining = mills;
        long endTime = System.currentTimeMillis() + mills;
        while (intValue) {
            // 超时处理
            if (hasRemaining <= 0) {
                throw new TimeOutException("Time out.");
            }

            blockThreadCollection.add(Thread.currentThread());
            this.wait(mills);
            hasRemaining = endTime - System.currentTimeMillis();
        }

        blockThreadCollection.remove(Thread.currentThread());
        this.intValue = true;
        currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        if (Thread.currentThread() == currentThread) {
            this.intValue = false;
            Optional.of(Thread.currentThread().getName() + " release the lock monitor")
                    .ifPresent(System.out::println);
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockThread() {
        return Collections.unmodifiableCollection(blockThreadCollection);
    }

    @Override
    public int getBlockSize() {
        return blockThreadCollection.size();
    }
}
