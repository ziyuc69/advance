package cn.glenn.concurrent.observerpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 16:07
 **/
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Binary string:" + Integer.toBinaryString(subject.getState()));
    }
}
