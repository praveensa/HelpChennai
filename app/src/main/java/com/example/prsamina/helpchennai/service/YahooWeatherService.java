package com.example.prsamina.helpchennai.service;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.prsamina.helpchennai.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by prsamina on 12/21/2015.
 */
public class YahooWeatherService {
    WeatherServiceCallBack weatherServiceCallBack;
    private String location;
    Exception exception;
    public YahooWeatherService(WeatherServiceCallBack w)
    {
        weatherServiceCallBack=w;
    }

   public void refreshWeather(final String location)
    {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String YQL=String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")",location);
                String endpoint=String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
                try {
                    URL url=new URL(endpoint);
                    URLConnection urlConnection=url.openConnection();
                    InputStream inputStream=urlConnection.getInputStream();

                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result =new StringBuilder();
                    String line;
                    while((line=bufferedReader.readLine())!=null)
                    {
                        result.append(line);
                    }
                    return  result.toString();

                } catch (Exception e) {
                    exception=e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if(s==null && exception!=null )
                {
                    weatherServiceCallBack.serviceFailure(exception);
                    return;
                }
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    JSONObject queryResult=jsonObject.optJSONObject("query");
                    Channel channel=new Channel();
                    channel.populate(queryResult.optJSONObject("results").optJSONObject("channel"));
                    weatherServiceCallBack.serviceSuccess(channel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute(location);
    }

    public String getLocation() {
        return location;
    }
}
