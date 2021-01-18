package cn.glenn.concurrent.readwritepatterns;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * @author: glenn wang
 * @date: 2019-11-27 15:09
 **/
public class ReaderThread extends Thread {

    private final ShareData data;

    public ReaderThread(ShareData data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (true) {
            try {
                char[] readbuf = data.read();
                Optional.of(Thread.currentThread().getName() + " reads " + String.valueOf(readbuf))
                        .ifPresent(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
