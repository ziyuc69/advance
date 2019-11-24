package cn.glenn.concurrent.singlethreadexecution;

/**
 * @author: glenn wang
 * @date: 2019-11-24 17:34
 **/
public class User extends Thread {

    private String myName;

    private String myAddress;

    private Gate gate;

    public User(String myName, String myAddress, Gate gate) {
        this.myName = myName;
        this.myAddress = myAddress;
        this.gate = gate;
    }

    @Override
    public void run() {
        System.out.println(myName + " BEGIN");
        while (true) {
            // 这里调用Gate类中的pass方法，因为里面牵扯到修改共享资源，
            // 为了保证线程安全，必须要保证线程串行化执行，虽有可以加锁处理
            this.gate.pass(myName, myAddress);
        }
    }
}
