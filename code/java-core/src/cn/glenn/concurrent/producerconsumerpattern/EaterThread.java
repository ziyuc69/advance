package cn.glenn.concurrent.producerconsumerpattern;

import java.util.Random;

/**
 * @author: glenn wang
 * @date: 2019-11-27 14:26
 **/
public class EaterThread extends Thread {

    private Random random;

    private Table table;

    public EaterThread(String name, Table table, long seed) {
        super(name);
        this.random = new Random(seed);
        this.table = table;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String cake = table.take();
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
