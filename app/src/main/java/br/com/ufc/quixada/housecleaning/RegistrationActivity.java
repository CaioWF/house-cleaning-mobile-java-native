package br.com.ufc.quixada.housecleaning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.UserFirebaseDAO;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.view.RegistrationView;
import br.com.ufc.quixada.housecleaning.view.eventlistener.RegistrationViewEventListener;

public class RegistrationActivity extends AppCompatActivity implements RegistrationViewEventListener {

    private UserDAO userDAO;
    private RegistrationView registrationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userDAO = UserFirebaseDAO.getInstance(new UserEventListener() {
            @Override
            public void onAdded(User user) {

            }

            @Override
            public void onChanged(User user) {

            }

            @Override
            public void onRemoved(User user) {

            }
        });

        View rootView = getWindow().getDecorView().getRootView();

        registrationView = new RegistrationView(this);
        registrationView.initialize(rootView);
    }

    @Override
    public void onClickRegistrationButton(String name, String email, String password) {
        userDAO.create(new User(name, email, password));

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivity(intent);

        finish();
    }

    @Override
    public void onClickGoToLoginButton() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivity(intent);

        finish();
    }
}
