package com.aiosdev.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aiosdev.weather.model.City;
import com.aiosdev.weather.tools.GsonUtilForTony;

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




    }
}
