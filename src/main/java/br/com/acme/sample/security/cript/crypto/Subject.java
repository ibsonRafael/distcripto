package br.com.acme.sample.security.cript.crypto;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state;

    public void add(Observer o) {
        observers.add(o);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        execute();
    }

    private void execute() {
        for (Observer observer: observers) {
            observer.update();
        }
    }
}
