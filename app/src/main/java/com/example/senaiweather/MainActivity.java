package com.example.senaiweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    public String API_KEY = "4592b054b7b14878bdb83839230212";
    public String API_URL = "https://api.weatherapi.com/v1";
    private RelativeLayout homeRL;
    private ProgressBar homeLoading;
    private TextView cityName, temperature, conditionTV;
    private RecyclerView weatherRV;
    private RecyclerView weatherDaysRV;
    private ArrayList<WeatherRVModal> weatherRVModalArrayList;
    private ArrayList<WeatherDaysRVModal> weatherDaysRVModalArrayList;
    private WeatherRVAdapter weatherRVAdapter;
    private WeatherDaysRVAdapter weatherDaysRVAdapter;
    private TextInputEditText cityNameImput;
    private ImageView searchIV, iconIV, backIV;
    private LocationManager locationManager;
    private TextView textView;
    private FusedLocationProviderClient fusedLocationClient;
    private  int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeRL = findViewById(R.id.idRLHome);
        homeLoading = findViewById(R.id.idPBLoading);
        cityName = findViewById(R.id.idTVCityName);
        temperature = findViewById(R.id.idTVTemperature);
        conditionTV = findViewById(R.id.idTVCondition);
        weatherRV = findViewById(R.id.idRVWeather);
        weatherDaysRV = findViewById(R.id.idRVWeatherDays);
        cityNameImput = findViewById(R.id.idEDTCity);
        searchIV = findViewById(R.id.idIVSearch);
        iconIV = findViewById(R.id.idIVIcon);
        backIV = findViewById(R.id.idIVBack);
        weatherDaysRVModalArrayList = new ArrayList<>();
        weatherRVModalArrayList = new ArrayList<>();
        weatherDaysRVAdapter = new WeatherDaysRVAdapter(this,weatherDaysRVModalArrayList);
        weatherRVAdapter = new WeatherRVAdapter(this,weatherRVModalArrayList);
        weatherRV.setAdapter(weatherRVAdapter);
        weatherDaysRV.setAdapter(weatherDaysRVAdapter);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            String city = getCityName(location.getLongitude(), location.getLatitude());
                            cityName.setText(city);
                            getWeatherInfo(city);
                        } else {
                            Toast.makeText(this, "Localização indisponível", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


        searchIV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String cityName2 = cityNameImput.getText().toString();
                getWeatherInfo(cityName2);
            }

        });
    }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == PERMISSION_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissão garantida", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Forneça as permissões", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

    private String getCityName(double longitude, double latitude) {
        String cityName2 = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);

            for (Address adr : addresses) {
                if (adr != null) {
                    String city = adr.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName2 = city;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName2;
    }



    public void getWeatherInfo(String cityName2) {

        String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + cityName2+"&days=6&aqi=no&alerts=no&lang=pt";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        homeLoading.setVisibility(View.GONE);
                        homeRL.setVisibility(View.VISIBLE);
                        weatherRVModalArrayList.clear();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String changeCity = jsonResponse.getJSONObject("location").getString("name");
                            cityName.setText(changeCity);
                            String changeTemp = jsonResponse.getJSONObject("current").getString("temp_c");
                            temperature.setText(String.format("%.1f°C", Float.parseFloat(changeTemp)));
                            /////////////////////////////////////////////////////////////////////

                            int isDay = jsonResponse.getJSONObject("current").getInt("is_day");
                            String condition = jsonResponse.getJSONObject("current").getJSONObject("condition").getString("text");
                            String conditionIcon = jsonResponse.getJSONObject("current").getJSONObject("condition").getString("icon");
                            Picasso.get().load("http:".concat(conditionIcon)).into(iconIV);
                            conditionTV.setText(condition);
                            if(isDay==1){
                                //Dia
                                Picasso.get().load("https://images.unsplash.com/photo-1566228015668-4c45dbc4e2f5?ixid=MnwxMjA3FDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80").into(backIV);
                            }else {
                                //Noite
                                Picasso.get().load("https://images.unsplash.com/photo-1532074534361-bb09a38cf917?ixid=MnwxMjA3fDB8MHxwa690by1wYWdLfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80").into(backIV);
                            }


                            JSONObject forecastObj = jsonResponse.getJSONObject("forecast");
                            JSONObject forecastO = forecastObj.getJSONArray("forecastday").getJSONObject(0);
                            JSONArray hourArray = forecastO.getJSONArray("hour");

                            for(int i=0; i<hourArray.length();i++){
                                JSONObject hourObj = hourArray.getJSONObject(i);
                                String time = hourObj.getString("time");
                                String temper = hourObj.getString("temp_c");
                                String img = hourObj.getJSONObject("condition").getString("icon");
                                String wind = hourObj.getString("wind_kph");
                                weatherRVModalArrayList.add(new WeatherRVModal(time,temper,img,wind));
                            }
                            weatherRVAdapter.notifyDataSetChanged();

                            JSONObject forecastObDays = jsonResponse.getJSONObject("forecast");
                            JSONArray forecastDaysArray = forecastObDays.getJSONArray("forecastday");
                            weatherDaysRVModalArrayList.clear();

                            for (int i = 0; i < forecastDaysArray.length(); i++) {
                                JSONObject dayObject = forecastDaysArray.getJSONObject(i).getJSONObject("day");
                                String date = forecastDaysArray.getJSONObject(i).getString("date");
                                String temper = dayObject.getString("avgtemp_c");
                                String img = dayObject.getJSONObject("condition").getString("icon");
                                String wind = dayObject.getString("maxwind_kph");
                                weatherDaysRVModalArrayList.add(new WeatherDaysRVModal(date, temper, img, wind));
                            }
                            weatherDaysRVAdapter.notifyDataSetChanged();


                        } catch (Exception e) {
                            Log.e("MeuApp", "Erro durante o processamento da resposta JSON: " + e.getMessage());
                            e.printStackTrace();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        Log.e("MeuApp", "Erro na chamada à API: " + error.getMessage());
                    }
                });

        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
    }

}