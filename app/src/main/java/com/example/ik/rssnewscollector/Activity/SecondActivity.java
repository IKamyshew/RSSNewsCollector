package com.example.ik.rssnewscollector.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.ik.rssnewscollector.Activity.MainActivity;
import com.example.ik.rssnewscollector.R;

public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        WebView w1 = (WebView)findViewById(R.id.webView);
        String link = getIntent().getStringExtra(MainActivity.ITEM_DATA);
        w1.loadUrl(link);
    }
}
