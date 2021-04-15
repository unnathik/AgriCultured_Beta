package com.agricultured.agricultured.v1.beta.ui.news;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.agricultured.agricultured.v1.beta.R;

public class TheBetterIndiaFragment extends Fragment {

    private TheBetterIndiaViewModel mViewModel;
    private WebView webViewTheBetterIndia;

    public static TheBetterIndiaFragment newInstance() {
        return new TheBetterIndiaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.the_better_india_fragment, container, false);

        webViewTheBetterIndia = root.findViewById(R.id.webViewTheBetterIndia);
        webViewTheBetterIndia.setWebViewClient(new WebViewClient());
        webViewTheBetterIndia.loadUrl("https://www.thebetterindia.com/topics/agriculture/");

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TheBetterIndiaViewModel.class);
        // TODO: Use the ViewModel
    }

}