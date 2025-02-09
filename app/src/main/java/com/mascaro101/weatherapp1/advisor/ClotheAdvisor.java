package com.mascaro101.weatherapp1.advisor;

            import android.content.Context;
            import android.content.SharedPreferences;

            public class ClotheAdvisor {

                private static double EXTREME_COLD_THRESHOLD;
                private static double COLD_THRESHOLD;
                private static double TEMPERATE_THRESHOLD;
                private static double WIND_THRESHOLD;
                private static int HIGH_HUMIDITY;
                private static double LOW_VISIBILITY;

                public static void loadPreferences(Context context) {
                    SharedPreferences preferences = context.getSharedPreferences("ClotheAdvisorPrefs", Context.MODE_PRIVATE);
                    EXTREME_COLD_THRESHOLD = preferences.getFloat("EXTREME_COLD_THRESHOLD", 5);
                    COLD_THRESHOLD = preferences.getFloat("COLD_THRESHOLD", 15);
                    TEMPERATE_THRESHOLD = preferences.getFloat("TEMPERATE_THRESHOLD", 25);
                    WIND_THRESHOLD = preferences.getFloat("WIND_THRESHOLD", 5);
                    HIGH_HUMIDITY = preferences.getInt("HIGH_HUMIDITY", 80);
                    LOW_VISIBILITY = preferences.getFloat("LOW_VISIBILITY", 1);
                }

                public static String getClothingRecommendation(double temperature, double tempMin, double tempMax, double windSpeed,
                                                               String weatherCond, int humidity, double visibility) {
                    StringBuilder recommendation = new StringBuilder("\n\n");

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
                        advice.append("– Temperatura fresca. Se recomienda una chaqueta o suéter y pantalones largos.\n\n");
                    } else if (tempMax <TEMPERATE_THRESHOLD) {
                        advice.append("– Clima templado. Puedes usar ropa ligera, pero considera llevar una capa extra por si refresca.\n\n");
                    } else {
                        advice.append("– Hace calor. Usa ropa fresca y cómoda (camiseta, shorts o falda) y mantente hidratado.\n\n");
                    }
                    return advice.toString();
                }

                private static String getWindAdvice(double windSpeed) {
                    if (windSpeed > WIND_THRESHOLD) {
                        return "– Hay viento. Evita prendas muy sueltas y, si es necesario, utiliza una chaqueta cortavientos.\n\n";
                    }
                    return "";
                }

                private static String getWeatherConditionAdvice(String weatherCond) {
                    StringBuilder advice = new StringBuilder();
                    if (weatherCond != null) {
                        String condLower = weatherCond.toLowerCase();
                        if (condLower.contains("rain")) {
                            advice.append("– Está lloviendo. No olvides llevar un paraguas o impermeable.\n\n");
                        } else if (condLower.contains("snow")) {
                            advice.append("– Está nevando. Usa botas impermeables y ropa térmica.\n\n");
                        } else if (condLower.contains("cloud")) {
                            advice.append("– El cielo está nublado. Podría refrescar, tenlo en cuenta.\n\n");
                        } else if (condLower.contains("clear")) {
                            advice.append("– El cielo está despejado. Ideal para salir, pero usa protector solar y gafas de sol.\n\n");
                        } else if (condLower.contains("storm") || condLower.contains("thunder")) {
                            advice.append("– Se esperan tormentas. Considera mantenerte en casa o llevar ropa y accesorios impermeables.\n\n");
                        }
                    } else {
                        advice.append("– Información de la condición climática no disponible.\n\n");
                    }
                    return advice.toString();
                }

                private static String getHumidityAdvice(int humidity, double temperature) {
                    if (humidity > HIGH_HUMIDITY) {
                        if (temperature >= TEMPERATE_THRESHOLD) {
                            return "– Humedad alta. Usa ropa ligera y transpirable.\n\n";
                        } else {
                            return "– Humedad alta. Asegúrate de mantenerte seco y abrigado.\n\n";
                        }
                    }
                    return "";
                }

                private static String getVisibilityAdvice(double visibility) {
                    if (visibility < LOW_VISIBILITY) {
                        return "– Visibilidad baja. Ten precaución al desplazarte.\n\n";
                    }
                    return "";
                }

                private static String getAdditionalAdvice(double temperature, double windSpeed) {
                    if (temperature < EXTREME_COLD_THRESHOLD && windSpeed > WIND_THRESHOLD) {
                        return "– Consejo adicional: En condiciones de frío extremo y viento, considera cubrir todas las partes expuestas del cuerpo.\n\n";
                    }
                    return "";
                }
            }