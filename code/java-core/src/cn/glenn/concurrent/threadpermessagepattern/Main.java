package cn.glenn.concurrent.threadpermessagepattern;

/**
 * @author: glenn wang
 * @date: 2019-11-27 15:43
 * 在主线程创建一个新的类，然后把任务委托给新的类里面去处理
 * 新的类中会开启新的线程执行任务，提高执行效率。但是并不需要主线程关心执行情况
 **/
public class Main {
    public static void main(String[] args) {
        System.out.println("main BEGIN");
        Host host = new Host();
        host.request(10, 'A');
        host.request(20, 'B');
        host.request(30, 'C');
        System.out.println("main END");
    }
}
