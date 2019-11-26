package br.com.ufc.quixada.housecleaning.view;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.view.eventlistener.RegistrationViewEventListener;

public class RegistrationView extends GenericView {

    private EditText nameField;

    private EditText emailField;

    private EditText passwordField;

    private CheckBox confirmRegistrationCheckBox;

    private Button registrationButton;

    private Button goToLoginButton;

    private RegistrationViewEventListener registrationViewEventListener;

    public RegistrationView(RegistrationViewEventListener registrationViewEventListener) {
        this.registrationViewEventListener = registrationViewEventListener;
    }

    @Override
    public void initialize(final View rootView) {
        super.initialize(rootView);

        nameField = rootView.findViewById(R.id.id_name_registration);

        emailField = rootView.findViewById(R.id.id_email_registration);

        passwordField = rootView.findViewById(R.id.id_senha_registration);

        confirmRegistrationCheckBox = rootView.findViewById(R.id.id_checkbox_confirm_registration);

        registrationButton = rootView.findViewById(R.id.id_btn_registration);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmRegistrationCheckBox.isChecked()) {
                    String name = nameField.getText().toString();
                    String email = emailField.getText().toString();
                    String password = passwordField.getText().toString();

                    registrationViewEventListener.onClickRegistrationButton(name, email, password);
                } else {
                    Toast.makeText(rootView.getContext(), "Você precisa aceitar os termos", Toast.LENGTH_LONG).show();
                }
            }
        });

        goToLoginButton = rootView.findViewById(R.id.id_btn_ir_para_login);
        goToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationViewEventListener.onClickGoToLoginButton();
            }
        });
    }

    @Override
    public int getLayoutFile() {
        return R.layout.activity_registration;
    }

}
