package com.example.prsamina.helpchennai.data;

import org.json.JSONObject;

/**
 * Created by prsamina on 12/21/2015.
 */
public class Condition implements JSONPopulator {
    private  int code;
    private String desc;
    private int temp;

    public int getCode() {
        return code;
    }

    public int getTemp() {
        return temp;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public void populate(JSONObject jsonObject) {
        code=jsonObject.optInt("code");
        desc=jsonObject.optString("text");
        temp=jsonObject.optInt("temp");
    }
}
