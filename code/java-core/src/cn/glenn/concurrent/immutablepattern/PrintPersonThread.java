package cn.glenn.concurrent.immutablepattern;

import java.util.Optional;

/**
 * @author: glenn wang
 * @date: 2019-11-27 20:00
 **/
public class PrintPersonThread extends Thread {

    private final Person person;

    public PrintPersonThread(final Person person) {
        this.person = person;
    }

    @Override
    public void run() {
        while (true) {
            Optional.of(Thread.currentThread().getName() + " prints " + person)
                    .ifPresent(System.out::println);
        }
    }
}
