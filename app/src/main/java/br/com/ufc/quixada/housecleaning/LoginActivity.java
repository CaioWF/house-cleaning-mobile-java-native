package br.com.ufc.quixada.housecleaning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import br.com.ufc.quixada.housecleaning.dao.PlaceDAO;
import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.PlaceFirebaseDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.UserFirebaseDAO;
import br.com.ufc.quixada.housecleaning.presenter.PlaceEventListener;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;
import br.com.ufc.quixada.housecleaning.view.LoginView;
import br.com.ufc.quixada.housecleaning.view.eventlistener.LoginViewEventListener;

public class LoginActivity extends AppCompatActivity implements LoginViewEventListener {

    private static final String PREFERENCES_FILE = "session_settings";

    private UserDAO userDAO;
    private LoginView loginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        loginView = new LoginView(this);
        loginView.initialize(rootView);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public LoginActivity() {
        super();
    }

    @Override
    public void onClickLoginButton(String email, String password) {
        User user = userDAO.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            SessionUtil.setCurrentUserId(this, user.getId());

            Intent i = new Intent(getApplicationContext(), HomeActivity.class);

            startActivity(i);

            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Você errou o email ou senha", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClickGoToRegistrationButton() {
        Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);

        startActivity(i);

        finish();
    }
}
