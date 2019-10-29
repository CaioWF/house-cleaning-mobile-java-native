package br.com.ufc.quixada.housecleaning.dao.memory;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<CleaningService> findAllByResponsible(Integer responsibleId) {
        List<CleaningService> cleaningServices = new ArrayList<>();

        for (CleaningService cleaningService : findAll()) {
            if (responsibleId == cleaningService.getResponsible().getId())
                cleaningServices.add(cleaningService);
        }

        return cleaningServices;
    }

    @Override
    public List<CleaningService> findAllByRequester(Integer requesterId) {
        List<CleaningService> cleaningServices = new ArrayList<>();

        for (CleaningService cleaningService : findAll()) {
            if (requesterId == cleaningService.getRequester().getId())
                cleaningServices.add(cleaningService);
        }

        return cleaningServices;
    }
}
