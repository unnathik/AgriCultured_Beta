package com.agricultured.agricultured.v1.beta;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.agricultured.agricultured.v1.beta.ui.weather.TodayWeatherFragment;
import com.agricultured.agricultured.v1.beta.ui.weather.TomorrowWeatherFragment;
import com.agricultured.agricultured.v1.beta.ui.weather.WeekWeatherFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                return new WeekWeatherFragment();
        }

        return new TodayWeatherFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
