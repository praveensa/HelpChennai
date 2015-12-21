package com.example.prsamina.helpchennai;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

public class  ShareHome extends Fragment implements OnMapReadyCallback{
    private GoogleMap googleMap;
    MapView mapView;
    public ShareHome(){}
    public View OnCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.share,container,false);
        mapView=(MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}