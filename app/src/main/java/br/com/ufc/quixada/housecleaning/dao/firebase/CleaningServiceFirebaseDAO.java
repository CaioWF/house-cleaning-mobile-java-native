package br.com.ufc.quixada.housecleaning.dao.firebase;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.ufc.quixada.housecleaning.dao.CleaningServiceDAO;
import br.com.ufc.quixada.housecleaning.presenter.CleaningServiceEventListener;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;

public class CleaningServiceFirebaseDAO extends GenericFirebaseDAO<CleaningService> implements CleaningServiceDAO {

    private static CleaningServiceDAO cleaningServiceDAO;

    private CleaningServiceFirebaseDAO(CleaningServiceEventListener eventListener) {
        super("cleaning_services", eventListener, CleaningService.class);
    }

    public static CleaningServiceDAO getInstance(CleaningServiceEventListener eventListener) {
        if (cleaningServiceDAO == null)
            cleaningServiceDAO = new CleaningServiceFirebaseDAO(eventListener);

        return cleaningServiceDAO;
    }

    @Override
    public List<CleaningService> findAllByResponsible(Object responsibleId) {
        List<CleaningService> cleaningServices = new ArrayList<>();

        for (CleaningService cleaningService : findAll()) {
            if (responsibleId.equals(cleaningService.getResponsible().getId()))
                cleaningServices.add(cleaningService);
        }

        return cleaningServices;
    }

    @Override
    public List<CleaningService> findAllByRequester(Object requesterId) {
        List<CleaningService> cleaningServices = new ArrayList<>();

        for (CleaningService cleaningService : findAll()) {
            Log.d("TEST", requesterId + " | " + cleaningService.getRequester().getId());

            if (requesterId.equals(cleaningService.getRequester().getId()))
                cleaningServices.add(cleaningService);
        }

        return cleaningServices;
    }
}
