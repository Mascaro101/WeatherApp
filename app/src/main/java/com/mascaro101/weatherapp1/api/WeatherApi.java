// WeatherApi.java
package com.mascaro101.weatherapp1.api;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mascaro101.weatherapp1.utils.Constants;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherApi {

    public interface WeatherResponseListener {
        void onResponse(JSONObject response);
        void onError(String error);
    }

    public static void fetchWeather(Context context, double latitude, double longitude, final WeatherResponseListener listener) {
        String url = Constants.WEATHER_API_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + Constants.API_KEY + "&units=metric";

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError("Error fetching weather data.");
            }
        });

        queue.add(jsonObjectRequest);
    }
}