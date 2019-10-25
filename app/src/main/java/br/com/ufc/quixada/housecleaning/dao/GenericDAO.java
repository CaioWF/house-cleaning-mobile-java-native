package br.com.ufc.quixada.housecleaning.dao;

import java.util.List;

import br.com.ufc.quixada.housecleaning.transactions.Bean;

public interface GenericDAO<T extends Bean> {

    void create(T t);

    void update(T t);

    void delete(T t);

    T findById(int id);

    List<T> findAll();

}
