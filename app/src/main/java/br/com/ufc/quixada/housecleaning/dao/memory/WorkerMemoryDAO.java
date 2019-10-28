package br.com.ufc.quixada.housecleaning.dao.memory;

import br.com.ufc.quixada.housecleaning.dao.WorkerDAO;
import br.com.ufc.quixada.housecleaning.presenter.WorkerEventListener;
import br.com.ufc.quixada.housecleaning.transactions.Worker;

public class WorkerMemoryDAO extends GenericMemoryDAO<Worker> implements WorkerDAO {

    private static WorkerDAO workerDAO;
    private WorkerEventListener workerEventListener;

    private WorkerMemoryDAO(WorkerEventListener workerEventListener) {
        super();
        this.workerEventListener = workerEventListener;
    }

    @Override
    public void create(Worker worker) {
        super.create(worker);
    }

    public static WorkerDAO getInstance(WorkerEventListener workerEventListener) {
        if (workerDAO == null)
            workerDAO = new WorkerMemoryDAO(workerEventListener);

        return workerDAO;
    }

}
