package com.example.prsamina.helpchennai.data;

import org.json.JSONObject;

/**
 * Created by prsamina on 12/21/2015.
 */
public class Unit implements JSONPopulator {
    String tempUnit;

    public String getTempUnit() {
        return tempUnit;
    }

    @Override
    public void populate(JSONObject jsonObject) {
    tempUnit=jsonObject.optString("temperature");
    }
}
