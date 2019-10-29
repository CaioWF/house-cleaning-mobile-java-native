package br.com.ufc.quixada.housecleaning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.UserMemoryDAO;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.User;

public class RegistrationActivity extends AppCompatActivity implements UserEventListener {

    private Button btnRegistration;
    private Button btnGoToLogin;
    private EditText textName;
    private EditText textEmail;
    private EditText textPass;
    private CheckBox confirmRegistration;
    private UserDAO userDAO = UserMemoryDAO.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        textName = findViewById(R.id.id_name_registration);
        textEmail = findViewById(R.id.id_email_registration);
        textPass = findViewById(R.id.id_senha_registration);
        confirmRegistration = findViewById(R.id.id_checkbox_confirm_registration);

        btnRegistration = findViewById(R.id.id_btn_registration);
        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmRegistration.isChecked() == true) {
                    String name = textName.getText().toString();
                    String email = textEmail.getText().toString();
                    String password = textPass.getText().toString();
                    userDAO.create(new User(name, email, password));
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(RegistrationActivity.this, "Você precisa aceitar os Termos", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnGoToLogin = (Button) findViewById(R.id.id_btn_ir_para_login);
        btnGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
