package com.agricultured.agricultured.v1.beta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agricultured.agricultured.v1.beta.model.WeatherForecastResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder>{
    Context context;
    WeatherForecastResult weatherForecastResult;

    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView locationWeek, descriptionWeek, tempWeek, highTempWeek, lowTempWeek;
        ImageView iconWeatherWeek;
        String appId = "4a8018b7b1640836e960924451e3d0b4";
        private final String url = "http://api.openweathermap.org/data/2.5/forecast";

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            locationWeek = (TextView) itemView.findViewById(R.id.locationWeek);
            descriptionWeek = (TextView) itemView.findViewById(R.id.descriptionWeek);
            tempWeek = (TextView) itemView.findViewById(R.id.tempWeek);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.locationWeek.setText(new StringBuilder(Common.convertUnixToDate(weatherForecastResult
        .list.get(position).dt)));

        holder.descriptionWeek.setText(new StringBuilder(weatherForecastResult.list.get(position)
        .weather.get(0).getDescription().toUpperCase()));

        holder.tempWeek.setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position)
        .main.getTemp())).append("\u2103"));


    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

}
