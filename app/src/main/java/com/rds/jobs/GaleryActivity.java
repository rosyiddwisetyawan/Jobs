package com.rds.jobs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.robertsimoes.shareable.Shareable;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class GaleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);

        getIncomingIntent();
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("id")){

            String currency = getIntent().getStringExtra("currency");
            String min = getIntent().getStringExtra("min");
            String max = getIntent().getStringExtra("max");
            String id = getIntent().getStringExtra("id");
            String image = getIntent().getStringExtra("image");
            String company = getIntent().getStringExtra("company");
            String date = getIntent().getStringExtra("date");
            String position = getIntent().getStringExtra("position");
            String jobtype = getIntent().getStringExtra("jobtype");
            String city = getIntent().getStringExtra("city");
            String url = getIntent().getStringExtra("url");
            String description = getIntent().getStringExtra("description");
            String responsibility = getIntent().getStringExtra("responsibility");
            String requirement = getIntent().getStringExtra("requirement");

            setData(currency, min, max, id, image, company,date,position,jobtype,city,url,description,responsibility,requirement);
        }
    }

    private void setData(String Mcurrency, String Mmin, String Mmax, String Mid, String Mimage, String Mcompany, String Mdate,
                         String Mposition, String Mjobtype, String Mcity,
                         final String Murl, String Mdescription, String Mresponsibility, String Mrequirement){

        TextView currency = findViewById(R.id.detailcur1);
        currency.setText(Mcurrency);

        TextView currency2 = findViewById(R.id.detailcur2);
        currency2.setText(Mcurrency);

        TextView min = findViewById(R.id.detailgajiawal);
        min.setText(Mmin);

        TextView max = findViewById(R.id.detailgajiakhir);
        max.setText(Mmax);

        TextView about = findViewById(R.id.detailabout);
        about.setText("About "+Mcompany);

        ImageView image = findViewById(R.id.detailimage);
        Glide.with(this)
                .load(Mimage)
                .apply(centerCropTransform()
                        .override(100, 100)
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(image);

        TextView company = findViewById(R.id.detailcompany);
        company.setText(Mcompany);

        TextView position = findViewById(R.id.detailposition);
        position.setText(Mposition);

        TextView city = findViewById(R.id.detailcity);
        city.setText(Mcity);

        TextView description = findViewById(R.id.detaildescription);
        description.setText(Mdescription);

        TextView responsibility = findViewById(R.id.detailresponsibility);
        responsibility.setText(Mresponsibility);

        TextView requeirement = findViewById(R.id.detailrequeirement);
        requeirement.setText(Mrequirement);

        ImageView imgfb = findViewById(R.id.imgfb);
        Glide.with(this)
                .load("http://lcie.be/wp-content/uploads/2018/03/1000px-F_icon.svg_-1.png")
                .apply(centerCropTransform()
                        .override(40, 40)
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(imgfb);

        ImageView imgtwitter = findViewById(R.id.imgtwitter);
        Glide.with(this)
                .load("https://bellevuechurch.com/files/2018/08/twitter.png")
                .apply(centerCropTransform()
                        .override(40, 40)
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(imgtwitter);

        ImageView imgplus = findViewById(R.id.imgplus);
        Glide.with(this)
                .load("https://vignette.wikia.nocookie.net/flutter-butterfly-sanctuary/images/2/25/Icon%C2%A7GooglePlus.png/revision/latest?cb=20150420171434")
                .apply(centerCropTransform()
                        .override(40, 40)
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(imgplus);

        ImageView imgmessage = findViewById(R.id.imgmessage);
        Glide.with(this)
                .load("https://cdn0.iconfinder.com/data/icons/apple-apps/100/Apple_Messages-512.png")
                .apply(centerCropTransform()
                        .override(40, 40)
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(imgmessage);

        ImageView imglinked = findViewById(R.id.imglinked);
        Glide.with(this)
                .load("http://www.stickpng.com/assets/images/58e91afdeb97430e81906504.png")
                .apply(centerCropTransform()
                        .override(40, 40)
                        .placeholder(R.drawable.thumbnailimage)
                        .error(R.drawable.thumbnailimage)
                        .priority(Priority.HIGH))
                .into(imglinked);


        LinearLayout fb = findViewById(R.id.sos_fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook(Murl);
            }
        });

        LinearLayout twitter = findViewById(R.id.sos_twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitter(Murl);
            }
        });

        LinearLayout plus = findViewById(R.id.sos_plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus(Murl);
            }
        });

        LinearLayout message = findViewById(R.id.sos_message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messages(Murl);
            }
        });

        LinearLayout linked = findViewById(R.id.sos_linked);
        linked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkedin(Murl);
            }
        });

        ImageView btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(i);
            }
        });
    }

    public void facebook(String message) {
        Shareable shareInstance = new Shareable.Builder(this)
                .message("Visit to link")
                .socialChannel(Shareable.Builder.FACEBOOK)
                .url(message)
                .build();
        shareInstance.share();
    }

    public void twitter(String message) {
        Shareable shareInstance = new Shareable.Builder(this)
                .message("Visit to link")
                .socialChannel(Shareable.Builder.TWITTER)
                .url(message)
                .build();
        shareInstance.share();
    }

    public void plus(String message) {
        Shareable shareInstance = new Shareable.Builder(this)
                .message("Visit to link")
                .socialChannel(Shareable.Builder.GOOGLE_PLUS)
                .url(message)
                .build();
        shareInstance.share();
    }

    public void messages(String message) {
        Shareable shareInstance = new Shareable.Builder(this)
                .message("Visit to link")
                .socialChannel(Shareable.Builder.MESSAGES)
                .url(message)
                .build();
        shareInstance.share();
    }

    public void linkedin(String message) {
        Shareable shareInstance = new Shareable.Builder(this)
                .message("Visit to link")
                .socialChannel(Shareable.Builder.LINKED_IN)
                .url(message)
                .build();
        shareInstance.share();
    }
}
