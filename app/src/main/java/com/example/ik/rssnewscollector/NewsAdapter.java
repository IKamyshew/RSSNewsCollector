package com.example.ik.rssnewscollector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter {
    private Activity myContext;
    private ArrayList<NewsItem> data;

    static class ViewHolder {
        ImageView titleImage;
        TextView title;
        TextView source;
    }

    public NewsAdapter(Context context, int textViewResourceId,ArrayList<NewsItem> objects) {
        super(context, textViewResourceId, objects);
        myContext = (Activity) context;
        data = objects;
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
        else
            Ion.with(viewHolder.titleImage)
                    .placeholder(R.drawable.android_insider)
                    .error(R.drawable.android_insider)
                            //.animateLoad(spinAnimation)
                            //.animateIn(fadeInAnimation)
                    .load(data.get(position).getImage());

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
