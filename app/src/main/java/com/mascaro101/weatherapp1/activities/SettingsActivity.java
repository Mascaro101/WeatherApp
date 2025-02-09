package com.mascaro101.weatherapp1.activities;

                    import android.content.Intent;
                    import android.content.SharedPreferences;
                    import android.os.Bundle;
                    import android.widget.Button;
                    import android.widget.EditText;
                    import androidx.appcompat.app.AppCompatActivity;
                    import com.mascaro101.weatherapp1.MainActivity;
                    import com.mascaro101.weatherapp1.R;

                    public class SettingsActivity extends AppCompatActivity {

                        private EditText etExtremeColdThreshold;
                        private EditText etColdThreshold;
                        private EditText etTemperateThreshold;
                        private EditText etWindThreshold;
                        private EditText etHighHumidity;
                        private EditText etLowVisibility;
                        private Button btnSave;

                        @Override
                        protected void onCreate(Bundle savedInstanceState) {
                            super.onCreate(savedInstanceState);

                            setContentView(R.layout.activity_settings);

                            etExtremeColdThreshold = findViewById(R.id.etExtremeColdThreshold);
                            etColdThreshold = findViewById(R.id.etColdThreshold);
                            etTemperateThreshold = findViewById(R.id.etTemperateThreshold);
                            etWindThreshold = findViewById(R.id.etWindThreshold);
                            etHighHumidity = findViewById(R.id.etHighHumidity);
                            etLowVisibility = findViewById(R.id.etLowVisibility);
                            btnSave = findViewById(R.id.btnSave);

                            // Load saved preferences
                            SharedPreferences preferences = getSharedPreferences("ClotheAdvisorPrefs", MODE_PRIVATE);
                            etExtremeColdThreshold.setText(String.valueOf(preferences.getFloat("EXTREME_COLD_THRESHOLD", 5)));
                            etColdThreshold.setText(String.valueOf(preferences.getFloat("COLD_THRESHOLD", 15)));
                            etTemperateThreshold.setText(String.valueOf(preferences.getFloat("TEMPERATE_THRESHOLD", 25)));
                            etWindThreshold.setText(String.valueOf(preferences.getFloat("WIND_THRESHOLD", 5)));
                            etHighHumidity.setText(String.valueOf(preferences.getInt("HIGH_HUMIDITY", 80)));
                            etLowVisibility.setText(String.valueOf(preferences.getFloat("LOW_VISIBILITY", 1)));

                            btnSave.setOnClickListener(v -> {
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putFloat("EXTREME_COLD_THRESHOLD", Float.parseFloat(etExtremeColdThreshold.getText().toString()));
                                editor.putFloat("COLD_THRESHOLD", Float.parseFloat(etColdThreshold.getText().toString()));
                                editor.putFloat("TEMPERATE_THRESHOLD", Float.parseFloat(etTemperateThreshold.getText().toString()));
                                editor.putFloat("WIND_THRESHOLD", Float.parseFloat(etWindThreshold.getText().toString()));
                                editor.putInt("HIGH_HUMIDITY", Integer.parseInt(etHighHumidity.getText().toString()));
                                editor.putFloat("LOW_VISIBILITY", Float.parseFloat(etLowVisibility.getText().toString()));
                                editor.apply();

                                setResult(RESULT_OK);
                                finish();
                            });
                        }
                    }