package com.example.ik.rssnewscollector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ListView newsItems;
    ArrayList<NewsItem> items;
    Button b1;

    private String finalUrl = "http://tutorialspoint.com/android/sampleXML.xml";
    private String androidInsider = "http://androidinsider.ru/feed";
    private HandleXML object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsItems = (ListView) findViewById(R.id.listview);

        items = new ArrayList<>();

        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object = new HandleXML(androidInsider);
                object.fetchXML();

                while(object.parsingComplete) {

                }
                items.addAll(object.getItems());
                NewsAdapter itemAdapter = new NewsAdapter(MainActivity.this,R.layout.activity_main, items);
                newsItems.setAdapter(itemAdapter);
            }
        });
    }
}


