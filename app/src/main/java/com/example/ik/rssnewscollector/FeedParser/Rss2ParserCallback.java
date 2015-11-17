package com.example.ik.rssnewscollector.FeedParser;

import com.example.ik.rssnewscollector.Item.NewsItem;
import java.util.List;

public abstract class Rss2ParserCallback {

    public abstract void onFeedParsed(List<NewsItem> items);

    public abstract void onError(Exception ex);
}
