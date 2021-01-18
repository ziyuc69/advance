package cn.glenn.concurrent.balkingpattern;

/**
 * @author: glenn wang
 * @date: 2019-11-27 11:22
 **/
public class Main {
    public static void main(String[] args) {
        Data data = new Data("data.txt", "(empty)");
        new ChangerThread("changerThread", data).start();
        new SaverThread("saverThread", data).start();
    }
}
