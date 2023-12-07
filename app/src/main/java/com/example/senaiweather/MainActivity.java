package com.example.senaiweather;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.WindowManager;
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
        wheaterIconIV = findViewById(R.id.idIVBack);
        backgroundIV = findViewById(R.id.idIVBack);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        String cityName2 = "Jequie";
        String url = "https://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + cityName2;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String changeCity = jsonResponse.getJSONObject("location").getString("name");
                            cityName.setText(changeCity);
                            String chanceTemp = jsonResponse.getJSONObject("current").getString("temp_c");
                            temperature.setText(chanceTemp);

                        } catch (Exception e) {
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

        Volley.newRequestQueue(this).add(stringRequest);

    }

}