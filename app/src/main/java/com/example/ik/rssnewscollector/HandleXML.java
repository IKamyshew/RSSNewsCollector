package com.example.ik.rssnewscollector;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HandleXML {
    private ArrayList<NewsItem> items;
    private NewsItem newItem;
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXML(String url) {
        urlString = url;
    }

    public ArrayList<NewsItem> getItems() {
        return items;
    }

    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;
        items = new ArrayList<>();
        newItem = new NewsItem();

        try {
            event = myParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch(event) {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals("item")) {
                            if(newItem != null)
                                items.add(newItem);

                            newItem = new NewsItem();
                        }
                        else if(name.equals("title"))
                            newItem.setTitle(text);

                        else if (name.equals("link"))
                            newItem.setLink(text);

                        else if (name.equals(("description")))
                            newItem.setDescription(text);

                        else if (name.equals(("content:encoded")))
                            newItem.setContent(text);

                        else if (name.equals(("pubDate")))
                            newItem.setDate(text);

                        else{ }

                        break;
                }

                event = myParser.next();

            }

            parsingComplete = false;

        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try{
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setReadTimeout(10000 /* milliseconds */);
                    connection.setConnectTimeout(15000 /* milliseconds */);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);

                    //starts the query
                    connection.connect();
                    InputStream stream  = connection.getInputStream();

                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myParser = xmlFactoryObject.newPullParser();

                    myParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myParser.setInput(stream, null);

                    parseXMLAndStoreIt(myParser);
                    stream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        thread.start();
    }

}
