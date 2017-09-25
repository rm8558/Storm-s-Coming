package com.reetfreelance.rm.stormscoming.util.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.reetfreelance.rm.stormscoming.R;
import com.reetfreelance.rm.stormscoming.model.WeatherHour;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>{

    WeatherHour weatherHours[];
    Context mContext;

    public HourlyAdapter(Context context, WeatherHour[] weatherHours) {
        this.mContext=context;
        this.weatherHours = weatherHours;
    }

    @Override
    public HourlyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.hourly_list_item,
                        parent,
                        false);

        HourlyViewHolder viewHolder=new HourlyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HourlyViewHolder holder, int position) {
        holder.bindHourlyData(weatherHours[position]);
    }

    @Override
    public int getItemCount() {
        return weatherHours.length;
    }

    public class HourlyViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        public TextView hourTextView;
        public TextView temperatureTextView;
        public TextView summaryTextView;
        public ImageView weatherIconImageView;

        public HourlyViewHolder(View itemView) {
            super(itemView);

            hourTextView=(TextView)itemView.findViewById(R.id.hourTimestampTextView);
            temperatureTextView=(TextView) itemView.findViewById(R.id.hourlyTempTextView);
            summaryTextView=(TextView) itemView.findViewById(R.id.hourlySummaryTextView);
            weatherIconImageView=(ImageView) itemView.findViewById(R.id.hourlyWeatherIcon);

            itemView.setOnClickListener(this);
        }

        public void bindHourlyData(WeatherHour weatherHour){
            hourTextView.setText(weatherHour.getHour());
            temperatureTextView.setText(weatherHour.getTemperature()+"\u00b0C");
            summaryTextView.setText(weatherHour.getSummary());
            weatherIconImageView.setImageResource(weatherHour.getIconId());
        }

        @Override
        public void onClick(View v) {
            String hour=hourTextView.getText().toString();
            String summary=summaryTextView.getText().toString();
            String temperature=temperatureTextView.getText().toString();

            Toast.makeText(mContext, hour+" - "+temperature+" - "+summary, Toast.LENGTH_SHORT).show();
        }
    }
}
