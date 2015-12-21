package com.example.prsamina.helpchennai.data;

import org.json.JSONObject;

/**
 * Created by prsamina on 12/21/2015.
 */
public class Items implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject jsonObject) {
            condition=new Condition();
            condition.populate(jsonObject.optJSONObject("condition"));
    }
}
