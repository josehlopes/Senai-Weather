package com.example.senaiweather;

import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity {

    public String API_KEY = "4592b054b7b14878bdb83839230212";
    public String API_URL = "https://api.weatherapi.com/v1";
    private RelativeLayout homeRL;
    private ProgressBar homeLoading;
    private TextView cityName, temperature, condition;
    private RecyclerView wheaterRV;
    private TextInputEditText cityNameImput;
    private ImageView searchIV, wheaterIconIV, backgroundIV;
    private LocationManager locationManager;

    private TextView textView;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeRL = findViewById(R.id.idRLHome);
        homeLoading = findViewById(R.id.idPBLoading);
        cityName = findViewById(R.id.idTVCityName);
        temperature = findViewById(R.id.idTVTemperature);
        condition = findViewById(R.id.idTVCondition);
        wheaterRV = findViewById(R.id.idRVWeather);
        cityNameImput = findViewById(R.id.idEDTCity);
        searchIV = findViewById(R.id.idIVSearch);
        wheaterIconIV = findViewById(R.id.idIVIcon);
        backgroundIV = findViewById(R.id.idIVBack);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        searchIV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String cityName2 = cityNameImput.getText().toString();
                getWheaterInfo(cityName2);
            }

        });

        cityNameImput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    String cityName2 = cityNameImput.getText().toString();
                    getWheaterInfo(cityName2);
                    return true;
                }
                return false;
            }
        });

    }
    public void getWheaterInfo(String cityName2) {

        String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + cityName2 + "&lang=pt";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String changeCity = jsonResponse.getJSONObject("location").getString("name");
                            cityName.setText(changeCity);
                            String changeTemp = jsonResponse.getJSONObject("current").getString("temp_c");
                            temperature.setText(String.format("%.1f°C", Float.parseFloat(changeTemp)));
                            String changeCondition = jsonResponse.getJSONObject("current").getJSONObject("condition").getString("text");
                            condition.setText(changeCondition);

                            String changeIcon = "https:" + jsonResponse.getJSONObject("current").getJSONObject("condition").getString("icon");
                            Picasso.get().load(changeIcon).into(wheaterIconIV);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        Log.e("Senai-Wheather:", "Erro na chamada à API: " + error.getMessage());
                    }
                });

        Volley.newRequestQueue(MainActivity.this).add(stringRequest);
    }

}