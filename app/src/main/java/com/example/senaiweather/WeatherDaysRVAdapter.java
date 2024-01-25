package com.example.senaiweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WeatherDaysRVAdapter extends RecyclerView.Adapter<WeatherDaysRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<WeatherDaysRVModal> weatherDaysRVModalArrayList;

    public WeatherDaysRVAdapter(Context context, ArrayList<WeatherDaysRVModal> weatherDaysRVModalArrayList) {
        this.context = context;
        this.weatherDaysRVModalArrayList = weatherDaysRVModalArrayList;
    }

    @NonNull
    @Override
    public WeatherDaysRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_rv_days_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherDaysRVAdapter.ViewHolder holder, int position) {

        WeatherDaysRVModal modalDays = weatherDaysRVModalArrayList.get(position);
        holder.temperatureTV.setText(modalDays.getTemperature() + "°c");
        Picasso.get().load("http:".concat(modalDays.getImg())).into(holder.conditionIV);
        holder.windTV.setText(modalDays.getWind() + "Km/h");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat output = new SimpleDateFormat("EEE, dd/MM", new Locale("pt", "BR"));
        try{
            Date date = input.parse(modalDays.getDate());
            String formatedDate = output.format(date);
            holder.dateTV.setText(output.format(date));
        } catch (ParseException e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return weatherDaysRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView windTV, temperatureTV, dateTV;
        private ImageView conditionIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            windTV = itemView.findViewById(R.id.idTVWindSpeed);
            temperatureTV = itemView.findViewById(R.id.idTVTemperature);
            dateTV = itemView.findViewById(R.id.idTVDate);
            conditionIV = itemView.findViewById(R.id.idIVCondition);
        }
    }
}
