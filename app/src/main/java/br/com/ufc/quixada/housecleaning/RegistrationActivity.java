package br.com.ufc.quixada.housecleaning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.UserFirebaseDAO;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;
import br.com.ufc.quixada.housecleaning.view.RegistrationView;
import br.com.ufc.quixada.housecleaning.view.eventlistener.RegistrationViewEventListener;

public class RegistrationActivity extends AppCompatActivity implements RegistrationViewEventListener {

    private UserDAO userDAO;
    private RegistrationView registrationView;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "RegistrationActivity";
    private ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            SessionUtil.setCurrentUserId(RegistrationActivity.this, firebaseAuth.getCurrentUser().getUid());
            Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

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

        firebaseAuth = FirebaseAuth.getInstance();


        View rootView = getWindow().getDecorView().getRootView();

        registrationView = new RegistrationView(this);
        registrationView.initialize(rootView);

        progressBar = findViewById(R.id.progressBarRegistration);
    }

    @Override
    public void onClickRegistrationButton(final String name, final String email, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    userDAO.create(new User(user.getUid(), name, email, password));
                    SessionUtil.setCurrentUserId(RegistrationActivity.this, user.getUid());
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(RegistrationActivity.this, "Falha ao registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClickGoToLoginButton() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivity(intent);

        finish();
    }
}
