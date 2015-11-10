package com.example.ik.rssnewscollector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by ik on 30.10.2015.
 */
public class MainActivity extends Activity {

    EditText title;
    EditText link;
    EditText description;

    Button b1;
    Button b2;

    private String finalUrl = "http://tutorialspoint.com/android/sampleXML.xml";
    private HandleXML object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.editText);
        link = (EditText) findViewById(R.id.editText2);
        description = (EditText) findViewById(R.id.editText3);

        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object = new HandleXML(finalUrl);
                object.fetchXML();

                while(object.parsingComplete) {
                    title.setText(object.getTitle());
                    link.setText(object.getLink());
                    description.setText(object.getDescription());
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}


