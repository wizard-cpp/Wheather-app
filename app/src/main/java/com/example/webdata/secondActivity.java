package com.example.webdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class secondActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        webView= findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/search?q=google+weather&rlz=1C1CHBF_enIN951IN951&oq=GOOGLE+WEQ&aqs=chrome.1.69i57j0i10i131i433l7j0i10i433j0i10.5084j0j15&sourceid=chrome&ie=UTF-8");
    }
}