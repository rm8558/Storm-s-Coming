package com.reetfreelance.rm.stormscoming.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.reetfreelance.rm.stormscoming.R;
import com.reetfreelance.rm.stormscoming.model.WeatherDay;
import com.reetfreelance.rm.stormscoming.util.adapters.DailyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyWeatherActivity extends ListActivity {

    private final String TAG=DailyWeatherActivity.class.getSimpleName();

    @BindView(android.R.id.list) ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_daily);
        ButterKnife.bind(this);

        Intent dailyIntent=getIntent();
        Parcelable parcelable[]=dailyIntent.getParcelableArrayExtra("DAILY_DATA");


        if(parcelable!=null &&
                parcelable.length>0){

            WeatherDay weatherDays[]= Arrays.copyOf(parcelable,
                    parcelable.length,
                    WeatherDay[].class);

            DailyAdapter dailyAdapter=new DailyAdapter(this,weatherDays);
            listView.setAdapter(dailyAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final WeatherDay weatherDay=(WeatherDay) parent.getItemAtPosition(position);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DailyWeatherActivity.this,
                                    weatherDay.getDay()
                                            +" - "
                                            +weatherDay.getTemperature()
                                            +"\u00b0C",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        /*ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,days);
        listView.setAdapter(arrayAdapter);*/
    }

    private WeatherDay[] parseDailyData(String dailyDataStr){
        try{
            JSONArray dailyDataArray=new JSONArray(dailyDataStr);
            WeatherDay weatherDays[]=new WeatherDay[dailyDataArray.length()];

            for(int i=0;i<weatherDays.length;i++){
                JSONObject dataObj=dailyDataArray.getJSONObject(i);
                weatherDays[i]=new WeatherDay(dataObj.getLong("time"),
                        dataObj.getDouble("temperatureMax"),
                        dataObj.getString("icon"));
            }

            return weatherDays;
        }catch(JSONException jsonX){
            Log.e(TAG,jsonX.getMessage(),jsonX);
        }
        return null;
    }
}
