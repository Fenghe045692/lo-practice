package stu.jzhang.algorithm.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hellen on 8/17/17.
 */
public class Bag<T> implements Iterable<T>{
    private List<T> items;

    public Bag() {
        items = new ArrayList<>();
    }

    public Bag(List<T> items) {
        this.items = items;
    }

    public List<T> getItems() {
        return items;
    }

    public void add(T item){
        items.add(item);
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }

    public int size(){
        return items.size();
    }

    @Override
    public Iterator<T> iterator() {
        return items.iterator();
    }
}
