package br.com.ufc.quixada.housecleaning.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.transactions.Address;
import br.com.ufc.quixada.housecleaning.transactions.CleaningService;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.view.eventlistener.RequestCleaningServiceViewEventListener;

public class RequestCleaningServiceView extends GenericView {

    private EditText areaSizeField;

    private Button setCleaningServiceDateButton;
    private Button setCleaningServiceTimeButton;

    private Spinner placeField;

    private EditText streetField;

    private EditText numberField;

    private EditText complementField;

    private EditText additionalCommentsField;

    private Button saveButton;

    private List<Place> places;
    private ArrayAdapter<Place> placeArrayAdapter;
    private RequestCleaningServiceViewEventListener requestCleaningServiceViewEventListener;

    public RequestCleaningServiceView(RequestCleaningServiceViewEventListener requestCleaningServiceViewEventListener) {
        places = new ArrayList<>();
        this.requestCleaningServiceViewEventListener = requestCleaningServiceViewEventListener;
    }

    @Override
    public void initialize(final View rootView) {
        super.initialize(rootView);

        areaSizeField = rootView.findViewById(R.id.area_size_field);

        setCleaningServiceDateButton = rootView.findViewById(R.id.set_cleaning_service_date_button);
        setCleaningServiceDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        setCleaningServiceDateButton.setText(formatDate(dayOfMonth, month + 1, year));
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        setCleaningServiceTimeButton = rootView.findViewById(R.id.set_cleaning_service_time_button);
        setCleaningServiceTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setCleaningServiceTimeButton.setText(formatTime(hourOfDay, minute));
                    }
                }, hourOfDay, minute, true);
                timePickerDialog.show();
            }
        });

        placeArrayAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_dropdown_item_1line, places);

        placeField = rootView.findViewById(R.id.place_field);
        placeField.setAdapter(placeArrayAdapter);

        streetField = rootView.findViewById(R.id.street_field);

        numberField = rootView.findViewById(R.id.number_field);

        complementField = rootView.findViewById(R.id.complement_field);

        additionalCommentsField = rootView.findViewById(R.id.additional_comments_field);

        saveButton = rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    float cleaningAreaSize = Float.parseFloat(areaSizeField.getText().toString());
                    float price = cleaningAreaSize * 10;
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(setCleaningServiceDateButton.getText().toString());
                    Date time = new SimpleDateFormat("hh:mm").parse(setCleaningServiceTimeButton.getText().toString());
                    Place place = (Place) placeField.getSelectedItem();
                    String street = streetField.getText().toString();
                    int number = Integer.parseInt(numberField.getText().toString());
                    String complement = complementField.getText().toString();
                    Address address = new Address(place, street, number, complement);
                    String additionalComments = additionalCommentsField.getText().toString();

                    CleaningService cleaningService = new CleaningService();
                    cleaningService.setCleaningAreaSize(cleaningAreaSize);
                    cleaningService.setPrice(price);
                    cleaningService.setDate(joinDateAndTime(date, time));
                    cleaningService.setAddress(address);
                    cleaningService.setAdditionalComments(additionalComments);

                    requestCleaningServiceViewEventListener.onClickSave(cleaningService);
                } catch (NumberFormatException | ParseException e) {
                    Toast.makeText(rootView.getContext(), "One or more field values are invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getLayoutFile() {
        return R.layout.activity_request_cleaning_service;
    }

    public void listAllPlaces(List<Place> places) {
        this.places.clear();

        for (Place place : places)
            this.places.add(place);

        placeArrayAdapter.notifyDataSetChanged();
    }

    private String formatDate(int dayOfMonth, int month, int year) {
        String date = "";

        date += dayOfMonth + "/";

        if (month < 10)
            date += "0" + month + "/";
        else date += month + "/";

        date += year;

        return date;
    }

    private String formatTime(int hourOfDay, int minute) {
        String time = "";

        if (hourOfDay < 10)
            time += "0" + hourOfDay + ":";
        else time += hourOfDay + ":";

        if (minute < 10)
            time += "0" + minute;
        else time += minute;

        return time;
    }

    private Date joinDateAndTime(Date date, Date time) {
        java.util.Calendar calendarDate = java.util.Calendar.getInstance();
        calendarDate.setTime(date);

        java.util.Calendar calendarTime = java.util.Calendar.getInstance();
        calendarTime.setTime(time);

        java.util.Calendar calendarDateTime = java.util.Calendar.getInstance();
        calendarDateTime.set(java.util.Calendar.DAY_OF_MONTH, calendarDate.get(java.util.Calendar.DAY_OF_MONTH));
        calendarDateTime.set(java.util.Calendar.MONTH, calendarDate.get(java.util.Calendar.MONTH));
        calendarDateTime.set(java.util.Calendar.YEAR, calendarDate.get(java.util.Calendar.YEAR));
        calendarDateTime.set(java.util.Calendar.HOUR, calendarTime.get(java.util.Calendar.HOUR));
        calendarDateTime.set(java.util.Calendar.MINUTE, calendarTime.get(java.util.Calendar.MINUTE));
        calendarDateTime.set(java.util.Calendar.SECOND, calendarTime.get(java.util.Calendar.SECOND));

        return calendarDateTime.getTime();
    }

}
