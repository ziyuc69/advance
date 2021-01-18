package cn.glenn.concurrent.balkingpattern;

import java.io.IOException;
import java.util.Random;

/**
 * @author: glenn wang
 * @date: 2019-11-27 11:19
 **/
public class ChangerThread extends Thread {

    private Random random = new Random();

    private Data data;

    public ChangerThread(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        for (int i = 0; true; i++) {
            data.change("No." + i);
            try {
                Thread.sleep(random.nextInt(1000));
                data.save();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
