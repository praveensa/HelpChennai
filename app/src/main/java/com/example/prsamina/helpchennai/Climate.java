package com.example.prsamina.helpchennai;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prsamina.helpchennai.data.Channel;
import com.example.prsamina.helpchennai.service.WeatherServiceCallBack;
import com.example.prsamina.helpchennai.service.YahooWeatherService;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by prsamina on 12/20/2015.
 */
public class Climate extends Fragment implements WeatherServiceCallBack {
    private ImageView climatImg;
    private TextView temperature;
    private TextView condition;
    private TextView location;
    private ProgressDialog progressBar;
    private YahooWeatherService service;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View v=  inflater.inflate(R.layout.climate, container, false);
        //WebView webView=(WebView)v.findViewById(R.id.webView);
        //webView.loadUrl("http://www.accuweather.com/en/in/chennai/206671/weather-forecast/206671");
        climatImg=(ImageView)v.findViewById(R.id.climateimg);
        temperature=(TextView)v.findViewById(R.id.temperature);
        condition=(TextView)v.findViewById(R.id.condition);
        location=(TextView)v.findViewById(R.id.location);

        service=new YahooWeatherService(this);
        progressBar=new ProgressDialog(getActivity());
        progressBar.setMessage("Loading!!..........");
        progressBar.show();
        service.refreshWeather("Chennai");


        return  v;
    }

    @Override
    public void serviceSuccess(Channel channel) {
    progressBar.hide();
        int rId=getResources().getIdentifier("drawable/icon_"+channel.getItems().getCondition().getCode(),null,"com.example.prsamina.helpchennai");
        Drawable drawable=getResources().getDrawable(rId,null);
        climatImg.setImageDrawable(drawable);
        location.setText("Chennai");
        temperature.setText(channel.getItems().getCondition().getTemp()+"\u00B0"+channel.getUnit().getTempUnit());
        condition.setText(channel.getItems().getCondition().getDesc());
    }

    @Override
    public void serviceFailure(Exception e) {
        progressBar.hide();
        e.printStackTrace();
        Toast.makeText(getActivity(),e.toString(), LENGTH_LONG).show();

    }
}
