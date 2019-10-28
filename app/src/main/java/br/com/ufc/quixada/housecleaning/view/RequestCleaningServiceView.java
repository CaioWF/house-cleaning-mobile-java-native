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

import java.util.ArrayList;
import java.util.List;

import br.com.ufc.quixada.housecleaning.R;
import br.com.ufc.quixada.housecleaning.transactions.Place;

public class RequestCleaningServiceView extends GenericView {

    private EditText areaSizeField;

    private Button setCleaningServiceDateButton;
    private Button setCleaningServiceTimeButton;

    private Spinner placeField;

    private EditText streetField;

    private EditText numberField;

    private EditText complementField;

    private EditText additionalCommentsField;

    private List<Place> places;
    private ArrayAdapter<Place> placeArrayAdapter;

    public RequestCleaningServiceView() {
        places = new ArrayList<>();
    }

    @Override
    public void initialize(View rootView) {
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

}
