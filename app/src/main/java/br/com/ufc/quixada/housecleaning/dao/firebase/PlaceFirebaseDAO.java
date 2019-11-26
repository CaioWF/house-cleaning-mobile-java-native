package br.com.ufc.quixada.housecleaning.dao.firebase;

import br.com.ufc.quixada.housecleaning.dao.PlaceDAO;
import br.com.ufc.quixada.housecleaning.presenter.PlaceEventListener;
import br.com.ufc.quixada.housecleaning.transactions.Place;

public class PlaceFirebaseDAO extends GenericFirebaseDAO<Place> implements PlaceDAO {

    private static PlaceDAO placeDAO = null;

    private PlaceFirebaseDAO(PlaceEventListener eventListener) {
        super("places", eventListener, Place.class);
    }

    public static PlaceDAO getInstance(PlaceEventListener eventListener) {
        if (placeDAO == null) {
            placeDAO = new PlaceFirebaseDAO(eventListener);
        }

        return placeDAO;
    }
}
