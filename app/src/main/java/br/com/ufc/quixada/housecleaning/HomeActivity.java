package br.com.ufc.quixada.housecleaning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {
    ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_search:
                //Implementar
                return true;
            case R.id.menu_profile:
                //Implementar
                return true;
            case R.id.menu_settings:
                //Implementar
                return true;
            case R.id.menu_about:
                //Implementar
                return true;
            case R.id.menu_logout:
                //Implementar
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
