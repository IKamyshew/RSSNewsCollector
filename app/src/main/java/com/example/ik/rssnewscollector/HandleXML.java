package com.example.ik.rssnewscollector;

import android.util.Log;

import com.example.ik.rssnewscollector.Item.NewsItem;

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
    private String source;
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
                            if (source != null)
                                newItem.setSource(source);
                        }
                        else if(name.equals("title"))
                            if (source == null)
                                source = text;
                            else
                            newItem.setTitle(text);

                        else if (name.equals("link"))
                            newItem.setLink(text);

                        else if (name.equals(("description")))
                            newItem.setDescription(text);

                        else if (name.equals(("content:encoded"))) {
                            try {
                                newItem.setImage(text.substring(text.indexOf("http://"), text.indexOf("\">")));
                            } catch (Exception e) {
                                Log.e("MyLogs","Failed to add image link");
                            }
                            Log.i("MyLogs", "image for " + newItem.getTitle() + " added");
                            newItem.setContent(text);
                        }

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

/*
int startIndex = text.indexOf("<p><a http://");
                            while (startIndex >= 0) {
                                System.out.println(startIndex);
                                startIndex = text.indexOf("<p><a http://", startIndex + 1);
                            }
                            int endIndex = text.indexOf("\"a>");
                            while (endIndex >= 0) {
                                System.out.println(endIndex);
                                endIndex = text.indexOf("\"a>", endIndex + 1);
                            }


public void setDescription(String description) {
 this.description = description;

 //parse description for any image or video links
 if (description.contains("<img ")){
  String img  = description.substring(description.indexOf("<img "));
  String cleanUp = img.substring(0, img.indexOf(">")+1);
  img = img.substring(img.indexOf("src=") + 5);
  int indexOf = img.indexOf("'");
  if (indexOf==-1){
   indexOf = img.indexOf("\"");
  }
  img = img.substring(0, indexOf);

  setImgLink(img);

  this.description = this.description.replace(cleanUp, "");
 }
}
 */
