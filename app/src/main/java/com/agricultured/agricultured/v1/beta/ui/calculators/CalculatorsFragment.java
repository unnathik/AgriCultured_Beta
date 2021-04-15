package com.agricultured.agricultured.v1.beta.ui.calculators;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agricultured.agricultured.v1.beta.FragmentAdapter;
import com.agricultured.agricultured.v1.beta.R;
import com.google.android.material.tabs.TabLayout;

public class CalculatorsFragment extends Fragment {

    private CalculatorsViewModel mViewModel;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    CalculatorFragmentAdapter adapter;

    public static CalculatorsFragment newInstance() {
        return new CalculatorsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.calculators_fragment, container, false);

        tabLayout = root.findViewById(R.id.tab_layout_calculators);
        viewPager2 = root.findViewById(R.id.view_pager2_calculators);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        adapter = new CalculatorFragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Plant Population"));
        tabLayout.addTab(tabLayout.newTab().setText("Seed Rate"));
        tabLayout.addTab(tabLayout.newTab().setText("Pesticide Quantity"));
        tabLayout.addTab(tabLayout.newTab().setText("Fertilizer Combinations"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));

            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CalculatorsViewModel.class);
        // TODO: Use the ViewModel
    }

}