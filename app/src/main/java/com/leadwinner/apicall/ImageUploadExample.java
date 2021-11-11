package com.leadwinner.apicall;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URL;

public class ImageUploadExample extends AppCompatActivity {
    ImageView imageView;
    Bitmap bmp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload_example);
        imageView=findViewById(R.id.imageView);
        String image="https://images.idgesg.net/images/article/2020/08/android-awkward-timing-100855433-large.jpg";

        Glide.with(this).load(image).into(imageView);

//        Thread thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(image);
//                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                   runOnUiThread(new Runnable() {
//                       @Override
//                       public void run() {
//                           imageView.setImageBitmap(bmp);
//                       }
//                   });
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();

    }
}