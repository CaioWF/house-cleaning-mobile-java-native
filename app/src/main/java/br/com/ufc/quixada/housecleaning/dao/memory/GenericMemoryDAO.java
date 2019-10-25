package br.com.ufc.quixada.housecleaning.dao.memory;

import java.util.ArrayList;
import java.util.List;

import br.com.ufc.quixada.housecleaning.dao.GenericDAO;
import br.com.ufc.quixada.housecleaning.presenter.EventListener;
import br.com.ufc.quixada.housecleaning.transactions.Bean;

public class GenericMemoryDAO<T extends Bean> implements GenericDAO<T> {

    private List<T> tList;
    private EventListener eventListener;

    public GenericMemoryDAO(EventListener eventListener) {
        tList = new ArrayList<>();
        this.eventListener = eventListener;
    }

    @Override
    public void create(T t) {
        tList.add(t);

        eventListener.onCreate(t);
    }

    @Override
    public void update(T t) {
        eventListener.onUpdate(t);
    }

    @Override
    public void delete(T t) {
        for (T tListItem : tList) {
            if (tListItem.getId() == t.getId())
                tList.remove(t);
        }

        eventListener.onDelete(t);
    }

    @Override
    public T findById(int id) {
        for (T t : tList) {
            if (t.getId() == id)
                return t;
        }

        return null;
    }

    @Override
    public List<T> findAll() {
        return tList;
    }

}
