package com.example.acer.smarthomev11;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by Acer on 10.07.2017.
 */

public class ActivityLight extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);
        WebView browser = (WebView) findViewById(R.id.webBrowser);
        browser.loadUrl("http://192.168.1.222/");
    }


}

