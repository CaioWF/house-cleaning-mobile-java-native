package br.com.ufc.quixada.housecleaning.view.eventlistener;

public interface RegistrationViewEventListener {

    void onClickRegistrationButton(String name, String email, String password);

    void onClickGoToLoginButton();

}
