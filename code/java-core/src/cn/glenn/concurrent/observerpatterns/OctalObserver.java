package cn.glenn.concurrent.observerpatterns;

/**
 * @author: glenn wang
 * @date: 2019-11-24 16:11
 **/
public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    public void update() {
        System.out.println("Binary string:" + Integer.toOctalString(subject.getState()));
    }
}
