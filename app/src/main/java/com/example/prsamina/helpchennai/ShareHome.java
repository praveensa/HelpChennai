package com.example.prsamina.helpchennai;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShareHome extends Activity implements OnMapReadyCallback,GoogleMap.OnMarkerDragListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks ,LocationListener
{
    public ProgressDialog progressDialog;
    private GoogleMap googleMap;
    private MapFragment mapFragment;
    double latitude,longitude;
    public static TextView name;
    public TextView phone;
    public Button register;
    public final String url="http://helpchennai.netau.net/insertdata.php";
    JSONParser jsonParser=new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share);
        mapFragment=new MapFragment();
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.map,mapFragment).commit();
        //noinspection deprecation
//        mapFragment.getMap().setOnMarkerDragListener(this);
        GPSTracker gps=new GPSTracker(this);
        if(gps.isLocationAvailabe())
        {
            latitude=gps.getLatitude();
            longitude=gps.getLongitude();
        }
        else
        {
            gps.showSettingAlert();
        }

        mapFragment.getMapAsync(this);

        name=(TextView)findViewById(R.id.name);
        phone=(TextView)findViewById(R.id.phone);
        register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
               new  InsertData(name.getText().toString(),phone.getText().toString(),latitude,longitude).execute();
            }
        });


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setOnMarkerDragListener(this);
        MarkerOptions mo=new MarkerOptions();
        mo.title("MyHome");
        LatLng currentPosition=new LatLng(latitude,longitude);
        mo.position(currentPosition).draggable(true);
       // mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.sh));
        googleMap.addMarker(mo);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,16));

    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        googleMap.clear();

        MarkerOptions mp = new MarkerOptions();

        mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

        mp.title("my position");

        googleMap.addMarker(mp);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 16));

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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng  homePosition=marker.getPosition();
        latitude=homePosition.latitude;
        longitude=homePosition.longitude;
        Toast.makeText(this,"testing dragable marker",Toast.LENGTH_LONG).show();
    }

    private class InsertData extends AsyncTask<String,String,String> {
        String name;
        String phone;
        Double lat,lon;

        public InsertData(String s, String s1,Double lat,Double lon) {
            this.name=s;
            this.phone=s1;
            this.lat=lat;
            this.lon=lon;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(ShareHome.this);
            progressDialog.setMessage("Inserting Data");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //noinspection deprecation
            List<NameValuePair> param  =new ArrayList<NameValuePair>();
            //noinspection deprecation
            param.add(new BasicNameValuePair("name",name));
            //noinspection deprecation
            param.add(new BasicNameValuePair("phone",phone));
            //noinspection deprecation
            param.add(new BasicNameValuePair("latitude",String.valueOf(lat)));
            //noinspection deprecation
            param.add(new BasicNameValuePair("longitude", String.valueOf(lon)));
            JSONObject jsonObject=jsonParser.makeHttpRequest(url, "GET", param);
            try {
                int success=jsonObject.getInt("status");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
        }
    }
}