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


public class DayAdapter extends BaseAdapter {

    private Context mContext;
    private WeatherDay[] weatherDays;
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
        return 0; //not going to use
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(null==convertView){
            //create brand new converView
            convertView= LayoutInflater.from(mContext).inflate(R.layout.daily_list_item,null);
            holder=new ViewHolder();

            holder.weatherIcon=(ImageView) convertView.findViewById(R.id.weatherIconSmallImageView);
            holder.dayLabel=(TextView) convertView.findViewById(R.id.dayTextView);
            holder.temperatureTextView=(TextView) convertView.findViewById(R.id.tempValTextView);

            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }

        WeatherDay weatherDay=weatherDays[position];
        holder.weatherIcon.setImageResource(weatherDay.getIconId());
        holder.temperatureTextView.setText(Double.toString(weatherDay.getTemperature()));
        holder.dayLabel.setText(weatherDay.getDay());

        return null;
    }

    private static class ViewHolder{
        ImageView weatherIcon;
        TextView dayLabel;
        TextView temperatureTextView;
    }
}
