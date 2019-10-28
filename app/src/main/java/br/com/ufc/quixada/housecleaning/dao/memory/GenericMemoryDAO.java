package br.com.ufc.quixada.housecleaning.dao.memory;

import java.util.ArrayList;
import java.util.List;

import br.com.ufc.quixada.housecleaning.dao.GenericDAO;
import br.com.ufc.quixada.housecleaning.transactions.Bean;

public class GenericMemoryDAO<T extends Bean> implements GenericDAO<T> {

    private List<T> tList;

    public GenericMemoryDAO() {
        tList = new ArrayList<>();
    }

    @Override
    public void create(T t) {
        tList.add(t);
    }

    @Override
    public void update(T t) {

    }

    @Override
    public void delete(T t) {
        for (T tListItem : tList) {
            if (tListItem.getId() == t.getId())
                tList.remove(t);
        }
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
