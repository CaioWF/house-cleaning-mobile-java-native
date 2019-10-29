package br.com.ufc.quixada.housecleaning;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

import br.com.ufc.quixada.housecleaning.dao.CleaningServiceDAO;
import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.CleaningServiceMemoryDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.UserMemoryDAO;
import br.com.ufc.quixada.housecleaning.presenter.CleaningServiceEventListener;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.transactions.User;

public class LoginActivity extends AppCompatActivity implements UserEventListener, CleaningServiceEventListener {

    private Button btnLogin;
    private Button btnGoToRegistration;
    private EditText textEmail;
    private EditText textPass;
    private static final String ARQUIVO_PREFERENCIA = "session_settings";
    private UserDAO userDAO = UserMemoryDAO.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeDatabase();

        textEmail = findViewById(R.id.id_email_login);
        textPass = findViewById(R.id.id_pass_login);

        btnLogin = findViewById(R.id.id_btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = textEmail.getText().toString();
                String password = textPass.getText().toString();
                List<User> users = userDAO.findAll();
                User user = null;
                for (User u : users) {
                    if (email.equals(u.getEmail()) && password.equals(u.getPassword())) {
                        user = u;
                    }
                }
                if (user != null) {
                    SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("user_id", user.getId());
                    editor.putString("name", user.getName());
                    editor.putString("email", user.getEmail());
                    editor.putString("pass", user.getPassword());
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Você errou o email ou senha", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnGoToRegistration = findViewById(R.id.id_btn_ir_para_cadastro);
        btnGoToRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void initializeDatabase() {
//      Users

        UserDAO userDAO = UserMemoryDAO.getInstance(this);

        User user1 = new User("", "John Doe", "", "", true, 4.5f);
        User user2 = new User("", "Jane Doe", "", "", false, 4.5f);

        userDAO.create(user1);
        userDAO.create(user1);
        userDAO.create(user1);
        userDAO.create(user1);
        userDAO.create(user1);
        userDAO.create(user2);
        userDAO.create(user2);
        userDAO.create(user2);
        userDAO.create(user2);


//      Cleaning Services

        CleaningServiceDAO cleaningServiceDAO = CleaningServiceMemoryDAO.getInstance(this);

        CleaningService cleaningService = new CleaningService();
        cleaningService.setDate(new Date());
        cleaningService.setResponsible(user1);
        cleaningService.setRequester(user2);
        cleaningService.setStatus(CleaningService.Status.PENDENT);

        cleaningServiceDAO.create(cleaningService);
        cleaningServiceDAO.create(cleaningService);
        cleaningServiceDAO.create(cleaningService);
        cleaningServiceDAO.create(cleaningService);
    }
}
