package br.com.ufc.quixada.housecleaning.view.eventlistener;

import android.net.Uri;

import java.util.List;

import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.transactions.User;

public interface UserProfileViewEventListener {

    void onClickLoadPhotoButton();

    void onClickSelectPlacesButton(List<Place> userServicePlaces);

    void onClickSave(User user, Uri newUserPhotoUri);

}
