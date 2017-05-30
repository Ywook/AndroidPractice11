package com.examples.androidpractice11;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {
    Handler mhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mhandler = new Handler();
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}
