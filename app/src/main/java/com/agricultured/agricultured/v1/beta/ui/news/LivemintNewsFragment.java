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

public class LivemintNewsFragment extends Fragment {

    private LivemintNewsViewModel mViewModel;
    private WebView webViewLivemint;

    public static LivemintNewsFragment newInstance() {
        return new LivemintNewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.livemint_news_fragment, container, false);

        webViewLivemint = root.findViewById(R.id.webViewLivemint);
        webViewLivemint.setWebViewClient(new WebViewClient());
        webViewLivemint.loadUrl("https://www.livemint.com/industry/agriculture");

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LivemintNewsViewModel.class);
        // TODO: Use the ViewModel
    }

}