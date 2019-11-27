package cn.glenn.concurrent.producerconsumerpattern;

import java.util.Optional;
import java.util.Random;

/**
 * @author: glenn wang
 * @date: 2019-11-27 14:26
 **/
public class MakerThread extends Thread {

    private Random random;

    private Table table;

    private static int id = 0;

    public MakerThread(String name, Table table, long seed) {
        super(name);
        this.random = new Random(seed);
        this.table = table;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(random.nextInt(1000));
                String cake = "Cake No." + nextId() + " by " + getName() + "";
                table.put(cake);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized int nextId() {
        return id++;
    }
}
