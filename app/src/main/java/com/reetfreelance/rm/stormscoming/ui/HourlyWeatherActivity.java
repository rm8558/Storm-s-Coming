package com.reetfreelance.rm.stormscoming.ui;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.reetfreelance.rm.stormscoming.R;
import com.reetfreelance.rm.stormscoming.model.WeatherHour;
import com.reetfreelance.rm.stormscoming.util.adapters.HourlyAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyWeatherActivity extends AppCompatActivity {
    private static final String TAG=HourlyWeatherActivity.class.getSimpleName();

    WeatherHour weatherHours[];
    @BindView(R.id.hourlyRecyclerView) RecyclerView recyclerView;
    @BindView(R.id.hourlyEmptyTextView) TextView hourlyEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);
        ButterKnife.bind(this);

        Intent hourlyIntent=getIntent();
        Parcelable parcelable[]=hourlyIntent.getParcelableArrayExtra("HOURLY_DATA");



        if(parcelable!=null &&
                parcelable.length>0){
            weatherHours= Arrays.copyOf(parcelable,parcelable.length,WeatherHour[].class);

            if(weatherHours.length==0){
                hourlyEmptyTextView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            else{
                hourlyEmptyTextView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                HourlyAdapter hourlyAdapter=new HourlyAdapter(this,weatherHours);
                recyclerView.setAdapter(hourlyAdapter);

                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),
                        linearLayoutManager.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
            }
        }
    }
}
