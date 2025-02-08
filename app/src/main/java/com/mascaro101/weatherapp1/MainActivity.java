package com.mascaro101.weatherapp1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mascaro101.weatherapp1.advisor.ClotheAdvisor;
import com.mascaro101.weatherapp1.api.WeatherApi;
import com.mascaro101.weatherapp1.api.WeatherApi.WeatherResponseListener;
import com.mascaro101.weatherapp1.database.AppDatabase;
import com.mascaro101.weatherapp1.database.ClothingAdvice;
import com.mascaro101.weatherapp1.utils.WeatherDrawableUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView tvCity, tvTemperature, tvWeather, clotheRecommendation;
    private ImageView weatherIcon;
    private Button historyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCity = findViewById(R.id.tvCity);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvWeather = findViewById(R.id.tvWeather);
        clotheRecommendation = findViewById(R.id.clotheRecommendation);
        weatherIcon = findViewById(R.id.weatherIcon);
        historyButton = findViewById(R.id.btnHistory);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fetchLocationAndWeather();
        }

        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, com.mascaro101.weatherapp1.activities.HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void fetchLocationAndWeather() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            fetchWeatherData(latitude, longitude);
                        } else {
                            Toast.makeText(MainActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchWeatherData(double latitude, double longitude) {
        WeatherApi.fetchWeather(this, latitude, longitude, new WeatherResponseListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String city = response.getString("name");
                    JSONObject main = response.getJSONObject("main");
                    double temperature = main.getDouble("temp");
                    String weatherCond = response.getJSONArray("weather").getJSONObject(0).getString("description");

                    tvCity.setText(city);
                    tvTemperature.setText(String.format("%.1f°C", temperature));
                    tvWeather.setText(weatherCond);

                    int weatherDrawable = WeatherDrawableUtil.getWeatherDrawable(weatherCond);
                    weatherIcon.setImageResource(weatherDrawable);

                    String recommendation = ClotheAdvisor.getClothingRecommendation(
                            temperature,
                            main.getDouble("temp_min"),
                            main.getDouble("temp_max"),
                            response.getJSONObject("wind").getDouble("speed"),
                            weatherCond,
                            main.getInt("humidity"),
                            response.getDouble("visibility")
                    );
                    clotheRecommendation.setText(recommendation);

                    String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                    saveWeatherData(currentDate, temperature, response.getJSONObject("wind").getDouble("speed"), recommendation);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error parsing weather data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveWeatherData(String date, double temperature, double windSpeed, String advice) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            ClothingAdvice clothingAdvice = new ClothingAdvice();
            clothingAdvice.date = date;
            clothingAdvice.temperature = temperature;
            clothingAdvice.windSpeed = windSpeed;
            clothingAdvice.advice = advice;
            db.clothingAdviceDAO().insert(clothingAdvice);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocationAndWeather();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
