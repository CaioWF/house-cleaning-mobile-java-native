package br.com.ufc.quixada.housecleaning.dao.memory;

import br.com.ufc.quixada.housecleaning.dao.CleaningServiceDAO;
import br.com.ufc.quixada.housecleaning.presenter.CleaningServiceEventListener;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;

public class CleaningServiceMemoryDAO extends GenericMemoryDAO<CleaningService> implements CleaningServiceDAO {

    private static CleaningServiceDAO cleaningServiceDAO;
    private CleaningServiceEventListener cleaningServiceEventListener;

    private CleaningServiceMemoryDAO(CleaningServiceEventListener cleaningServiceEventListener) {
        super();
        this.cleaningServiceEventListener = cleaningServiceEventListener;
    }

    public static CleaningServiceDAO getInstance(CleaningServiceEventListener cleaningServiceEventListener) {
        if (cleaningServiceDAO == null)
            cleaningServiceDAO = new CleaningServiceMemoryDAO(cleaningServiceEventListener);

        return cleaningServiceDAO;
    }
}
