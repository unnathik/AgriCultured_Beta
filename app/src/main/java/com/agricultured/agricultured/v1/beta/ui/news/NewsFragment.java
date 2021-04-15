package com.agricultured.agricultured.v1.beta.ui.news;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agricultured.agricultured.v1.beta.R;
import com.agricultured.agricultured.v1.beta.ui.calculators.CalculatorFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class NewsFragment extends Fragment {

    private NewsViewModel mViewModel;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    NewsFragmentAdapter adapter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.news_fragment, container, false);

        tabLayout = root.findViewById(R.id.tab_layout_news);
        viewPager2 = root.findViewById(R.id.view_pager2_news);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        adapter = new NewsFragmentAdapter(fm, getLifecycle());
        viewPager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("The Economic Times"));
        tabLayout.addTab(tabLayout.newTab().setText("The Better India"));
        tabLayout.addTab(tabLayout.newTab().setText("The Hindu"));
        tabLayout.addTab(tabLayout.newTab().setText("Livemint"));

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
        mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        // TODO: Use the ViewModel
    }

}