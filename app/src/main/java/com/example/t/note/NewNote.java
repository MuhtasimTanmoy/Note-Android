package com.example.t.note;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorSingleton;
import im.delight.android.ddp.ResultListener;

/**
 * Created by t on 11/29/17.
 */

public class NewNote extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private Meteor meteor;
    private String TAG="NewNote";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        meteor= MeteorSingleton.getInstance();
        meteor.connect();

        title= (EditText) findViewById(R.id.title);
        content= (EditText) findViewById(R.id.content);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String titleText=title.getText().toString();
        String contentText=content.getText().toString();

        Log.d(TAG, titleText);

        if (titleText.length() > 0|| contentText.length()>0) {

            Log.d(TAG, "working");


            final Map<String, Object> values = new HashMap<String, Object>();
            values.put("title",titleText);
            values.put("text", contentText);

            final Object[] queryParams = {values};



            meteor.call("notes.insert", queryParams, new ResultListener() {




                @Override

                public void onSuccess(String result) {
                    Log.d(TAG, "success  in inserting");
                }

                @Override
                public void onError(String error, String reason, String details) {

                    Log.d(TAG, "Error: " + error + " " + reason + " " + details);

                    Toast.makeText(getApplicationContext(), "Enter valid information"+ reason + error, Toast.LENGTH_SHORT).show();


                }
            });
        }

    meteor.disconnect();
    }
}
