package br.com.ufc.quixada.housecleaning.dao;

import java.util.List;

import br.com.ufc.quixada.housecleaning.transactions.User;

public interface UserDAO extends GenericDAO<User> {

    List<User> findAllWorkers();

}
