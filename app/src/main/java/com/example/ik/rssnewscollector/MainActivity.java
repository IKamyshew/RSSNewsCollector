package com.example.ik.rssnewscollector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String ITEM_DATA = "ITEM";
    public final static String STATE_NEWS = "STATE_NEWS";

    ListView newsItems;
    ArrayList<NewsItem> items;

    private String finalUrl = "http://tutorialspoint.com/android/sampleXML.xml";
    private String androidInsider = "http://androidinsider.ru/feed";
    private HandleXML object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsItems = (ListView) findViewById(R.id.listview);

        if (savedInstanceState != null){
            items = savedInstanceState.getParcelableArrayList(STATE_NEWS);
            GetNews();
        } else {
            ParseXML();
            GetNews();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_NEWS, items);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                ParseXML();
                GetNews();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void GetNews() {
        NewsAdapter itemAdapter = new NewsAdapter(MainActivity.this,R.layout.activity_main, items);
        newsItems.setAdapter(itemAdapter);
        newsItems.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                NewsItem item = (NewsItem)adapter.getItemAtPosition(position);

                ReviewNews(item);
            }
        });
    }

    private void ParseXML() {
        items = new ArrayList<>();
        object = new HandleXML(androidInsider);
        object.fetchXML();

        while(object.parsingComplete) {
            //do nothing
        }
        items.addAll(object.getItems());
    }

    private void ReviewNews(NewsItem item) {
        Intent intent = new Intent(MainActivity.this, ItemActivity.class); //SecondActivity.class = webView
        intent.putExtra(ITEM_DATA, item.getContent()); // item.getLink() - for SecondActivity.class
        startActivity(intent);
    }

}


