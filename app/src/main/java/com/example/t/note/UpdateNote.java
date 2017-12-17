package com.example.t.note;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import im.delight.android.ddp.Meteor;
import im.delight.android.ddp.MeteorSingleton;
import im.delight.android.ddp.ResultListener;

/**
 * Created by root on 12/1/17.
 */

public class UpdateNote extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private Meteor meteor;
    private String TAG="UpdateNote";
    private Bundle bundle;
    private String id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        meteor= MeteorSingleton.getInstance();
        meteor.connect();

        title= (EditText) findViewById(R.id.title_edit);
        content= (EditText) findViewById(R.id.content_edit);

        bundle=getIntent().getExtras();
        title.setText(bundle.getString("title"));
        content.setText(bundle.getString("content"));
        id=bundle.getString("id");



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String titleText=title.getText().toString();
        String contentText=content.getText().toString();

        Log.d(TAG, titleText);
        Log.d(TAG, contentText);






        if (titleText.length() > 0|| contentText.length()>0) {

            Log.d(TAG, "working");


            final Map<String, Object> values = new HashMap<String, Object>();
            values.put("id",id);
            values.put("title",titleText);
            values.put("text", contentText);

            final Object[] queryParams = {values};



            meteor.call("notes.update", queryParams, new ResultListener() {




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
