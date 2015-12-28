package com.example.prsamina.helpchennai;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by prsamina on 12/26/2015.
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;

    LocationManager locationManager;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean locationAvailabe = false;

    Location location;
    double latitude, longitude;
    int MIN_DISTANCE_FOR_UPDATE = 10;
    int MIN_TIME_FOR_UPDATE = 1000 * 60;

    public GPSTracker(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }

    private Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled=locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
            isNetworkEnabled=locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
               // showSettingAlert();

            } else {
                locationAvailabe = true;
                if (isNetworkEnabled == true) {

                    //noinspection ResourceType
                    locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                    Log.d("Network", "Network");
                    if(locationManager!=null)
                    {
                        //noinspection ResourceType
                        location=locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                        if(location!=null)
                        {
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();

                        }
                    }
            }
                 if (isGPSEnabled == true) {

                    //noinspection ResourceType
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                    Log.d("Network", "Network");
                    if(locationManager!=null)
                    {
                        //noinspection ResourceType
                        location=locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                        if(location!=null)
                        {
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();

                        }
                    }
                }
            }
        }catch (Exception e)
        {

            e.printStackTrace();
        }
        return  location;
    }

    public void showSettingAlert() {
        AlertDialog.Builder alertBox=new AlertDialog.Builder(mContext);
        alertBox.setTitle("Turn On your PROVIDER BRO");
        alertBox.setMessage("Give me some way to find your location , i am not a God to guess ");
        alertBox.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        alertBox.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertBox.show();



    }


    ////Getter


    public double getLatitude() {
        if(location!=null)
            return location.getLatitude();
        return latitude;
    }

    public double getLongitude() {
        if(location!=null)
            return  location.getLongitude();
        return longitude;
    }

    public boolean isLocationAvailabe() {
        return locationAvailabe;
    }

    void stopUsingGPS()
    {
        if(locationManager!=null)
            //noinspection ResourceType
            locationManager.removeUpdates(GPSTracker.this);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
