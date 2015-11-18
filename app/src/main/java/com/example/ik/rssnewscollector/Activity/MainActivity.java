package com.example.ik.rssnewscollector.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ik.rssnewscollector.FeedParser.Rss2Parser;
import com.example.ik.rssnewscollector.FeedParser.Rss2ParserCallback;
import com.example.ik.rssnewscollector.HandleXML;
import com.example.ik.rssnewscollector.NewsAdapter;
import com.example.ik.rssnewscollector.Item.NewsItem;
import com.example.ik.rssnewscollector.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String ITEM_DATA = "ITEM";
    public final static String STATE_NEWS = "STATE_NEWS";
    public final static String MY_LOGS = "MyLogs";

    private Rss2ParserCallback mCallback;

    private Context mContext;

    ListView newsItems;
    ArrayList<NewsItem> items;
    SwipeRefreshLayout listviewRefresher;

    private String androidInsider = "http://androidinsider.ru/feed";
    private String itcUA = "http://feeds.feedburner.com/itc-ua";
    private HandleXML object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        listviewRefresher = (SwipeRefreshLayout) findViewById(R.id.listview_refresher);
        listviewRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listviewRefresher.setRefreshing(true);
                Rss2Parser parser = new Rss2Parser(androidInsider, getCallback());
                parser.parseAsync();
                listviewRefresher.setRefreshing(false);
            }
        });

        newsItems = (ListView) findViewById(R.id.listview);

        if (savedInstanceState != null){
            items = savedInstanceState.getParcelableArrayList(STATE_NEWS);
            Refresh(items);
        } else {
            Rss2Parser parser = new Rss2Parser(androidInsider, getCallback());
            parser.parseAsync();
        }

        /*if (savedInstanceState != null){
            items = savedInstanceState.getParcelableArrayList(STATE_NEWS);
        } else {
            ParseXML(androidInsider);
        }
        GetNews();*/


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
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                Rss2Parser parser = new Rss2Parser(androidInsider, getCallback());
                parser.parseAsync();
                /*
                ParseXML(androidInsider);
                GetNews();
                
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
    private Rss2ParserCallback getCallback(){
        if(mCallback == null){
            mCallback = new Rss2ParserCallback() {

                @Override
                public void onFeedParsed(List<NewsItem> items) {
                    MainActivity.this.items = (ArrayList)items;
                    Refresh(items);
                }

                @Override
                public void onError(Exception ex) {
                    Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };
        }

        return mCallback;
    }

    private void Refresh(List<NewsItem> items) {
        for(int i = 0; i < items.size(); i++){
            Log.d("Rss2ParserDemo", "Title: " + items.get(i).getTitle());
            Log.d("Rss2ParserDemo", "Image: " + (items.get(i).getImage() == null? "" : items.get(i).getImage()));
            Log.d("Rss2ParserDemo", "Source: " + (items.get(i).getSource() == null? "" : items.get(i).getSource()));
            Log.d("Rss2ParserDemo", "Link: " + items.get(i).getLink());
            Log.d("Rss2ParserDemo", "Description: " + items.get(i).getDescription());
            Log.d("Rss2ParserDemo", "Content: " + items.get(i).getContent());
            Log.d("Rss2ParserDemo", "Date: " + items.get(i).getDate());
            Log.d("Rss2ParserDemo", "--------------------------------------------------------------------");
        }

        newsItems.setAdapter(new NewsAdapter(MainActivity.this,R.layout.activity_main, items));
        newsItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                NewsItem item = (NewsItem) adapter.getItemAtPosition(position);

                if(item.getContent() != null)
                    AppNewsReview(item);
                else
                    WebSiteNewsReview(item);
            }
        });
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

                if(item.getContent() != null)
                    AppNewsReview(item);
                else
                    WebSiteNewsReview(item);
            }
        });
    }

    private void ParseXML(String feedURL) {
        items = new ArrayList<>();
        object = new HandleXML(feedURL);
        object.fetchXML();

        while(object.parsingComplete) {
            //do nothing
        }
        items.addAll(object.getItems());
    }

    private void AppNewsReview(NewsItem item) {
        Intent intent = new Intent(MainActivity.this, ItemActivity.class);
        intent.putExtra(ITEM_DATA, item.getContent());
        startActivity(intent);
    }

    private void WebSiteNewsReview(NewsItem item) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra(ITEM_DATA, item.getLink());
        startActivity(intent);
    }

}


