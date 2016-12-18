package com.aiosdev.weather;

import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aiosdev.weather.model.City;
import com.aiosdev.weather.model.Clouds;
import com.aiosdev.weather.model.Main;
import com.aiosdev.weather.model.Weather;
import com.aiosdev.weather.model.WeatherInfo;
import com.aiosdev.weather.model.Wind;
import com.aiosdev.weather.tools.GsonUtilForTony;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.framed.FrameReader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FuncByGeoCoorActivity extends AppCompatActivity {

    private String[] mStrings = {"Beijing", "Shanghai", "Guangzhou", "Montreal", "Toronto", "Vancouver", "Ottawa", "New York", "Washington", "Moscow", "Paris", "London"};
    private String[] mStringNames = {"北京", "上海", "广州", "蒙特利尔", "多伦多", "温哥华", "渥太华", "纽约", "华盛顿", "莫斯科", "巴黎", "伦敦"};

    private List<City> cityList;

    private Handler requestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    WeatherInfo weatherInfoRes = (WeatherInfo) msg.getData().getSerializable("info");
                    Toast.makeText(FuncByGeoCoorActivity.this, "SUCCESSFUL : " + weatherInfoRes.getClouds().getAll().toString(), Toast.LENGTH_SHORT).show();
                    displayWeatherInfo(weatherInfoRes);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_by_geo_coor);

        System.out.println("==============this is a onCreate methode !");

        JSONArray jsonArray = getJsonFromAssets(getApplicationContext(), "citylist.json");
        //System.out.println("jsonArray" + jsonArray.length());
        cityList = GsonUtilForTony.parseJsonWitGson(jsonArray.toString());

        ListView listView = (ListView) findViewById(R.id.lisview);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, mStrings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {

                double lon = 0;
                double lat = 0;
                boolean flag = false;
                for (City city : cityList) {
                    if (mStrings[i].equals(city.getName())) {
                        lon = city.getCoord().getLon();
                        lat = city.getCoord().getLat();
                        flag = true;
                    }
                }

                if (flag) {
                    try {
                        String st = getWeatherInfo(lon, lat);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), mStringNames[i], Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "城市未找到！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //从Assets文件夹中读取数据，返回JSONArray对象
    public static JSONArray getJsonFromAssets(Context mContext, String fileName) {

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

    //显示天气情况
    private void displayWeatherInfo(WeatherInfo weatherInfo) {

        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("天气信息")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(createView(weatherInfo))
                // 设置内容
                .setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    //创建对话框中的layout，并赋值
    private View createView(WeatherInfo weatherInfo) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramLl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(paramLl);

        LinearLayout layoutChild1 = new LinearLayout(this);
        layoutChild1.setOrientation(LinearLayout.VERTICAL);
        //layoutChild1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        LinearLayout.LayoutParams paramLlChild1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutChild1.setLayoutParams(paramLlChild1);


        for (int i = 0; i < weatherInfo.getWeather().size(); i++) {
            ImageView iv = new ImageView(this);
            iv.setId(i);
            String ivUrl = "http://openweathermap.org/img/w/" + weatherInfo.getWeather().get(i).getIcon() + ".png";
            Picasso.with(getApplicationContext()).load(ivUrl).resize(100, 100).into(iv);
            //iv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            LinearLayout.LayoutParams paramIv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
            iv.setLayoutParams(paramIv);
            layoutChild1.addView(iv);
        }


        LinearLayout layoutChild2 = new LinearLayout(this);
        layoutChild2.setOrientation(LinearLayout.VERTICAL);
        //layoutChild2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        LinearLayout.LayoutParams paramLlChild2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2.0f);
        layoutChild2.setLayoutParams(paramLlChild2);

        TextView tv1 = new TextView(this);
        int temp = ((int) Double.parseDouble(weatherInfo.getMain().getTemp())) - 273;
        tv1.setText("当前温度 " + temp + " °C");
        LinearLayout.LayoutParams paramTv1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        tv1.setLayoutParams(paramTv1);
        layoutChild2.addView(tv1);

        TextView tv2 = new TextView(this);
        int min = ((int) Double.parseDouble(weatherInfo.getMain().getTemp_min())) - 273;
        int max = ((int) Double.parseDouble(weatherInfo.getMain().getTemp_max())) - 273;
        tv2.setText("最低 " + min + "°C" + " ——— " + "最高 " + max + "°C");
        LinearLayout.LayoutParams paramTv2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        tv2.setLayoutParams(paramTv2);
        layoutChild2.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText("风速 " + weatherInfo.getWind().getSpeed() + " 米/秒");
        LinearLayout.LayoutParams paramTv3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        tv3.setLayoutParams(paramTv3);
        layoutChild2.addView(tv3);

        TextView tv4 = new TextView(this);
        tv4.setText("湿度 " + weatherInfo.getMain().getHumidity() + " %");
        LinearLayout.LayoutParams paramTv4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        tv4.setLayoutParams(paramTv4);
        layoutChild2.addView(tv4);

        layout.addView(layoutChild1);
        layout.addView(layoutChild2);

        return layout;
    }

    //发送Http请求（OkHttp网络框架—GET方法）
    public String getWeatherInfo(double lon, double lat) throws IOException {
        String baseUrl = "http://api.openweathermap.org/data/2.5/weather?APPID=86e5dc2075020faa2d390c7f9c556355&";
        String baseLon = "lon=" + lon;
        final String baseLat = "lat=" + lat;
        String requestUrl = baseUrl + baseLat + "&" + baseLon;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(requestUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }

                String res = response.body().string();
                System.out.println(res);
                WeatherInfo weatherInfo = parseJson(res);
                System.out.println("test weatherInfo: " + weatherInfo);
                //displayWeatherInfo();
                Message message = requestHandler.obtainMessage();
                message.what = 1;
                Bundle bundle = new Bundle();
                bundle.putSerializable("info", weatherInfo);
                message.setData(bundle);
                message.sendToTarget();

            }
        });

        return "";
    }

    //解析返回的网络数据
    private WeatherInfo parseJson(String jsonStr) {
        WeatherInfo weatherInfo = new WeatherInfo();
        try {
            JSONObject object = new JSONObject(jsonStr);

            List<Weather> weatherList = new ArrayList<>();
            JSONArray jsonArray = object.getJSONArray("weather");
            for (int i = 0; i < jsonArray.length(); i++) {
                Weather weather = new Weather();
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                weather.setId(jsonObject.getString("id"));
                weather.setMain(jsonObject.getString("main"));
                weather.setDescription(jsonObject.getString("description"));
                weather.setIcon(jsonObject.getString("icon"));

                weatherList.add(weather);
            }
            weatherInfo.setWeather(weatherList);

            JSONObject jsonMain = object.getJSONObject("main");
            Main main = new Main();
            main.setTemp(jsonMain.getString("temp"));
            main.setPressure(jsonMain.getString("pressure"));
            main.setHumidity(jsonMain.getString("humidity"));
            main.setTemp_min(jsonMain.getString("temp_min"));
            main.setTemp_max(jsonMain.getString("temp_max"));
            weatherInfo.setMain(main);

            JSONObject jsonWind = object.getJSONObject("wind");
            Wind wind = new Wind();
            wind.setSpeed(jsonWind.getString("speed"));
            if(!jsonWind.isNull("deg")) {
                wind.setDeg(jsonWind.getString("deg"));
            }
            weatherInfo.setWind(wind);

            JSONObject jsonClouds = object.getJSONObject("clouds");
            Clouds clouds = new Clouds();
            clouds.setAll(jsonClouds.getString("all"));
            weatherInfo.setClouds(clouds);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return weatherInfo;
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("==============this is a onStart methode !");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("==============this is a onResume methode !");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("==============this is a onPause methode !");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("==============this is a onStop methode !");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("==============this is a onDestroy methode !");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("==============this is a onRestart methode !");
    }
}
