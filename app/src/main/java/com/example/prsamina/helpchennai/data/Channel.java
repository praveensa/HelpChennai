package com.example.prsamina.helpchennai.data;

import org.json.JSONObject;

/**
 * Created by prsamina on 12/21/2015.
 */
public class Channel implements JSONPopulator {
    private  Items items;
    private Unit unit;

    public Items getItems() {
        return items;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public void populate(JSONObject jsonObject) {
        items=new Items();
        items.populate(jsonObject.optJSONObject("item"));

        unit=new Unit();
        unit.populate(jsonObject.optJSONObject("units"));
    }
}
