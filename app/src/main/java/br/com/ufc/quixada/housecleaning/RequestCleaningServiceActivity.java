package br.com.ufc.quixada.housecleaning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import br.com.ufc.quixada.housecleaning.dao.CleaningServiceDAO;
import br.com.ufc.quixada.housecleaning.dao.PlaceDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.CleaningServiceMemoryDAO;
import br.com.ufc.quixada.housecleaning.dao.memory.PlaceMemoryDAO;
import br.com.ufc.quixada.housecleaning.presenter.CleaningServiceEventListener;
import br.com.ufc.quixada.housecleaning.presenter.PlaceEventListener;
import br.com.ufc.quixada.housecleaning.transactions.Address;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.view.RequestCleaningServiceView;

public class RequestCleaningServiceActivity extends AppCompatActivity implements PlaceEventListener, CleaningServiceEventListener {

    private PlaceDAO placeDAO;
    private CleaningServiceDAO cleaningServiceDAO;
    private RequestCleaningServiceView requestCleaningServiceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_cleaning_service);

        placeDAO = PlaceMemoryDAO.getInstance(this);

        cleaningServiceDAO = CleaningServiceMemoryDAO.getInstance(this);

        View rootView = getWindow().getDecorView().getRootView();

        requestCleaningServiceView = new RequestCleaningServiceView();
        requestCleaningServiceView.initialize(rootView);

        placeDAO.create(new Place("Quixada", "Centro"));
        placeDAO.create(new Place("Quixada", "Centro"));
        placeDAO.create(new Place("Quixada", "Centro"));
        placeDAO.create(new Place("Quixada", "Centro"));
        placeDAO.create(new Place("Quixada", "Centro"));

        List<Place> places = placeDAO.findAll();
        requestCleaningServiceView.listAllPlaces(places);
    }

    public void onClickButtonCancel(View view) {
        finish();
    }

    public void onClickButtonSave(View view) {
        CleaningService cleaningService = new CleaningService();

        EditText areaSizeField = findViewById(R.id.area_size_field);
        Spinner placeField = findViewById(R.id.place_field);
        EditText streetField = findViewById(R.id.street_field);
        EditText numberField = findViewById(R.id.number_field);
        EditText complementField = findViewById(R.id.complement_field);
        EditText additionalCommentsField = findViewById(R.id.additional_comments_field);

        Place place = (Place) placeField.getSelectedItem();
        String street = streetField.getText().toString();
        int number = Integer.parseInt(numberField.getText().toString());
        String complement = complementField.getText().toString();
        String additionalComments = additionalCommentsField.getText().toString();
        float areaSize = Float.parseFloat(areaSizeField.getText().toString());

        cleaningService.setCleaningAreaSize(areaSize);
        cleaningService.setPrice(areaSize * 10);
        cleaningService.setAddress(new Address(place, street, number, complement));
        cleaningService.setAdditionalComments(additionalComments);
        cleaningService.setStatus(CleaningService.Status.PENDENT);

        cleaningServiceDAO.create(cleaningService);
    }

}
