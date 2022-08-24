package com.example.cryptocurrenciesapp.utilities;

import com.example.cryptocurrenciesapp.model.Detail;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailListDeserializer implements JsonDeserializer<List<Detail>> {

    @Override
    public List<Detail> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Detail> detailList = new ArrayList<>();

        final JsonArray detailArray = json.getAsJsonObject().getAsJsonObject("Data").getAsJsonArray("Data");

        for (JsonElement el : detailArray) {
            final JsonObject detail = el.getAsJsonObject();

            detailList.add(new Detail(
                    detail.get("time").getAsLong(),
                    detail.get("open").getAsDouble(),
                    detail.get("high").getAsDouble(),
                    detail.get("low").getAsDouble(),
                    detail.get("close").getAsDouble()
            ));
        }

        return detailList;
    }
}

