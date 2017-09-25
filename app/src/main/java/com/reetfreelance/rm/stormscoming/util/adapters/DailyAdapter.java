package com.reetfreelance.rm.stormscoming.util.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.reetfreelance.rm.stormscoming.R;
import com.reetfreelance.rm.stormscoming.model.WeatherDay;

public class DailyAdapter extends BaseAdapter{
    private Context mContext;
    private WeatherDay[] weatherDays;

    public DailyAdapter(Context mContext, WeatherDay[] weatherDays) {
        this.mContext = mContext;
        this.weatherDays = weatherDays;
    }

    @Override
    public int getCount() {
        return weatherDays.length;
    }

    @Override
    public Object getItem(int position) {
        return weatherDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;  //Not used
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(null==convertView){
            //create brand new convertView
            convertView=LayoutInflater.from(mContext).inflate(R.layout.daily_list_item,null);

            holder=new ViewHolder();
            holder.dayLabel=(TextView) convertView.findViewById(R.id.dayTextView);
            holder.temperatureTextView=(TextView) convertView.findViewById(R.id.tempValTextView);
            holder.weatherIcon=(ImageView) convertView.findViewById(R.id.weatherIconSmallImageView);
            //holder.thermoIcon=(ImageView) convertView.findViewById(R.id.tempIconImageView);

            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }

        WeatherDay weatherDay=weatherDays[position];

        if(position==0){
            holder.dayLabel.setText("Today");
        }
        else{
            holder.dayLabel.setText(weatherDay.getDay());
        }
        holder.temperatureTextView.setText(weatherDay.getTemperature()+"\u00b0C");
        holder.weatherIcon.setImageResource(weatherDay.getIconId());
        //holder.thermoIcon.setImageResource(R.drawable.ic_temp);

        return convertView;
    }

    private static class ViewHolder {
        ImageView weatherIcon;
        TextView dayLabel;
        TextView temperatureTextView;
        //ImageView thermoIcon;
    }
}
