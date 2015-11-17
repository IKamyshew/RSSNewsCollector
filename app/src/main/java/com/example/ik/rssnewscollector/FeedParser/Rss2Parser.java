package com.example.ik.rssnewscollector.FeedParser;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

import com.example.ik.rssnewscollector.Item.NewsItem;

public class Rss2Parser extends FeedParser {

    private Rss2ParserCallback mCallback;

    public Rss2Parser(String feedUrl, Rss2ParserCallback callback) {
        super(feedUrl);
        this.mCallback = callback;
    }

    public void parseAsync(){
        AsyncTask task = new AsyncTask() {

            private Exception mEx;
            private List<NewsItem> items;

            @Override
            protected void onPostExecute(Object result) {
                if(mEx != null){
                    if(mCallback != null){
                        mCallback.onError(mEx);
                    }
                } else {
                    if(mCallback != null){
                        mCallback.onFeedParsed(items);
                    }
                }
            }

            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    items = parse();
                } catch(Exception e){
                    mEx = e;
                }

                return null;
            }
        };

        task.execute();
    }

    public List<NewsItem> parse() {
        final NewsItem currentMessage = new NewsItem();
        RootElement root = new RootElement("rss");
        final List<NewsItem> messages = new ArrayList<NewsItem>();
        Element channel = root.getChild("channel");
        Element item = channel.getChild(ITEM);

        channel.getChild(TITLE).setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                currentMessage.setSource(body);
            }
        });
        item.setEndElementListener(new EndElementListener() {
            public void end() {
                messages.add(currentMessage.copy());
            }
        });
        item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                currentMessage.setTitle(body);
            }
        });
        item.getChild(LINK).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setLink(body);
            }
        });
        item.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setDescription(body);
            }
        });
        item.getChild("http://purl.org/rss/1.0/modules/content/", "encoded").setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setContent(body);
            }
        });
        item.getChild(CONTENT).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setContent(body);
            }
        });
        item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener(){
            public void end(String body) {
                currentMessage.setDate(body);
            }
        });
        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return messages;
    }
}
