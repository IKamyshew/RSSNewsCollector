package com.example.ik.rssnewscollector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class ItemActivity extends AppCompatActivity {
    String item;
    TextView contentText;

    public static final Map<String, WeakReference<Drawable>> mDrawableCache = Collections.synchronizedMap(new WeakHashMap<String, WeakReference<Drawable>>());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        item = intent.getStringExtra(MainActivity.ITEM_DATA);

        /*Html.ImageGetter igLoader = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                //Если рисунок существует в кеше, то просто устанавливаем его и ничего не делаем дальше
                if (mDrawableCache.containsKey(source))
                    return mDrawableCache.get(source).get();
                //В противном случае, скачиваем его из сети
                new ImageDownloadAsyncTask(source, item, messageView).execute();
                //Пока он скачивается устанавливаем пустой рисунок
                return new BitmapDrawable(getResources());
            }
        };*/

        contentText = (TextView) findViewById(R.id.itemContent);
        contentText.setMovementMethod(new ScrollingMovementMethod());

        contentText.setText(Html.fromHtml(item));

    }

    //Создаем второй ImageGetter.
//В нем возникнет потребность, когда файл загрузится
    /*Html.ImageGetter igCached = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            //Просто возвращаем наш рисунок из кеша
            if (mDrawableCache.containsKey(source))
                return mDrawableCache.get(source).get();
            return null;
        }
    };*/

    /*private Html.ImageGetter GetHtmlImage(String source) {
        new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                ImageLoader#loadImage(url, new OnImageLoadedListener() {

                //...

                void onImageLoaded(@Nullable Bitmap image) {
                    textView.setText(Html.fromHtml(message, image, null));
                }

            });
        }
    };*/
        /*return new Html.ImageGetter() {
            @Override public Drawable getDrawable(String source) {
                Drawable drawFromPath;
                int path =
                        ItemActivity.this.getResources().getIdentifier(source, "drawable",
                                "com.package...");
                drawFromPath = (Drawable) ItemActivity.this.getResources().getDrawable(path);
                drawFromPath.setBounds(0, 0, drawFromPath.getIntrinsicWidth(),
                        drawFromPath.getIntrinsicHeight());
                return drawFromPath;
            }
        };
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_back:
                BackToMain();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void BackToMain() {
        Intent intent = new Intent(ItemActivity.this, MainActivity.class);
        startActivity(intent);
    }

/*class ImageDownloadAsyncTask extends AsyncTask<Void, Void, Void> {
    private String source;
    private String message;
    private TextView textView;

    public ImageDownloadAsyncTask(String source, String message,
                                  TextView textView) {
        this.source = source;
        this.message = message;
        this.textView = textView;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (!mDrawableCache.containsKey(source)) {
            try {
                //Скачиваем картинку в наш кэш
                URL url = new URL(source);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                Drawable drawable = Drawable.createFromStream(is, "src");

                // Если нужно, чтобы рисунки не масштабировались,
                // закомментируйте строчку выше и расскомментируйте код
                // ниже.

				/*
				Bitmap bmp = BitmapFactory.decodeStream(is);
				DisplayMetrics dm =
				MainActivity.this.getResources().getDisplayMetrics();
				bmp.setDensity(dm.densityDpi); Drawable drawable=new
				BitmapDrawable(MainActivity.this.getResources(),bmp);


                is.close();

                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                mDrawableCache.put(source, new WeakReference<Drawable>(
                        drawable));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // Переустанавливаем содержимое нашего поля
        textView.setText(Html.fromHtml(message, igCached, null));
    }
}*/
}

