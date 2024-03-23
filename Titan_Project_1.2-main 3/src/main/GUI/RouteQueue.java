package src.main.GUI;

import java.util.LinkedList;

public class RouteQueue<T> {

    final LinkedList<T> queueElements = new LinkedList<>();

    public int size() {
        return queueElements.size();
    }

    public void add(T newPositionPoint) {
        if (queueElements.size() >= 100) {
            removeFirst();
        }
        queueElements.add(newPositionPoint);
    }

    public void removeFirst() {
        queueElements.removeFirst();
    }

    public T getLast() {
        return queueElements.getLast();
    }

    public T get(int index) throws ArrayIndexOutOfBoundsException {
        return queueElements.get(index);
    }

}
