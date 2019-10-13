package br.com.ufc.quixada.housecleaning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        Spinner spinner = (Spinner) findViewById(R.id.spinner_endereco_registration);
        //List<String> linguagens = new ArrayList<>(Arrays.asList("Java","Python","PHP","Ruby"));

        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
        //        android.R.layout.simple_spinner_item, linguagens );
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //spinner.setAdapter(dataAdapter);

        Button btnGoToLogin = (Button) findViewById(R.id.id_btn_ir_para_login);
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
