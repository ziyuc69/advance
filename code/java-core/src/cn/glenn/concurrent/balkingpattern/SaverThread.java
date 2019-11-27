package cn.glenn.concurrent.balkingpattern;

import java.io.IOException;

/**
 * @author: glenn wang
 * @date: 2019-11-27 11:09
 **/
public class SaverThread extends Thread {

    private Data data;

    public SaverThread(String name, Data data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        while (true) {
            try {
                data.save();
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
