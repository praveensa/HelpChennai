package com.example.prsamina.helpchennai.service;

import com.example.prsamina.helpchennai.data.Channel;

/**
 * Created by prsamina on 12/21/2015.
 */
public interface WeatherServiceCallBack {

    void serviceSuccess(Channel channel);
    void serviceFailure(Exception e);

}
