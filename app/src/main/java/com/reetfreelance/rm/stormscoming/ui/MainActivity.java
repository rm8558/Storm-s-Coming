package com.reetfreelance.rm.stormscoming.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.reetfreelance.rm.stormscoming.R;
import com.reetfreelance.rm.stormscoming.model.Location;
import com.reetfreelance.rm.stormscoming.model.WeatherDay;
import com.reetfreelance.rm.stormscoming.model.WeatherHour;
import com.reetfreelance.rm.stormscoming.util.AlertDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final String TAG=MainActivity.class.getSimpleName();
    private Location location;
    private static final String DAILY_DATA="DAILY_DATA";
    private static final String HOURLY_DATA="HOURLY_DATA";

    /*private int testCounter=0;
    private String testIcon[]={
            "clear-day", "clear-night",
            "rain", "snow", "sleet", "wind",
            "fog", "cloudy", "partly-cloudy-day",
            "partly-cloudy-night", "thunderstorm",
            "tornado"
    };*/

    @BindView(R.id.temperatureTextView) TextView temperatureTextView;
    @BindView(R.id.humidityValueTextView) TextView humidityValueTextView;
    @BindView(R.id.precipValueTextView) TextView precipValueTextView;
    @BindView(R.id.timestampTextView) TextView timestampTextView;
    @BindView(R.id.mainSwipeToRefreshLayout) SwipeRefreshLayout mainSwipeRefreshLayout;
    @BindString(R.string.defaultDecimalFormat) String defaultDecimalFormat;
    @BindView(R.id.weatherIconImageView) ImageView weatherIconImageView;
    @BindView(R.id.summaryTextView) TextView summaryTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent locationIntent=getIntent();
        double latitude=locationIntent.getDoubleExtra("LATITUDE",-99999.99),
                longitude=locationIntent.getDoubleExtra("LONGITUDE",-99999.99);

        final String apiKey=getResources().getString(R.string.API_KEY);

        if(latitude==-99999.99
                && longitude==-99999.99){
            location=new Location();
            manageLocation();
        }
        else{
            location=new Location(latitude,longitude);
        }


        manageWeather(apiKey);

        SwipeRefreshLayout.OnRefreshListener mainSwipeRefreshListener=new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                manageWeather(apiKey);
            }
        };
        mainSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mainSwipeRefreshLayout.setOnRefreshListener(mainSwipeRefreshListener);
    }

    private void manageLocation(){
        if(isLocationEnabled()){
            Toast.makeText(this, "Location enabled", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Location disabled", Toast.LENGTH_SHORT).show();
        }
    }



    private void manageWeather(String apiKey){
        if(isOnline()){
            //Toast.makeText(this, "Fetching weather data", Toast.LENGTH_SHORT).show();
            getWeatherDetails(apiKey);
        }
        else{
            alertUserAboutError();
            stopMainRefreshing();
            //Toast.makeText(this, "Check connectivity", Toast.LENGTH_SHORT).show();
        }
    }

    //Make a call for weather details and and handle the response
    public void getWeatherDetails(String apiKey){
        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url(location.getAPIUrl(apiKey))
                .build();

        Call call=client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                stopMainRefreshing();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr=response.body().string();

                try{
                    JSONObject weatherObj=new JSONObject(responseStr);

                    JSONObject currentWeatherObj=weatherObj.getJSONObject("currently");
                    location.setTimezone(weatherObj.getString("timezone"));
                    location.setTemperature(currentWeatherObj.getDouble("temperature"));
                    location.setHumidity(currentWeatherObj.getDouble("humidity"));
                    location.setPrecipitation(currentWeatherObj.getDouble("precipProbability"));
                    location.setTime(currentWeatherObj.getLong("time"));
                    location.setSummary(currentWeatherObj.getString("summary"));
                    location.setIcon(currentWeatherObj.getString("icon"));

                    //for icon testing only
                    /*location.setIcon(testIcon[testCounter]);
                    testCounter=(testCounter+1)%testIcon.length;*/

                    updateUI();

                    parseDailyData(weatherObj);
                    parseHourlyData(weatherObj);

                }catch(JSONException jsonX){
                    Log.e(TAG,jsonX.getMessage(),jsonX);
                }finally {
                    stopMainRefreshing();
                }
            }
        });
    }

    private void parseHourlyData(JSONObject weatherObj) throws JSONException{
        JSONObject hourlyObj=weatherObj.getJSONObject("hourly");
        JSONArray hourlyDataArray=hourlyObj.getJSONArray("data");

        WeatherHour weatherHours[]=new WeatherHour[hourlyDataArray.length()];

        for(int i=0;i<weatherHours.length;i++){
            JSONObject weatherDataObj=hourlyDataArray.getJSONObject(i);
            weatherHours[i]=new WeatherHour();
            weatherHours[i].setTime(weatherDataObj.getLong("time"));
            weatherHours[i].setTimezone(weatherObj.getString("timezone"));
            weatherHours[i].setIcon(weatherDataObj.getString("icon"));
            weatherHours[i].setSummary(weatherDataObj.getString("summary"));
            weatherHours[i].setTemperature(weatherDataObj.getDouble("temperature"));
        }

        location.setWeatherHours(weatherHours);
        Log.v(TAG,"Successfully added the hourly weather details");
    }

    private void parseDailyData(JSONObject weatherObj) throws JSONException{
        JSONObject dailyObj=weatherObj.getJSONObject("daily");

        JSONArray dailyDataArray=dailyObj.getJSONArray("data");
        WeatherDay weatherDays[]=new WeatherDay[dailyDataArray.length()];

        for(int i=0;i<weatherDays.length;i++){
            JSONObject dataObj=dailyDataArray.getJSONObject(i);
            weatherDays[i]=new WeatherDay(dataObj.getLong("time"),
                    dataObj.getDouble("temperatureMax"),
                    dataObj.getString("icon"));
        }

        location.setWeatherDays(weatherDays);
    }

    private void stopMainRefreshing(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StringBuffer tempBuffer=new StringBuffer(String.format(defaultDecimalFormat,location.getTemperature()));
                if(location.getUnit()!=null
                        && location.getUnit().equalsIgnoreCase("si")){
                    tempBuffer.append("\u00b0C");
                }
                temperatureTextView.setText(tempBuffer.toString());
                timestampTextView.setText(location.getDateAsString().toUpperCase());
                humidityValueTextView.setText(String.format(defaultDecimalFormat,location.getHumidity()));
                double precipVal=location.getPrecipitation()*100;
                precipValueTextView.setText(String.format(defaultDecimalFormat,precipVal)+"%");

                Drawable weatherIconDrawable= ContextCompat.getDrawable(MainActivity.this,location.getIconId());
                weatherIconImageView.setImageDrawable(weatherIconDrawable);

                summaryTextView.setText(location.getSummary());
            }
        });
    }

    //Check if device is connected to the internet
    public boolean isOnline(){
        boolean flag=false;
        ConnectivityManager connMgr=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connMgr.getActiveNetworkInfo();

        if(networkInfo !=null
                && networkInfo.isConnected()){
            flag=true;
        }

        return flag;
    }

    private void alertUserAboutError(){
        AlertDialogFragment errorFragment=new AlertDialogFragment();
        errorFragment.show(getFragmentManager(),"error_dialog");
    }

    @OnClick(R.id.hourlyWeatherButton)
    public void onWeatherHourlyClick(View View){
        Intent hourlyIntent=new Intent(this,HourlyWeatherActivity.class);
        hourlyIntent.putExtra(HOURLY_DATA,location.getWeatherHours());
        startActivity(hourlyIntent);
    }

    @OnClick(R.id.dailyWeatherButton)
    public void onWeatherDailyClick(View view){
        Intent dailyIntent=new Intent(this,DailyWeatherActivity.class);
        dailyIntent.putExtra(DAILY_DATA,location.getWeatherDays());
        startActivity(dailyIntent);
    }

    public boolean isLocationEnabled(){
        boolean flag=false;
        LocationManager locMgr=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if(locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            flag=true;
        }

        return flag;
    }
}
