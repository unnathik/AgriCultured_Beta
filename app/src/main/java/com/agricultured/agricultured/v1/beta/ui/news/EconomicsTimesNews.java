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

public class EconomicsTimesNews extends Fragment {

    private EconomicsTimesNewsViewModel mViewModel;
    private WebView webViewET;

    public static EconomicsTimesNews newInstance() {
        return new EconomicsTimesNews();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.economics_times_news_fragment, container, false);

        webViewET = root.findViewById(R.id.webViewET);
        webViewET.setWebViewClient(new WebViewClient());
        webViewET.getSettings().setJavaScriptEnabled(true);
        webViewET.loadUrl("https://economictimes.indiatimes.com/news/economy/agriculture");

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EconomicsTimesNewsViewModel.class);
        // TODO: Use the ViewModel
    }

}