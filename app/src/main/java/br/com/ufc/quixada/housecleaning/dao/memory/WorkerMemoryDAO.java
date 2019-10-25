package br.com.ufc.quixada.housecleaning.dao.memory;

import br.com.ufc.quixada.housecleaning.dao.WorkerDAO;
import br.com.ufc.quixada.housecleaning.presenter.EventListener;
import br.com.ufc.quixada.housecleaning.transactions.Worker;

public class WorkerMemoryDAO extends GenericMemoryDAO<Worker> implements WorkerDAO {

    private static WorkerDAO workerDAO;

    private WorkerMemoryDAO(EventListener eventListener) {
        super(eventListener);
    }

    public static WorkerDAO getInstance(EventListener eventListener) {
        if (workerDAO == null)
            workerDAO = new WorkerMemoryDAO(eventListener);

        return workerDAO;
    }

}
