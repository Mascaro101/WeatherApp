package com.mascaro101.weatherapp1.api;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mascaro101.weatherapp1.R;
import com.mascaro101.weatherapp1.advisor.ClotheAdvisor;
import com.mascaro101.weatherapp1.utils.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherApi {

    public static void fetchWeather(Context context, double latitude, double longitude, final TextView tvWeather, final TextView clotheRecomendation, final ImageView weatherBackground) {
        String url = Constants.WEATHER_API_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + Constants.API_KEY + "&units=metric";

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String city = response.optString("name", "Desconocido");

                    JSONObject main = response.optJSONObject("main");
                    if (main == null) {
                        tvWeather.setText("Error: Datos del clima no disponibles.");
                        return;
                    }
                    double temp = main.optDouble("temp", 0);
                    double tempMin = main.optDouble("temp_min", 0);
                    double tempMax = main.optDouble("temp_max", 0);
                    int humidity = main.optInt("humidity", 0);

                    JSONObject wind = response.optJSONObject("wind");
                    double windSpeed = (wind != null) ? wind.optDouble("speed", 0) : 0;

                    JSONObject sys = response.optJSONObject("sys");
                    String country = (sys != null) ? sys.optString("country", "Desconocido") : "Desconocido";

                    double visibility = 0;
                    if (response.has("visibility")) {
                        visibility = response.optDouble("visibility", 10000) / 1000.0;
                    }

                    String weatherCond = "Desconocido";
                    if (response.has("weather")) {
                        weatherCond = response.getJSONArray("weather").getJSONObject(0).optString("description", "Desconocido");
                    }

                    String weatherInfo = "Ciudad: " + city + ", " + country + "\n" +
                            "Temperatura: " + temp + "°C\n" +
                            "Min: " + tempMin + "°C, Max: " + tempMax + "°C\n" +
                            "Humedad: " + humidity + "%\n" +
                            "Wind Speed: " + windSpeed + " m/s\n" +
                            "Visibilidad: " + visibility + " km\n" +
                            "Condición: " + weatherCond;

                    tvWeather.setText(weatherInfo);
                    clotheRecomendation.setText(ClotheAdvisor.getClothingRecommendation(temp, tempMin, tempMax, windSpeed, weatherCond, humidity, visibility));

                    // Cambiar el fondo dependiendo de la condición del clima
                    setWeatherBackground(weatherCond, weatherBackground);

                } catch (JSONException e) {
                    e.printStackTrace();
                    tvWeather.setText("Error procesando datos del clima.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvWeather.setText("Error al obtener datos del clima.");
            }
        });

        queue.add(request);
    }

    private static void setWeatherBackground(String weatherCond, ImageView weatherBackground) {
        // Cambiar la imagen del fondo según el clima
        switch (weatherCond.toLowerCase()) {
            case "clouds":
                weatherBackground.setImageResource(R.drawable.cloudy);
                break;
            case "rain":
                weatherBackground.setImageResource(R.drawable.rainy);
                break;
            case "snow":
                weatherBackground.setImageResource(R.drawable.snowy);
                break;
            case "thunderstorm":
                weatherBackground.setImageResource(R.drawable.storm);
                break;
            case "drizzle":
                weatherBackground.setImageResource(R.drawable.drizzle);
                break;
            default:
                weatherBackground.setImageResource(R.drawable.default_weather);
                break;
        }
    }
}