package cn.glenn.concurrent.immutablepattern;

/**
 * @author: glenn wang
 * @date: 2019-11-27 19:59
 **/
public class Main {
    public static void main(String[] args) {
        Person person = new Person("glenn", "Shaanxi");
        new PrintPersonThread(person).start();
        new PrintPersonThread(person).start();
        new PrintPersonThread(person).start();
    }
}
