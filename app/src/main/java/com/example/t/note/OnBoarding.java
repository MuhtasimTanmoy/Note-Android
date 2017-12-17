package com.example.t.note;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import im.delight.android.ddp.MeteorSingleton;
import im.delight.android.ddp.db.memory.InMemoryDatabase;

import static com.example.t.note.StaticData.ADDRESS;


public class OnBoarding extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            getWindow().setStatusBarColor(getResources().getColor(R.color.base_color));

//        }


        MeteorSingleton.createInstance(this, ADDRESS,new InMemoryDatabase());

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(1800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    Intent intent = new Intent(OnBoarding.this, SignIn.class);

                    startActivity(intent);
                }
            }
        };
        timerThread.start();


    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }


}
