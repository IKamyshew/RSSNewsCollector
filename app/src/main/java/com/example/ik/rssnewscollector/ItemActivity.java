package com.example.ik.rssnewscollector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {
    String item;
    TextView contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        item = intent.getStringExtra(MainActivity.ITEM_DATA);

        contentText = (TextView) findViewById(R.id.itemContent);
        contentText.setText(Html.fromHtml(item));

    }

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

}
