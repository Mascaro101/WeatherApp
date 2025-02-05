package com.mascaro101.weatherapp1.advisor;

public class ClotheAdvisor {

    private static final double EXTREME_COLD_THRESHOLD = 5;
    private static final double COLD_THRESHOLD = 15;
    private static final double TEMPERATE_THRESHOLD = 25;
    private static final double WIND_THRESHOLD = 5;
    private static final int HIGH_HUMIDITY = 80;
    private static final double LOW_VISIBILITY = 1;

    public static String getClothingRecommendation(double temperature, double tempMin, double tempMax, double windSpeed,
                                                   String weatherCond, int humidity, double visibility) {
        StringBuilder recommendation = new StringBuilder("Recomendación de vestimenta:\n");

        recommendation.append(getTemperatureAdvice(temperature, tempMin, tempMax));
        recommendation.append(getWindAdvice(windSpeed));
        recommendation.append(getWeatherConditionAdvice(weatherCond));
        recommendation.append(getHumidityAdvice(humidity, temperature));
        recommendation.append(getVisibilityAdvice(visibility));
        recommendation.append(getAdditionalAdvice(temperature, windSpeed));

        return recommendation.toString();
    }

    private static String getTemperatureAdvice(double temperature, double tempMin, double tempMax) {
        StringBuilder advice = new StringBuilder();
        if (tempMin < EXTREME_COLD_THRESHOLD) {
            advice.append("– Hace mucho frío. Usa abrigo grueso, bufanda, guantes y gorro.\n");
        } else if (tempMin < COLD_THRESHOLD) {
            advice.append("– La temperatura es fresca. Se recomienda una chaqueta o suéter y pantalones largos.\n");
        } else if (tempMax < TEMPERATE_THRESHOLD) {
            advice.append("– El clima es templado. Puedes usar ropa ligera, pero considera llevar una capa extra por si refresca.\n");
        } else {
            advice.append("– Hace calor. Usa ropa fresca y cómoda (camiseta, shorts o falda) y mantente hidratado.\n");
        }
        return advice.toString();
    }

    private static String getWindAdvice(double windSpeed) {
        if (windSpeed > WIND_THRESHOLD) {
            return "– Hay viento. Evita prendas muy sueltas y, si es necesario, utiliza una chaqueta cortavientos.\n";
        }
        return "";
    }

    private static String getWeatherConditionAdvice(String weatherCond) {
        StringBuilder advice = new StringBuilder();
        if (weatherCond != null) {
            String condLower = weatherCond.toLowerCase();
            if (condLower.contains("rain")) {
                advice.append("– Está lloviendo. No olvides llevar un paraguas o impermeable.\n");
            } else if (condLower.contains("snow")) {
                advice.append("– Está nevando. Usa botas impermeables y ropa térmica.\n");
            } else if (condLower.contains("cloud")) {
                advice.append("– El cielo está nublado. Podría refrescar, tenlo en cuenta.\n");
            } else if (condLower.contains("clear")) {
                advice.append("– El cielo está despejado. Ideal para salir, pero usa protector solar y gafas de sol.\n");
            } else if (condLower.contains("storm") || condLower.contains("thunder")) {
                advice.append("– Se esperan tormentas. Considera mantenerte en casa o llevar ropa y accesorios impermeables.\n");
            }
        } else {
            advice.append("– Información de la condición climática no disponible.\n");
        }
        return advice.toString();
    }

    private static String getHumidityAdvice(int humidity, double temperature) {
        if (humidity > HIGH_HUMIDITY) {
            if (temperature >= TEMPERATE_THRESHOLD) {
                return "– La humedad es alta. Usa ropa ligera y transpirable.\n";
            } else {
                return "– La humedad es alta. Asegúrate de mantenerte seco y abrigado.\n";
            }
        }
        return "";
    }

    private static String getVisibilityAdvice(double visibility) {
        if (visibility < LOW_VISIBILITY) {
            return "– La visibilidad es muy baja. Ten precaución al desplazarte.\n";
        }
        return "";
    }

    private static String getAdditionalAdvice(double temperature, double windSpeed) {
        if (temperature < EXTREME_COLD_THRESHOLD && windSpeed > WIND_THRESHOLD) {
            return "– Consejo adicional: En condiciones de frío extremo y viento, considera cubrir todas las partes expuestas del cuerpo.\n";
        }
        return "";
    }
}