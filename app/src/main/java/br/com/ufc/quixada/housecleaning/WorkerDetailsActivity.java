package br.com.ufc.quixada.housecleaning;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.firebase.UserFirebaseDAO;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.view.WorkerDetailsView;

public class WorkerDetailsActivity extends AppCompatActivity {

    private UserDAO userDAO;

    private WorkerDetailsView workerDetailsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Detalhes do Prestador de Serviço");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#62DBA8")));

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

        View rootView = getWindow().getDecorView().getRootView();

        workerDetailsView = new WorkerDetailsView();
        workerDetailsView.initialize(rootView);

        String workerId = getIntent().getExtras().getString("user_id");
        User worker = userDAO.findById(workerId);

        workerDetailsView.loadWorkerProfile(worker);
    }

}
