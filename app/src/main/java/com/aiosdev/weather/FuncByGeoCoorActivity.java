package com.aiosdev.weather;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private String[] mStrings = {"Beijing", "Shanghai", "Guangzhou", "Montreal", "Toronto", "Vancouver", "Ottawa", "New York", "Washington", "Moscow", "Paris", "London"};
    private String[] mStringNames = {"北京", "上海", "广州", "蒙特利尔", "多伦多", "温哥华", "渥太华", "纽约", "华盛顿", "莫斯科", "巴黎", "伦敦"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_by_geo_coor);

        ListView listView = (ListView) findViewById(R.id.lisview);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, mStrings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View view1, int i, long l) {

                displayWeatherInfo();
                Toast.makeText(getApplicationContext(), mStringNames[i], Toast.LENGTH_SHORT).show();
            }
        });

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

    //显示天气情况
    private void displayWeatherInfo(){

        StringBuffer sb = new StringBuffer();
        sb.append("天气信息");
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage(sb.toString())
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(createView())
                // 设置内容
                .setPositiveButton("确认注销",// 设置确定按钮
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // 点击"取消"按钮之后退出程序
                                //finish();
                            }
                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    private View createView(){
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramLl = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(paramLl);

        LinearLayout layoutChild1 = new LinearLayout(this);
        layoutChild1.setOrientation(LinearLayout.VERTICAL);
        layoutChild1.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        LinearLayout.LayoutParams paramLlChild1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        layoutChild1.setLayoutParams(paramLlChild1);

        ImageView iv = new ImageView(this);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.gty_icon_1));
        iv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        LinearLayout.LayoutParams paramIv = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //paramIv.weight = 4;
        iv.setLayoutParams(paramIv);
        layoutChild1.addView(iv);



        LinearLayout layoutChild2 = new LinearLayout(this);
        layoutChild2.setOrientation(LinearLayout.VERTICAL);
        layoutChild2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        LinearLayout.LayoutParams paramLlChild2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4.0f);
        layoutChild2.setLayoutParams(paramLlChild2);

        TextView tv1 = new TextView(this);
        tv1.setText("this is a textview1");
        LinearLayout.LayoutParams paramTv1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        tv1.setLayoutParams(paramTv1);
        layoutChild2.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText("this is a textview2");
        LinearLayout.LayoutParams paramTv2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1.0f);
        tv2.setLayoutParams(paramTv2);
        layoutChild2.addView(tv2);

        layout.addView(layoutChild1);
        layout.addView(layoutChild2);

        return layout;
    }
}
