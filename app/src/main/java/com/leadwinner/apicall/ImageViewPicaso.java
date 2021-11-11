package com.leadwinner.apicall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageViewPicaso extends AppCompatActivity {
        ImageView picasso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_picaso);
        picasso=findViewById(R.id.picasso);

        //String url="https://images.idgesg.net/images/article/2020/08/android-awkward-timing-100855433-large.jpg";
        Picasso.get().load( "http://i.imgur.com/DvpvklR.png").into(picasso);

    }
}