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

public class TomorrowWeatherFragment extends Fragment {

    private TomorrowWeatherViewModel mViewModel;

    public static TomorrowWeatherFragment newInstance() {
        return new TomorrowWeatherFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tomorrow_weather_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TomorrowWeatherViewModel.class);
        // TODO: Use the ViewModel
    }

}