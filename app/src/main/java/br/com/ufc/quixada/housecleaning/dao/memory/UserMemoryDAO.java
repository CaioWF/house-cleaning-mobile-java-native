package br.com.ufc.quixada.housecleaning.dao.memory;

import java.util.ArrayList;
import java.util.List;

import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.User;

public class UserMemoryDAO extends GenericMemoryDAO<User> implements UserDAO {

    private static UserDAO userDAO;
    private UserEventListener userEventListener;

    private UserMemoryDAO(UserEventListener userEventListener) {
        super();
        this.userEventListener = userEventListener;
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

    public static UserDAO getInstance(UserEventListener userEventListener) {
        if (userDAO == null)
            userDAO = new UserMemoryDAO(userEventListener);

        return userDAO;
    }

}
