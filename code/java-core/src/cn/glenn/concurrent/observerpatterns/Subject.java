package cn.glenn.concurrent.observerpatterns;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: glenn wang
 * @date: 2019-11-24 16:02
 **/
public class Subject {

    private List<Observer> observers = new ArrayList<>();

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if (state == this.state) {
            return;
        }

        this.state = state;
        notifyAllObserver();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyAllObserver() {
        observers.stream().forEach(o -> {
            o.update();
        });
    }
}
