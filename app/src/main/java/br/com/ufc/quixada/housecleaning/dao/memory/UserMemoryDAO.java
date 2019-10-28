package br.com.ufc.quixada.housecleaning.dao.memory;

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

    public static UserDAO getInstance(UserEventListener userEventListener) {
        if (userDAO == null)
            userDAO = new UserMemoryDAO(userEventListener);

        return userDAO;
    }
}
