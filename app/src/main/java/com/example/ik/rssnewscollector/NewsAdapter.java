package com.example.ik.rssnewscollector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter {
    private Activity myContext;
    private NewsItem[] data;

    public NewsAdapter(Context context, int textViewResourceId,NewsItem[] objects) {
        super(context, textViewResourceId, objects);
        // TODO Auto-generated constructor stub
        myContext = (Activity) context;
        data = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.news_item, null);

        ImageView itemImage = (ImageView) rowView.findViewById(R.id.itemImage);
        if (data[position].getImage() == null)
            itemImage.setImageResource(R.drawable.itc);

        TextView title = (TextView) rowView.findViewById(R.id.itemTitle);
        title.setText(data[position].getTitle());

        TextView source = (TextView) rowView.findViewById(R.id.itemSource);

        source.setText(data[position].getSource());

        return rowView;
    }


}
