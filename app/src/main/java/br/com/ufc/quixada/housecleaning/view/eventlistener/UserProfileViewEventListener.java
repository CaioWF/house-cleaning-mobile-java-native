package br.com.ufc.quixada.housecleaning.view.eventlistener;

import android.net.Uri;

import br.com.ufc.quixada.housecleaning.transactions.User;

public interface UserProfileViewEventListener {

    void onClickLoadPhotoButton();

    void onClickSave(User user, Uri newUserPhotoUri);

}
