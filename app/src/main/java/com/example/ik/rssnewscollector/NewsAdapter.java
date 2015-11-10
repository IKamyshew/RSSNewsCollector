package com.example.ik.rssnewscollector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter {
    private Activity myContext;
    private ArrayList<NewsItem> data;

    public NewsAdapter(Context context, int textViewResourceId,ArrayList<NewsItem> objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        myContext = (Activity) context;
        data = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.news_item, null);

        ImageView itemImage = (ImageView) rowView.findViewById(R.id.itemImage);
        if (data.get(position).getImage() == null)
            itemImage.setImageResource(R.drawable.itc);

        TextView title = (TextView) rowView.findViewById(R.id.itemTitle);
        title.setText(data.get(position).getTitle());

        TextView source = (TextView) rowView.findViewById(R.id.itemSource);

        source.setText(data.get(position).getSource());

        return rowView;
    }


}
