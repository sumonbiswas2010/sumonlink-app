package com.example.freeurlshortener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    WebView web;
    String url = "https://sumonlink.netlify.app/";
    public static final String USER_AGENT_FAKE = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        web = findViewById(R.id.web);
        web.setWebViewClient(new WebViewClient() {
                                 @Override
                                 public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                     String currentUrl = url;

                                     if (url.startsWith("http") || url.startsWith("https")) {
                                         return false;
                                     }
                                     if (url.startsWith("intent")) {

                                         try {
                                             Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);

                                             String fallbackUrl = intent.getStringExtra("browser_fallback_url");
                                             if (fallbackUrl != null) {
                                                 web.loadUrl(fallbackUrl);
                                                 return true;
                                             }
                                         } catch (URISyntaxException e) {
                                             //not an intent uri
                                         }
                                         return true;//do nothing in other cases
                                     }
                                     return true;
                                 }
                             }

        );

        web.loadUrl(url);

        WebSettings webSettings = web.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString(USER_AGENT_FAKE);
    }

    @Override
    public void onBackPressed() {

        if (web.canGoBack())
        {
            web.goBack();
        }
        else
        {
            super.onBackPressed();
        }

    }}