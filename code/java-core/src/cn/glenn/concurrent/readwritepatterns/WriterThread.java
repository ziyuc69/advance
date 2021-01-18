package cn.glenn.concurrent.readwritepatterns;

import java.util.Random;

/**
 * @author: glenn wang
 * @date: 2019-11-27 15:09
 **/
public class WriterThread extends Thread {

    private static final Random random = new Random();

    private final ShareData data;

    private final String filler;

    private int index = 0;

    public WriterThread(ShareData data, String filler) {
        this.data = data;
        this.filler = filler;
    }

    @Override
    public void run() {
        while (true) {
            try {
                char c = nextChar();
                data.write(c);
                Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private char nextChar() {
        char c = filler.charAt(index);
        index++;
        if (index >= filler.length()) {
            index = 0;
        }
        return c;
    }
}
