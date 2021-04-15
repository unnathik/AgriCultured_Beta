package com.agricultured.agricultured.v1.beta.ui.calculators;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.agricultured.agricultured.v1.beta.ui.weather.TodayWeatherFragment;
import com.agricultured.agricultured.v1.beta.ui.weather.WeekWeatherFragment;

public class CalculatorFragmentAdapter extends FragmentStateAdapter {

    public CalculatorFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                return new SeedRateFragment();
            case 2:
                return new PesticideFragment();
            case 3:
                return new FertilizerFragment();
        }

        return new PlantPopulationFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
