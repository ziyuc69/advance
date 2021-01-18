package cn.glenn.concurrent.singlethreadexecution;

/**
 * @author: glenn wang
 * @date: 2019-11-24 17:31
 **/
public class Gate {

    private int counter = 0;

    private String name = "NoBody";

    private String address = "Nowhere";

    public synchronized void pass(String name, String address) {
        this.counter++;
        this.name = name;
        this.address = address;
        verify();
    }

    private void verify() {
        if (this.name.charAt(0) != this.address.charAt(0)) {
            System.out.println("********BROKEN********" + toString());
        }
    }

    @Override
    public synchronized String toString() {
        return "No." + counter + ":" + name + "," + address;
    }
}
