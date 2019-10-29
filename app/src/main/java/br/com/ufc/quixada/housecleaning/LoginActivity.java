package br.com.ufc.quixada.housecleaning;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.UserMemoryDAO;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.User;

public class LoginActivity extends AppCompatActivity implements UserEventListener {

    private Button btnLogin;
    private Button btnGoToRegistration;
    private EditText textEmail;
    private EditText textPass;
    private static final String ARQUIVO_PREFERENCIA = "ArqPref";
    private UserDAO userDAO = UserMemoryDAO.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                for (User u: users){
                    if(email.equals(u.getEmail()) && password.equals(u.getPassword())) {
                        user = u;
                    }
                }
                if (user != null) {
                    SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", user.getName());
                    editor.putString("email", user.getEmail());
                    editor.putString("pass", user.getPassword());
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                    finish();
                } else{
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
}
