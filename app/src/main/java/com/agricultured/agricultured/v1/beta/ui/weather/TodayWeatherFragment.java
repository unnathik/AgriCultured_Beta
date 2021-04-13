package com.agricultured.agricultured.v1.beta.ui.weather;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agricultured.agricultured.v1.beta.Common;
import com.agricultured.agricultured.v1.beta.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class TodayWeatherFragment extends Fragment {

    private TodayWeatherViewModel mViewModel;
    String appId = "4a8018b7b1640836e960924451e3d0b4";
    EditText etCity;
    private final String url = "http://api.openweathermap.org/data/2.5/weather";

    DecimalFormat df = new DecimalFormat("#.##");
    ImageView searchBtnLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    TextView tempResult;
    TextView locationResult;
    TextView highTempResult;
    TextView lowTempResult;
    TextView suggestionWeather;
    TextView descriptionResult;
    ImageView weatherIcon;

    TextView nationalWeatherAlert;

    TextView txt_wind;
    TextView txt_pressure;
    TextView txt_humidity;
    TextView txt_sunrise;
    TextView txt_sunset;

    public static TodayWeatherFragment newInstance() {
        return new TodayWeatherFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.today_weather_fragment, container, false);

        etCity = root.findViewById(R.id.editTextLocation);
        searchBtnLocation = root.findViewById(R.id.searchBtnLocation);
        getCurrentWeatherDetails();

        tempResult = root.findViewById(R.id.tempToday);
        locationResult = root.findViewById(R.id.locationToday);
        highTempResult = root.findViewById(R.id.highTempToday);
        lowTempResult = root.findViewById(R.id.lowTempToday);
        suggestionWeather = root.findViewById(R.id.suggestionToday);
        descriptionResult = root.findViewById(R.id.descriptionToday);
        weatherIcon = root.findViewById(R.id.iconWeather);

        txt_wind = root.findViewById(R.id.txt_wind);
        txt_pressure = root.findViewById(R.id.txt_pressure);
        txt_humidity = root.findViewById(R.id.txt_humidity);
        txt_sunrise = root.findViewById(R.id.txt_sunrise);
        txt_sunset = root.findViewById(R.id.txt_sunset);

        searchBtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeatherDetails(root);
            }
        });

        return root;
    }

    private void getCurrentWeatherDetails() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(getContext(),
                                Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );

                            String city = addresses.get(0).getLocality();
                            String tempUrl = "";
                            tempUrl = url + "?q=" + city + "&appid=" + appId;

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response", response);
                                    String tempOutput = "";
                                    String locationOutput = "";
                                    String highTempOutput = "";
                                    String lowTempOutput = "";

                                    String windOutput = "";
                                    String pressureOutput= "";
                                    String humidityOutput = "";

                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                                        int descID = jsonObjectWeather.getInt("id");
                                        String description = jsonObjectWeather.getString("description");
                                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
                                        double tempMax = jsonObjectMain.getDouble("temp_max") - 273.15;
                                        double tempMin = jsonObjectMain.getDouble("temp_min") - 273.15;

                                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                                        String countryName = jsonObjectSys.getString("country");
                                        String cityName = jsonResponse.getString("name");
                                        int dt = jsonResponse.getInt("dt");

                                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                                        double windSpeed = jsonObjectWind.getDouble("speed");
                                        double windDeg = jsonObjectWind.getDouble("deg");
                                        windOutput = "Speed: " + df.format(windSpeed) + " | " + "Deg: " + df.format(windDeg);
                                        txt_wind.setText(windOutput);

                                        double pressure = jsonObjectMain.getDouble("pressure");
                                        pressureOutput += df.format(pressure);
                                        txt_pressure.setText(pressureOutput + "hpa");

                                        double humidity = jsonObjectMain.getDouble("humidity");
                                        humidityOutput += df.format(humidity);
                                        txt_humidity.setText(humidityOutput + "%");

                                        int sunrise = jsonObjectSys.getInt("sunrise");
                                        txt_sunrise.setText(Common.convertUnixToDate(sunrise));

                                        int sunset = jsonObjectSys.getInt("sunset");
                                        txt_sunset.setText(Common.convertUnixToDate(sunset));

                                        locationOutput = cityName + ", " + countryName + "\n" + Common.convertUnixToDate(dt);
                                        locationResult.setText(locationOutput);

                                        tempOutput += df.format(temp);
                                        tempResult.setText(tempOutput + "\u2103");

                                        highTempOutput += df.format(tempMax);
                                        highTempResult.setText("H: " + highTempOutput + "\u2103");

                                        lowTempOutput += df.format(tempMin);
                                        lowTempResult.setText("L: " + lowTempOutput + "\u2103");

                                        descriptionResult.setText(description.toUpperCase());

                                        if (descID >= 200 && descID < 300) {
                                            suggestionWeather.setText("Take personal safety measures and be wary of potential rain to take measures accordingly.");
                                            weatherIcon.setImageResource(R.drawable.art_storm);
                                        }

                                        if (descID >= 300 && descID < 500) {
                                            suggestionWeather.setText("Even though it is drizzling today, don't completely assume the rains to soak down to plant roots. Make sure your plants still get the water they need if there is run off!");
                                            weatherIcon.setImageResource(R.drawable.art_light_rain);
                                        }

                                        if (descID >= 500 && descID < 600) {
                                            suggestionWeather.setText("Even though it is raining today, don't completely assume the rains to soak down to plant roots. Make sure your plants still get the water they need if there is run off!");
                                            weatherIcon.setImageResource(R.drawable.art_light_rain);
                                        }

                                        if (descID >= 600 && descID < 700) {
                                            suggestionWeather.setText("Snow will insulate the soil, providing the needed soil moisture. Reduce fertilizer usage as snow contains trace amounts of nitrogen. Also, remember 10 inches of snow equal to one inch of water for the crops, so plan your watering cycles accordingly.");
                                            weatherIcon.setImageResource(R.drawable.art_snow);
                                        }

                                        if (descID >= 700 && descID < 800) {
                                            suggestionWeather.setText("Implement normal farming practices. No special weather-based recommendations for now!");
                                            weatherIcon.setImageResource(R.drawable.art_fog);
                                        }

                                        if (descID >= 800 && descID < 900) {
                                            suggestionWeather.setText("Implement normal farming practices. No special weather-based recommendations for now!");
                                            weatherIcon.setImageResource(R.drawable.art_clear);
                                        }

                                        if (descID >= 900 && descID < 1000) {
                                            suggestionWeather.setText("Take personal safety measures and be wary of potential rain to take measures accordingly. Water when it is windy.");
                                            weatherIcon.setImageResource(R.drawable.art_storm);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            requestQueue.add(stringRequest);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    public void getWeatherDetails(View view) {
        String tempUrl = "";
        String city = etCity.getText().toString().trim();
        if(city.equals("")) {
            tempResult.setText("Please pick a city!");
        } else {
            tempUrl = url + "?q=" + city + "&appid=" + appId;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                String tempOutput = "";
                String locationOutput = "";
                String highTempOutput = "";
                String lowTempOutput = "";

                String windOutput = "";
                String pressureOutput= "";
                String humidityOutput = "";

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    int descID = jsonObjectWeather.getInt("id");
                    String description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    double tempMax = jsonObjectMain.getDouble("temp_max") - 273.15;
                    double tempMin = jsonObjectMain.getDouble("temp_min") - 273.15;

                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    String cityName = jsonResponse.getString("name");
                    int dt = jsonResponse.getInt("dt");

                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                    double windSpeed = jsonObjectWind.getDouble("speed");
                    double windDeg = jsonObjectWind.getDouble("deg");
                    windOutput = "Speed: " + df.format(windSpeed) + " | " + "Deg: " + df.format(windDeg);
                    txt_wind.setText(windOutput);

                    double pressure = jsonObjectMain.getDouble("pressure");
                    pressureOutput += df.format(pressure);
                    txt_pressure.setText(pressureOutput + "hpa");

                    double humidity = jsonObjectMain.getDouble("humidity");
                    humidityOutput += df.format(humidity);
                    txt_humidity.setText(humidityOutput + "%");

                    int sunrise = jsonObjectSys.getInt("sunrise");
                    txt_sunrise.setText(Common.convertUnixToDate(sunrise));

                    int sunset = jsonObjectSys.getInt("sunset");
                    txt_sunset.setText(Common.convertUnixToDate(sunset));

                    locationOutput = cityName + ", " + countryName + "\n" + Common.convertUnixToDate(dt);
                    locationResult.setText(locationOutput);

                    tempOutput += df.format(temp);
                    tempResult.setText(tempOutput + "\u2103");

                    highTempOutput += df.format(tempMax);
                    highTempResult.setText("H: " + highTempOutput + "\u2103");

                    lowTempOutput += df.format(tempMin);
                    lowTempResult.setText("L: " + lowTempOutput + "\u2103");

                    descriptionResult.setText(description.toUpperCase());

                    if (descID >= 200 && descID < 300) {
                        suggestionWeather.setText("Take personal safety measures and be wary of potential rain to take measures accordingly.");
                        weatherIcon.setImageResource(R.drawable.art_storm);
                    }

                    if (descID >= 300 && descID < 500) {
                        suggestionWeather.setText("Even though it is drizzling today, don't completely assume the rains to soak down to plant roots. Make sure your plants still get the water they need if there is run off!");
                        weatherIcon.setImageResource(R.drawable.art_light_rain);
                    }

                    if (descID >= 500 && descID < 600) {
                        suggestionWeather.setText("Even though it is raining today, don't completely assume the rains to soak down to plant roots. Make sure your plants still get the water they need if there is run off!");
                        weatherIcon.setImageResource(R.drawable.art_light_rain);
                    }

                    if (descID >= 600 && descID < 700) {
                        suggestionWeather.setText("Snow will insulate the soil, providing the needed soil moisture. Reduce fertilizer usage as snow contains trace amounts of nitrogen. Also, remember 10 inches of snow equal to one inch of water for the crops, so plan your watering cycles accordingly.");
                        weatherIcon.setImageResource(R.drawable.art_snow);
                    }

                    if (descID >= 700 && descID < 800) {
                        suggestionWeather.setText("Implement normal farming practices. No special weather-based recommendations for now!");
                        weatherIcon.setImageResource(R.drawable.art_fog);
                    }

                    if (descID >= 800 && descID < 900) {
                        suggestionWeather.setText("Implement normal farming practices. No special weather-based recommendations for now!");
                        weatherIcon.setImageResource(R.drawable.art_clear);
                    }

                    if (descID >= 900 && descID < 1000) {
                        suggestionWeather.setText("Take personal safety measures and be wary of potential rain to take measures accordingly. Water when it is windy.");
                        weatherIcon.setImageResource(R.drawable.art_storm);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TodayWeatherViewModel.class);
        // TODO: Use the ViewModel
    }
}