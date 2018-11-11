package com.rds.jobs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView)findViewById(R.id.idsplash);
        Glide.with(getApplicationContext())
                .load("https://s3-ap-southeast-1.amazonaws.com/ricebowl/employers/4913.png")
                .apply(centerCropTransform()
                        .override(200, 200)
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(img);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent m = new Intent(MainActivity.this,MenuActivity.class);
                startActivity(m);
            }
        },SPLASH_TIME_OUT);
    }
}
