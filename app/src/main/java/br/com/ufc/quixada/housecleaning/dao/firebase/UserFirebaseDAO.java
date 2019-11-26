package br.com.ufc.quixada.housecleaning.dao.firebase;

import java.util.ArrayList;
import java.util.List;

import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.User;

public class UserFirebaseDAO extends GenericFirebaseDAO<User> implements UserDAO {

    private static UserDAO userDAO = null;

    private UserFirebaseDAO(UserEventListener eventListener) {
        super("users", eventListener, User.class);
    }

    public static UserDAO getInstance(UserEventListener eventListener) {
        if (userDAO == null) {
            userDAO = new UserFirebaseDAO(eventListener);
        }

        return userDAO;
    }

    @Override
    public List<User> findAllWorkers() {
        List<User> workers = new ArrayList<>();

        for (User user : findAll()) {
            if (user.isWorker())
                workers.add(user);
        }

        return workers;
    }

    @Override
    public User findByEmail(String email) {
        for (User user : findAll()) {
            if (user.getEmail().equals(email))
                return user;
        }

        return null;
    }

}
