package com.aiosdev.weather.tools;

import com.aiosdev.weather.model.City;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2016/12/11 0011.
 */

public class GsonUtilForTony {
    public static List<City>  parseJsonWitGson(String jsonData){
        List<City> list = new ArrayList<City>();
        if(jsonData != null){
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(jsonData).getAsJsonArray();

            for(JsonElement obj: jsonArray){
                City city = gson.fromJson(obj, City.class);
                list.add(city);
            }
        }

        return list;
    }
}
