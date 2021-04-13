package com.agricultured.agricultured.v1.beta.ui.weather;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agricultured.agricultured.v1.beta.R;

public class WeekWeatherFragment extends Fragment {

    private WeekWeatherViewModel mViewModel;

    public static WeekWeatherFragment newInstance() {
        return new WeekWeatherFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.week_weather_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(WeekWeatherViewModel.class);
        // TODO: Use the ViewModel
    }

}