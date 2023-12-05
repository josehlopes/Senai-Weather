package com.example.senaiweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout homeRL;
    private ProgressBar homeLoading;
    private TextView cityName, temperature, condition;
    private RecyclerView wheaterRV;
    private TextInputEditText cityNameImput;
    private ImageView searchIV, wheaterIconIV, backgroundIV;
    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        double temperaturaAtual = 25.5;
        temperature.setText(temperaturaAtual + " °C");

    }
}