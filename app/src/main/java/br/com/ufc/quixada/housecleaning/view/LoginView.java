package br.com.ufc.quixada.housecleaning.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.view.eventlistener.LoginViewEventListener;

public class LoginView extends GenericView {

    private EditText emailField;

    private EditText passwordField;

    private Button loginButton;

    private Button goToRegistrationButton;

    private LoginViewEventListener loginViewEventListener;

    public LoginView(LoginViewEventListener loginViewEventListener) {
        this.loginViewEventListener = loginViewEventListener;
    }

    @Override
    public void initialize(View rootView) {
        super.initialize(rootView);

        emailField = rootView.findViewById(R.id.id_email_login);

        passwordField = rootView.findViewById(R.id.id_pass_login);

        loginButton = rootView.findViewById(R.id.id_btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                loginViewEventListener.onClickLoginButton(email, password);
            }
        });

        goToRegistrationButton = rootView.findViewById(R.id.id_btn_ir_para_cadastro);
        goToRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewEventListener.onClickGoToRegistrationButton();
            }
        });
    }

    @Override
    public int getLayoutFile() {
        return R.layout.activity_login;
    }

}
