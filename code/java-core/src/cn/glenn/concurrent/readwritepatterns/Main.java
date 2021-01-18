package cn.glenn.concurrent.readwritepatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-27 15:10
 **/
public class Main {
    public static void main(String[] args) {
        ShareData data = new ShareData(10);
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new WriterThread(data, "ABCDEFGHIJKLMNOPQRSTUVWXYZ").start();
    }
}
