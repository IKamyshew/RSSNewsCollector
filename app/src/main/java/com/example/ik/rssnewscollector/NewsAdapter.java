package com.example.ik.rssnewscollector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
        else
            Ion.with(itemImage)
                    .placeholder(R.drawable.itc)
                    .error(R.drawable.itc)
                    //.animateLoad(spinAnimation)
                    //.animateIn(fadeInAnimation)
                    .load(data.get(position).getImage());

        TextView title = (TextView) rowView.findViewById(R.id.itemTitle);
        String tit = data.get(position).getTitle();
        if (data.get(position).getTitle() == null)
            title.setText("Empty title");
        else
            title.setText(data.get(position).getTitle());

        TextView source = (TextView) rowView.findViewById(R.id.itemSource);
        if (data.get(position).getLink() == null)
            source.setText("androidinsider.ru");
        else
            source.setText(data.get(position).getLink());

        return rowView;
    }

    /*class GetImageFromURL extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground (String... urls) {
            URL url = null;
            try {
                url = new URL(urls[0]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return bmp;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("Adapter", "MalformedURLException on \"" + urls[0] + "\"");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Adapter", "IOException on \"" + urls[0] + "\"");
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            // TODO: check this.exception
            // TODO: do something with the feed

        }
    }*/
}
