package com.example.prsamina.helpchennai;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by prsamina on 12/20/2015.
 */
public class Climate extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v=  inflater.inflate(R.layout.climate, container, false);
        WebView webView=(WebView)v.findViewById(R.id.webView);
        webView.loadUrl("http://www.accuweather.com/en/in/chennai/206671/weather-forecast/206671");
        return  v;
    }
}
