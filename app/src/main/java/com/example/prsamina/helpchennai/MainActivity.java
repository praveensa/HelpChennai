package com.example.prsamina.helpchennai;
//Certificate fingerprint (SHA1):

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prsamina.helpchennai.adaptor.NavDrawerListAdapter;
import com.example.prsamina.helpchennai.model.NavDrawerItems;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.prsamina.helpchennai.R.string.app_name;

/**
 * Created by prsamina on 12/19/2015.
 */
public class MainActivity extends Activity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private int tempOption = -1;
    //Fussed Location Provider
    private  double current_long,current_lat;
    GoogleApiClient googleApiClient;
    //LocateSafePlaces
    JSONParser jsonParser=new JSONParser();
    public final String url= "http://helpchennai.netau.net/";
    ArrayList<HashMap<String,String>> safePlaces=new ArrayList<>();
    ArrayList<HashMap<String,String>> supplyCamps=new ArrayList<>();



    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItems> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private MapFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Adding MapFragment
        fragment = new MapFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.map, fragment).commit();

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItems>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItems(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItems(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItems(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItems(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new NavDrawerItems(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setIcon(R.drawable.safe);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                getActionBar().setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, app_name, app_name) {
            public void onDrawerClosed(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    getActionBar().setTitle(mTitle);
                }
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        if(googleApiClient==null)
        {
            googleApiClient=new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayView(0);
    }

    void displayView(int position) {
        mDrawerTitle = navMenuTitles[position];
        switch (position) {
            case 0: {
               // fragment = new MapFragment();
                //FragmentManager fragmentManager = getFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.map, fragment).commit();
                tempOption=0;
                GPSTracker gps =new GPSTracker(this);
                if(gps.isLocationAvailabe())
                {
                    current_lat=Float.parseFloat(String.valueOf(gps.getLatitude()));
                    current_long= (float) gps.getLongitude();
                }
                fragment.getMapAsync(this);
                break;
            }
            case 1: {
                tempOption=1;
                fragment.getMapAsync(this);
                break;
            }
            case 2: {
                startActivity(new Intent(this, ShareHome.class));
                mDrawerLayout.closeDrawer(mDrawerList);
                break;

            }
            case 3: {
                tempOption = 3;
                 startActivity(new Intent(this,TrackPerson.class));
                 mDrawerLayout.closeDrawer(mDrawerList);
                break; }
            case 4: //fragment= new Climate();
                    startActivity(new Intent(this,Climate.class));
                     mDrawerLayout.closeDrawer(mDrawerList);

                break;
            default:
                break;
        }
            //MapFragment mapFragment =new MapFragment();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (tempOption == 1) {
            googleMap.clear();
            new SupplyCamp().execute();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_lat,current_long), 12));
        }
        else {
            //For displaying the safe location from database
            safeLocate(googleMap);
            //googleMap.addMarker(new MarkerOptions().title("hereIam").position(new LatLng(current_lat, current_long)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current_lat,current_long),10));

        }
    }

    private void safeLocate(GoogleMap googleMap) {

        new LoadSafePlaces().execute();


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder close=new AlertDialog.Builder(MainActivity.this);
        close.setTitle("Exiting the App ?");
        close.setMessage("Are you sure wanna go out?");
        close.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        close.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        close.show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        //noinspection ResourceType
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (mLastLocation != null) {
            current_lat=Float.parseFloat(String.valueOf(mLastLocation.getLatitude()));
            current_long=Float.parseFloat(String.valueOf(mLastLocation.getLongitude()));
        }



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private class SlideMenuClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }

    private class LoadSafePlaces extends AsyncTask<String,String,String>{

        ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setIndeterminate(false);
            progressDialog.setMessage("Fetching Data from server ...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            //noinspection deprecation
            List<NameValuePair> parms=new ArrayList<NameValuePair>();
            JSONObject jsonObject=jsonParser.makeHttpRequest(url+"RetrieveData.php","GET",parms);
               try {
                    int success = jsonObject.getInt("status");
                    if (success == 0) {
                        JSONArray location = jsonObject.getJSONArray("location");
                        Log.d("All Location", location.toString());

                        for (int i = 0; i < location.length(); i++) {
                            JSONObject c = location.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("phone", c.getString("phone"));
                            map.put("latitude", String.valueOf(c.getDouble("latitude")));
                            map.put("longitude", String.valueOf(c.getDouble("longitude")));
                            safePlaces.add(map);
                        }

                    }


                else
                {
                    HashMap<String,String> map = new HashMap<>();
                    map.put("phone","1234567");
                    map.put("latitude",String.valueOf(12.8983473));
                    map.put("longitude",String.valueOf(77.6003738));
                    safePlaces.add(map);

                }

            } catch (JSONException e) {
                   Log.e("error","hello world");
                   e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.cancel();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<safePlaces.size();i++)
                    {
                        //LatLng temp=new LatLng(0,0);
                        LatLng temp=new LatLng(Float.parseFloat(safePlaces.get(i).get("latitude")),Float.parseFloat(safePlaces.get(i).get("longitude")));
                        //noinspection deprecation
                        fragment.getMap().addMarker(new MarkerOptions().position(temp));
                    }
                    Toast.makeText(MainActivity.this,"The fire rises",Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private class SupplyCamp extends AsyncTask <String,String,String> {
        ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading SupplyCamps...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            List<NameValuePair> parms=new ArrayList<NameValuePair>();
            JSONObject jsonObject=jsonParser.makeHttpRequest(url+"supplies.php","GET",parms);
            try {
                int status=jsonObject.getInt("status");
                if(status==0)
                {
                    JSONArray location = jsonObject.getJSONArray("Details");
                    Log.d("All Location", location.toString());

                    for (int i = 0; i < location.length(); i++) {
                        JSONObject c = location.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("phone", c.getString("phone"));
                        map.put("latitude", String.valueOf(c.getDouble("latitude")));
                        map.put("longitude", String.valueOf(c.getDouble("longitude")));
                        supplyCamps.add(map);
                    }

                }
                else
                {
                    HashMap<String,String> map = new HashMap<>();
                    map.put("phone","1234567");
                    map.put("latitude",String.valueOf(12.8983473));
                    map.put("longitude",String.valueOf(77.6003738));
                    supplyCamps.add(map);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.cancel();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < supplyCamps.size(); i++) {
                        //LatLng temp=new LatLng(0,0);
                        LatLng temp = new LatLng(Float.parseFloat(supplyCamps.get(i).get("latitude")), Float.parseFloat(supplyCamps.get(i).get("longitude")));
                        //noinspection deprecation
                        fragment.getMap().addMarker(new MarkerOptions().position(temp));
                    }
                }
            });

        }
    }
}





