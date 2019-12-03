package br.com.ufc.quixada.housecleaning.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.view.eventlistener.UpdateCurrentPlaceEventListener;

public class LocationUtil {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;
    private UpdateCurrentPlaceEventListener updateCurrentPlaceEventListener;

    public LocationUtil(UpdateCurrentPlaceEventListener updateCurrentPlaceEventListener) {
        this.updateCurrentPlaceEventListener = updateCurrentPlaceEventListener;
    }

    public boolean checkGPSPermission(Activity activity) {
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestGPSPermission(Activity activity) {
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
        }
    }

    public void useLocation(final Activity activity) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(activity, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if (location != null) {
                    geocoder = new Geocoder(activity, Locale.getDefault());
                    List<Address> addresses;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (addresses.size() > 0) {
                            //Toast.makeText(activity, addresses.get(0).getSubAdminArea()+
                            //        ","+addresses.g   et(0).getSubLocality(), Toast.LENGTH_LONG).show();
                            //Log.d("LOCATION================================", addresses.get(0).toString());
                            Place place = new Place();
                            place.setCity(addresses.get(0).getSubAdminArea());
                            place.setNeighborhood(addresses.get(0).getSubLocality());
                            updateCurrentPlaceEventListener.updatePlace(place);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

}
