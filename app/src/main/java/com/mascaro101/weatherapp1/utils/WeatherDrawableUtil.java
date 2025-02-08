package com.mascaro101.weatherapp1.utils;

import com.mascaro101.weatherapp1.R;

public class WeatherDrawableUtil {

    public static int getWeatherDrawable(String weatherDescription) {
        if (weatherDescription == null) {
            return R.drawable.unknown;
        }

        String description = weatherDescription.toLowerCase();

        if (description.contains("clear")) {
            return R.drawable.clear;
        } else if (description.contains("cloud")) {
            return R.drawable.cloudy;
        } else if (description.contains("rain")) {
            return R.drawable.rain;
        } else if (description.contains("snow")) {
            return R.drawable.snow;
        } else if (description.contains("storm") || description.contains("thunder")) {
            return R.drawable.storm;
        } else {
            return R.drawable.unknown;
        }
    }
}