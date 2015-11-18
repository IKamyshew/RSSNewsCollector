package com.example.ik.rssnewscollector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ik.rssnewscollector.Item.NewsItem;
import com.koushikdutta.ion.Ion;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.List;

public class NewsAdapter extends ArrayAdapter {
    private Activity myContext;
    private List<NewsItem> data;
    private ImageLoader imageLoader;

    static class ViewHolder {
        ImageView titleImage;
        TextView title;
        TextView source;
    }

    public NewsAdapter(Context context, int textViewResourceId,List<NewsItem> objects) {
        super(context, textViewResourceId, objects);
        myContext = (Activity) context;
        data = objects;

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(new ImageLoaderConfiguration.Builder(myContext)
                .memoryCacheExtraOptions(110, 75)
                .threadPoolSize(10)
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .defaultDisplayImageOptions(
                        new DisplayImageOptions.Builder()
                                .displayer(new RoundedBitmapDisplayer(10))
                                .showImageOnFail(R.drawable.itc)
                                .cacheInMemory(true)
                                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                                .build())
                .build());


                        //.createDefault(myContext));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = myContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.news_item, null);

            viewHolder = new ViewHolder();

            viewHolder.titleImage = (ImageView) convertView.findViewById(R.id.itemImage);
            viewHolder.title = (TextView) convertView.findViewById(R.id.itemTitle);
            viewHolder.source = (TextView) convertView.findViewById(R.id.itemSource);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (data.get(position).getImage() == null)
            viewHolder.titleImage.setImageResource(R.drawable.android_insider);
        else {
            imageLoader.displayImage(data.get(position).getImage(), viewHolder.titleImage);
            /*Ion.with(viewHolder.titleImage)
                    .placeholder(R.drawable.android_insider)
                    .error(R.drawable.android_insider)
                    .load(data.get(position).getImage());
                    //.animateLoad(spinAnimation)
                    //.animateIn(fadeInAnimation)
                    */
        }

        if (data.get(position).getTitle() == null)
            viewHolder.title.setText("Empty title");
        else
            viewHolder.title.setText(data.get(position).getTitle());

        if (data.get(position).getLink() == null)
            viewHolder.source.setText("androidinsider.ru");
        else
            viewHolder.source.setText(data.get(position).getSource());

        return convertView;
    }
}
