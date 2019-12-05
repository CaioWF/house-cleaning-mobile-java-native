package br.com.ufc.quixada.housecleaning.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.core.app.ActivityCompat;
import br.com.ufc.quixada.housecleaning.transactions.Place;
import br.com.ufc.quixada.housecleaning.view.eventlistener.UpdateCurrentPlaceEventListener;

public class LocationUtil {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Geocoder geocoder;
    private UpdateCurrentPlaceEventListener updateCurrentPlaceEventListener;

    public LocationUtil(UpdateCurrentPlaceEventListener updateCurrentPlaceEventListener) {
        this.updateCurrentPlaceEventListener = updateCurrentPlaceEventListener;
    }

    public LocationUtil() {}

    public boolean checkGPSPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestGPSPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
        }
    }

    public void useLocation(final Activity activity) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(5 * 1000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        geocoder = new Geocoder(activity, Locale.getDefault());
                        List<Address> addresses;
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (addresses.size() > 0) {
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
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    public LatLng getLatLngFromCityAndNeighborhood(Activity activity, br.com.ufc.quixada.housecleaning.transactions.Address address) {
        geocoder = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses;
        LatLng latLng = null;
        try {
            addresses = geocoder.getFromLocationName(address.getStreet()+", "+
                            address.getNumber()+" - "+address.getPlace().getNeighborhood()+", "
                            +address.getPlace().getCity(), 1);
            latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }

    public void finishUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

}
