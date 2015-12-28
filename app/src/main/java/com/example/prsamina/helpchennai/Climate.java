package com.example.prsamina.helpchennai;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
public class Climate extends Activity implements WeatherServiceCallBack {
    private ImageView climatImg;
    private TextView temperature;
    private TextView condition;
    private TextView location;
    private ProgressDialog progressBar;
    private YahooWeatherService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.climate);

        climatImg=(ImageView)findViewById(R.id.climateimg);
        temperature=(TextView)findViewById(R.id.temperature);
        condition=(TextView)findViewById(R.id.condition);
        location=(TextView)findViewById(R.id.location);

        service=new YahooWeatherService(this);
        progressBar=new ProgressDialog(this);
        progressBar.setMessage("Loading!!..........");
        progressBar.show();
        service.refreshWeather("Chennai");



    }



    @Override
    public void serviceSuccess(Channel channel) {
    progressBar.hide();
        int rId=getResources().getIdentifier("drawable/icon_"+channel.getItems().getCondition().getCode(),null,"com.example.prsamina.helpchennai");
        Drawable drawable=getResources().getDrawable(rId,null);
        climatImg.setImageDrawable(drawable);
        location.setText("Chennai");
        temperature.setText(channel.getItems().getCondition().getTemp() + "\u00B0" + channel.getUnit().getTempUnit());
        condition.setText(channel.getItems().getCondition().getDesc());
    }

    @Override
    public void serviceFailure(Exception e) {
        progressBar.hide();
        e.printStackTrace();
        Toast.makeText(this,e.toString(), LENGTH_LONG).show();

    }

}
