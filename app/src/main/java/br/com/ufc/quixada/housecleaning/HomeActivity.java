package br.com.ufc.quixada.housecleaning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import br.com.ufc.quixada.housecleaning.adapter.WorkerListAdapter;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        WorkerListFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener, CleaningServiceSolicitationListFragment.OnFragmentInteractionListener {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);

        SessionUtil.setCurrentUserId(HomeActivity.this, firebaseAuth.getCurrentUser().getUid());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
            }
        };

        bottomNavigationView = findViewById(R.id.id_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        openFragment(new HistoryFragment(), "fragHistory");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        hideSearchButton();

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                WorkerListFragment workerListFragment = (WorkerListFragment) getSupportFragmentManager().findFragmentById(R.id.id_fragment_container);
                ((WorkerListAdapter) workerListFragment.getWorkerListView().getWorkerListAdapter()).getFilter().filter(s);

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_profile:
                Intent userProfile = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(userProfile);

                return true;

            case R.id.menu_about:
                Intent about = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(about);

                return true;

            case R.id.menu_logout:
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                firebaseAuth.signOut();
                startActivity(login);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        String tag = "";

        switch (menuItem.getItemId()) {
            case R.id.id_bottom_home:
                hideSearchButton();
                selectedFragment = new HistoryFragment();
                tag = "fragHistory";
                getSupportActionBar().setTitle("House Cleaning");

                break;

            case R.id.id_bottom_workers:
                showSearchButton();
                selectedFragment = new WorkerListFragment();
                tag = "fragWorkers";
                getSupportActionBar().setTitle("Solicitar Serviço");

                break;

            case R.id.id_bottom_requestes:
                hideSearchButton();
                selectedFragment = new CleaningServiceSolicitationListFragment();
                tag = "fragRequests";
                getSupportActionBar().setTitle("Solicitações de Serviço");

                break;
        }
        openFragment(selectedFragment, tag);
        return true;
    }

    private void hideSearchButton() {
        toolbar.getMenu().findItem(R.id.menu_search).setVisible(false);
    }

    private void showSearchButton() {
        toolbar.getMenu().findItem(R.id.menu_search).setVisible(true);
    }

    private void openFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_fragment_container, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
