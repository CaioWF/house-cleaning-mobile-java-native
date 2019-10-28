package br.com.ufc.quixada.housecleaning.dao.memory;

import br.com.ufc.quixada.housecleaning.dao.PlaceDAO;
import br.com.ufc.quixada.housecleaning.presenter.PlaceEventListener;
import br.com.ufc.quixada.housecleaning.transactions.Place;

public class PlaceMemoryDAO extends GenericMemoryDAO<Place> implements PlaceDAO {

    private static PlaceDAO placeDAO;
    private PlaceEventListener placeEventListener;

    private PlaceMemoryDAO(PlaceEventListener placeEventListener) {
        super();
        this.placeEventListener = placeEventListener;
    }

    public static PlaceDAO getInstance(PlaceEventListener placeEventListener) {
        if (placeDAO == null)
            placeDAO = new PlaceMemoryDAO(placeEventListener);

        return placeDAO;
    }
}
