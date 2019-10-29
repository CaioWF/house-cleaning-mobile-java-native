package br.com.ufc.quixada.housecleaning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import br.com.ufc.quixada.housecleaning.transactions.Address;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.transactions.User;
import br.com.ufc.quixada.housecleaning.util.SessionUtil;
import br.com.ufc.quixada.housecleaning.view.RequestCleaningServiceView;

public class RequestCleaningServiceActivity extends AppCompatActivity implements UserEventListener, PlaceEventListener, CleaningServiceEventListener {

    private UserDAO userDAO;
    private PlaceDAO placeDAO;
    private CleaningServiceDAO cleaningServiceDAO;
    private RequestCleaningServiceView requestCleaningServiceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_cleaning_service);

        userDAO = UserMemoryDAO.getInstance(this);

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
        EditText areaSizeField = findViewById(R.id.area_size_field);
        Button setCleaningServiceDateButton = findViewById(R.id.set_cleaning_service_date_button);
        Button setCleaningServiceTimeButton = findViewById(R.id.set_cleaning_service_time_button);
        Spinner placeField = findViewById(R.id.place_field);
        EditText streetField = findViewById(R.id.street_field);
        EditText numberField = findViewById(R.id.number_field);
        EditText complementField = findViewById(R.id.complement_field);
        EditText additionalCommentsField = findViewById(R.id.additional_comments_field);

        try {
            float cleaningAreaSize = Float.parseFloat(areaSizeField.getText().toString());
            float price = cleaningAreaSize * 10;
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(setCleaningServiceDateButton.getText().toString());
            Date time = new SimpleDateFormat("hh:mm").parse(setCleaningServiceTimeButton.getText().toString());
            Date fullDate = joinDateAndTime(date, time);
            int responsibleId = getIntent().getExtras().getInt("user_id");
            User responsible = userDAO.findById(responsibleId);
            User requester = getCurrentUser();
            Place place = (Place) placeField.getSelectedItem();
            String street = streetField.getText().toString();
            int number = Integer.parseInt(numberField.getText().toString());
            String complement = complementField.getText().toString();
            Address address = new Address(place, street, number, complement);
            String additionalComments = additionalCommentsField.getText().toString();

            CleaningService cleaningService = new CleaningService();
            cleaningService.setCleaningAreaSize(cleaningAreaSize);
            cleaningService.setPrice(price);
            cleaningService.setDate(fullDate);
            cleaningService.setResponsible(responsible);
            cleaningService.setRequester(requester);
            cleaningService.setAddress(address);
            cleaningService.setStatus(CleaningService.Status.PENDENT);
            cleaningService.setAdditionalComments(additionalComments);

            cleaningServiceDAO.create(cleaningService);

            finish();
        } catch (ParseException | NumberFormatException e) {
            Toast.makeText(this, "One or more field values are invalid", Toast.LENGTH_SHORT).show();
        }
    }

    private User getCurrentUser() {
        int currentUserId = SessionUtil.getCurrentUserId(this);

        return userDAO.findById(currentUserId);
    }

    private Date joinDateAndTime(Date date, Date time) {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);

        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(time);

        Calendar calendarDateTime = Calendar.getInstance();
        calendarDateTime.set(Calendar.DAY_OF_MONTH, calendarDate.get(Calendar.DAY_OF_MONTH));
        calendarDateTime.set(Calendar.MONTH, calendarDate.get(Calendar.MONTH));
        calendarDateTime.set(Calendar.YEAR, calendarDate.get(Calendar.YEAR));
        calendarDateTime.set(Calendar.HOUR, calendarTime.get(Calendar.HOUR));
        calendarDateTime.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
        calendarDateTime.set(Calendar.SECOND, calendarTime.get(Calendar.SECOND));

        return calendarDateTime.getTime();
    }
}
