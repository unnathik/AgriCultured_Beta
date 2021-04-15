package com.agricultured.agricultured.v1.beta.ui.news;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.agricultured.agricultured.v1.beta.ui.calculators.FertilizerFragment;
import com.agricultured.agricultured.v1.beta.ui.calculators.PesticideFragment;
import com.agricultured.agricultured.v1.beta.ui.calculators.PlantPopulationFragment;
import com.agricultured.agricultured.v1.beta.ui.calculators.SeedRateFragment;

public class NewsFragmentAdapter extends FragmentStateAdapter {
    public NewsFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                return new TheBetterIndiaFragment();
            case 2:
                return new TheHinduNewsFragment();
            case 3:
                return new LivemintNewsFragment();
        }

        return new EconomicsTimesNews();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
