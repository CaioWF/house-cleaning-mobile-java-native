package br.com.ufc.quixada.housecleaning;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.ufc.quixada.housecleaning.dao.WorkerDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.WorkerMemoryDAO;
import br.com.ufc.quixada.housecleaning.presenter.WorkerEventListener;
import br.com.ufc.quixada.housecleaning.transactions.Worker;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        WorkerListFragment.OnFragmentInteractionListener, WorkerEventListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.id_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        WorkerDAO workerDAO = WorkerMemoryDAO.getInstance(this);
        workerDAO.create(new Worker("", "Just Testing", (float) 4.5));
        workerDAO.create(new Worker("", "Just Testing", (float) 4.5));
        workerDAO.create(new Worker("", "Just Testing", (float) 4.5));
        workerDAO.create(new Worker("", "Just Testing", (float) 4.5));
        workerDAO.create(new Worker("", "Just Testing", (float) 4.5));
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        switch (menuItem.getItemId()) {
            case R.id.id_bottom_home:
                selectedFragment = new WorkerListFragment();
                getSupportActionBar().setTitle("House Cleaning");
                break;
            case R.id.id_bottom_workers:
                selectedFragment = new WorkerListFragment();
                getSupportActionBar().setTitle("Solicitar Serviço");
                /*Fragment workersFragment = WorkerListFragment.newInstance("param1", "param2");
                openFragment(workersFragment);*/
                break;
            case R.id.id_bottom_requestes:
                selectedFragment = new WorkerListFragment();
                getSupportActionBar().setTitle("Solicitações de Serviço");
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_fragment_container, selectedFragment).commit();
        return true;
    }

    /*private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
