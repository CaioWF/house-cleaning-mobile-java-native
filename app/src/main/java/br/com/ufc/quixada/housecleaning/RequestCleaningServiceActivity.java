package br.com.ufc.quixada.housecleaning;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import br.com.ufc.quixada.housecleaning.dao.CleaningServiceDAO;
import br.com.ufc.quixada.housecleaning.dao.PlaceDAO;
import br.com.ufc.quixada.housecleaning.dao.UserDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.CleaningServiceMemoryDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.PlaceMemoryDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.UserMemoryDAO;
import br.com.ufc.quixada.housecleaning.presenter.CleaningServiceEventListener;
import br.com.ufc.quixada.housecleaning.presenter.PlaceEventListener;
import br.com.ufc.quixada.housecleaning.presenter.UserEventListener;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;
import br.com.ufc.quixada.housecleaning.view.RequestCleaningServiceView;
import br.com.ufc.quixada.housecleaning.view.eventlistener.RequestCleaningServiceViewEventListener;

public class RequestCleaningServiceActivity extends AppCompatActivity implements UserEventListener, PlaceEventListener, CleaningServiceEventListener, RequestCleaningServiceViewEventListener {

    private UserDAO userDAO;
    private PlaceDAO placeDAO;
    private CleaningServiceDAO cleaningServiceDAO;
    private RequestCleaningServiceView requestCleaningServiceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_cleaning_service);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Solicitar Serviço de Limpeza");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#62DBA8")));

        userDAO = UserMemoryDAO.getInstance(this);

        placeDAO = PlaceMemoryDAO.getInstance(this);

        cleaningServiceDAO = CleaningServiceMemoryDAO.getInstance(this);

        View rootView = getWindow().getDecorView().getRootView();

        requestCleaningServiceView = new RequestCleaningServiceView(this);
        requestCleaningServiceView.initialize(rootView);

        List<Place> places = placeDAO.findAll();
        requestCleaningServiceView.listAllPlaces(places);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickSave(CleaningService cleaningService) {
        int responsibleId = getIntent().getExtras().getInt("user_id");

        User responsible = userDAO.findById(responsibleId);
        User requester = getCurrentUser();

        cleaningService.setResponsible(responsible);
        cleaningService.setRequester(requester);
        cleaningService.setStatus(CleaningService.Status.PENDENT);

        cleaningServiceDAO.create(cleaningService);

        finish();
    }

    private User getCurrentUser() {
        int currentUserId = SessionUtil.getCurrentUserId(this);

        return userDAO.findById(currentUserId);
    }
}
