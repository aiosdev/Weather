package com.aiosdev.weather;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aiosdev.weather.model.City;
import com.aiosdev.weather.tools.GsonUtilForTony;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FuncByGeoCoorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_by_geo_coor);

        String jsonData = "[\n" +
                "    {\n" +
                "        \"_id\": 1270260,\n" +
                "        \"name\": \"State of Haryāna\",\n" +
                "        \"country\": \"IN\",\n" +
                "        \"coord\": {\n" +
                "            \"lon\": 76,\n" +
                "            \"lat\": 29\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"_id\": 708546,\n" +
                "        \"name\": \"Holubynka\",\n" +
                "        \"country\": \"UA\",\n" +
                "        \"coord\": {\n" +
                "            \"lon\": 33.900002,\n" +
                "            \"lat\": 44.599998\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"_id\": 1283710,\n" +
                "        \"name\": \"Bāgmatī Zone\",\n" +
                "        \"country\": \"NP\",\n" +
                "        \"coord\": {\n" +
                "            \"lon\": 85.416664,\n" +
                "            \"lat\": 28\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"_id\": 529334,\n" +
                "        \"name\": \"Mar’ina Roshcha\",\n" +
                "        \"country\": \"RU\",\n" +
                "        \"coord\": {\n" +
                "            \"lon\": 37.611111,\n" +
                "            \"lat\": 55.796391\n" +
                "        }\n" +
                "    },\n" +
                "    {\n" +
                "        \"_id\": 1269750,\n" +
                "        \"name\": \"Republic of India\",\n" +
                "        \"country\": \"IN\",\n" +
                "        \"coord\": {\n" +
                "            \"lon\": 77,\n" +
                "            \"lat\": 20\n" +
                "        }\n" +
                "    }\n" +
                "]";

        List<City> cityList = GsonUtilForTony.parseJsonWitGson(jsonData);
        for(City city: cityList){
            System.out.println(city);
        }

        JSONArray jsonArray = getJsonFromAssets(getApplicationContext(), "citylist.json");
        System.out.println("jsonArray" + jsonArray.length());



    }

    public static JSONArray getJsonFromAssets(Context mContext, String fileName){

        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        JSONArray jsonObject = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }

            jsonObject = new JSONArray(sb.toString().trim());

        } catch (IOException e) {
            e.printStackTrace();
            sb.delete(0, sb.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
