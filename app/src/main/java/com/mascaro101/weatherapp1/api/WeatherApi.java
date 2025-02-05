package com.mascaro101.weatherapp1.api;

import android.content.Context;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mascaro101.weatherapp1.advisor.ClotheAdvisor;
import com.mascaro101.weatherapp1.utils.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherApi {

    public static void fetchWeather(Context context, double latitude, double longitude, final TextView tvWeather, final TextView clotheRecomendation) {
        String url = Constants.WEATHER_API_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + Constants.API_KEY + "&units=metric";

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String city = response.getString("name");
                    JSONObject main = response.getJSONObject("main");
                    double temp = main.getDouble("temp");
                    double tempMin = main.getDouble("temp_min");
                    double tempMax = main.getDouble("temp_max");
                    int humidity = main.getInt("humidity");

                    JSONObject wind = response.getJSONObject("wind");
                    double windSpeed = wind.getDouble("speed");

                    JSONObject sys = response.getJSONObject("sys");
                    String country = sys.getString("country");

                    double visibility = response.getDouble("visibility") / 1000.0; // Convert to kilometers

                    String weatherCond = response.getJSONArray("weather").getJSONObject(0).getString("description");

                    String weatherInfo = "City: " + city + ", " + country + "\n" +
                            "Temperature: " + temp + "°C\n" +
                            "Min: " + tempMin + "°C, Max: " + tempMax + "°C\n" +
                            "Humidity: " + humidity + "%\n" +
                            "Wind Speed: " + windSpeed + " m/s\n" +
                            "Visibility: " + visibility + " km\n" +
                            "Condition: " + weatherCond;

                    tvWeather.setText(weatherInfo);
                    clotheRecomendation.setText(ClotheAdvisor.getClothingRecommendation(temp, tempMin, tempMax, windSpeed, weatherCond, humidity, visibility));

                } catch (JSONException e) {
                    e.printStackTrace();
                    tvWeather.setText("Error parsing weather data.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvWeather.setText("Error fetching weather data.");
            }
        });

        queue.add(request);
    }
}