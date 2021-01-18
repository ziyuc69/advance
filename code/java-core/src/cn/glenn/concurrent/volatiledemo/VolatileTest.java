package cn.glenn.concurrent.volatiledemo;

import java.util.Optional;

/**
 * @author: glenn wang
 * @date: 2019-11-24 12:47
 *
 * happens-before原则
 * 1。代码的执行顺序，编写在前面的发生在编写在后面的
 * 2。unlock必须发生在lock之后
 * 3。volatile修饰变量，对于一个变量的写操作先于对变量的读操作
 * 4。传递规则，操作A优先于B，B先于C，那么A肯定先于C
 * 5。线程启动规则，start方法肯定先于线程run
 * 6。线程中段规则，interrupt这个动作，必须发生在捕获该动作之前
 * 7。对象销毁规则，初始化必须发生在finalize之前
 * 8。线程终结规则，所以的操作都发生在线程死亡之前
 *
 *
 * volatile关键字
 * 一旦一个共享变量被volatile修饰，具备连层语义
 * 1。保证了不同线程间的可见性
 * 2。禁止对其进行重排序，也就是保证了有序性
 * 3。并不保证原子性
 *
 *
 * 防止重排序
 * 1。保证重排序的是不会把后面的执行放在屏障的前面，也不会把前面的执行放在屏障后面
 * 2。强制对缓存的修改操作立刻写入主存
 * 3。如果是写操作，他会导致其他cpu中的缓存失效
 **/
public class VolatileTest {

    private volatile static int INIT_VALUE = 0;

    private final static int MAX_VALUE = 5;

    public static void main(String[] args) {
        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (localValue < MAX_VALUE) {
                if (localValue != INIT_VALUE) {
                    Optional.of("the value update to " + INIT_VALUE)
                            .ifPresent(System.out::println);
                    localValue = INIT_VALUE;
                }
            }
        }, "READ-THREAD").start();

        new Thread(() -> {
           int localValue = INIT_VALUE;
           while (localValue < MAX_VALUE) {
               Optional.of("Update the value to " + (++localValue))
                       .ifPresent(System.out::println);
               INIT_VALUE = localValue;

               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }

       }, "WRITE-THREAD").start();
    }

}
