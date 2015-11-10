package com.example.ik.rssnewscollector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by ik on 30.10.2015.
 */
public class MainActivity extends Activity {

    EditText title;
    EditText link;
    EditText description;
    ListView newsItems;

    ArrayList<NewsItem> items;

    Button b1;
    Button b2;

    private String finalUrl = "http://tutorialspoint.com/android/sampleXML.xml";
    private String androidInsider = "http://androidinsider.ru/feed";
    private HandleXML object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.editText);
        link = (EditText) findViewById(R.id.editText2);
        description = (EditText) findViewById(R.id.editText3);
        newsItems = (ListView) findViewById(R.id.listview);

        items = new ArrayList<>();

        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object = new HandleXML(androidInsider);
                object.fetchXML();

                while(object.parsingComplete) {
                    items.addAll(object.getItems());
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        NewsAdapter itemAdapter = new NewsAdapter(this,R.layout.activity_main, items);
        newsItems.setAdapter(itemAdapter);
    }
}


