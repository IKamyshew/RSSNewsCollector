package com.example.ik.rssnewscollector;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by ik on 02.11.2015.
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        WebView w1 = (WebView)findViewById(R.id.webView);
        w1.loadUrl("http://tutorialspoint.com/android/sampleXML.xml");
    }
}
