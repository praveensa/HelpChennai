package com.example.prsamina.helpchennai;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by prsamina on 12/25/2015.
 */
public class ShareHm extends Fragment implements OnMapReadyCallback {

    private   GoogleMap googleMap;
    MapFragment mapFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.share,container,false);
        mapFragment=new MapFragment();
        mapFragment.getMapAsync(this);
        RelativeLayout relativeLayout=(RelativeLayout)v.findViewById(R.id.inputform);
        //TextView textView=(TextView)v.findViewById(R.id.editText);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.map, mapFragment).commit();

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng Chennai = new LatLng(13.0827, 80.2707);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Chennai, 12));


    }
}
