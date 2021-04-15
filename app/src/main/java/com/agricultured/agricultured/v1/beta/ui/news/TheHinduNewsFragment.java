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

public class TheHinduNewsFragment extends Fragment {

    private TheHinduNewsViewModel mViewModel;
    private WebView webViewTheHindu;

    public static TheHinduNewsFragment newInstance() {
        return new TheHinduNewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.the_hindu_news_fragment, container, false);

        webViewTheHindu = root.findViewById(R.id.webViewTheHindu);
        webViewTheHindu.setWebViewClient(new WebViewClient());
        webViewTheHindu.loadUrl("https://www.thehindu.com/topic/Agriculture/");

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TheHinduNewsViewModel.class);
        // TODO: Use the ViewModel
    }

}