package com.agricultured.agricultured.v1.beta.ui.weather;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agricultured.agricultured.v1.beta.Common;
import com.agricultured.agricultured.v1.beta.R;
import com.agricultured.agricultured.v1.beta.Retrofit.IOpenWeatherMap;
import com.agricultured.agricultured.v1.beta.Retrofit.RetrofitClient;
import com.agricultured.agricultured.v1.beta.model.WeatherForecastResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class WeekWeatherFragment extends Fragment {
    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    FusedLocationProviderClient fusedLocationProviderClient;
    RecyclerView recycler_forecast;

    private WeekWeatherViewModel mViewModel;
    static WeekWeatherFragment instance;

    public static WeekWeatherFragment getInstance() {
        if(instance == null)
            instance = new WeekWeatherFragment();
        return instance;
    }

    public static WeekWeatherFragment newInstance() {
        return new WeekWeatherFragment();
    }

    public WeekWeatherFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.week_weather_fragment, container, false);

        recycler_forecast = (RecyclerView)itemView.findViewById(R.id.recycler_forecast);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        getForecastWeatherInformation();

        return itemView;
    }

    private void getForecastWeatherInformation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    double currentLatitude = location.getLatitude();
                    double currentLongitude = location.getLongitude();

                    compositeDisposable.add(mService.getForecastWeatherByLatLng(
                            String.valueOf(currentLatitude),
                            String.valueOf(currentLongitude),
                            Common.APP_ID,
                            "metric")
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<WeatherForecastResult>() {
                                @Override
                                public void accept(WeatherForecastResult weatherForecastResult) throws Throwable {
                                    displayForecastWeather(weatherForecastResult);
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Throwable {
                                    Log.d("Error", "" + throwable.getMessage());
                                }
                            })
                    );
                }
            });
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        };
    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {
        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), weatherForecastResult);
        recycler_forecast.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WeekWeatherViewModel.class);
        // TODO: Use the ViewModel
    }

}