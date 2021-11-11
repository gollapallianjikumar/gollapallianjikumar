package com.leadwinner.apicall.MultiThreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.leadwinner.apicall.ListViewJson;
import com.leadwinner.apicall.R;

import java.util.ArrayList;

public class MultiThreadExample extends AppCompatActivity
{
        Button userData;
        Button listview;
        Button picasso;
        Button imageupload;
        Button fragment;
        Button image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread_example);
        userData=findViewById(R.id.userDataThread);
        listview=findViewById(R.id.Listview);
        picasso=findViewById(R.id.Picasso);
        imageupload=findViewById(R.id.Imageupload);
        fragment=findViewById(R.id.Fragment);
        image=findViewById(R.id.Image);

        userData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<String> arrayList=new ArrayList();

                        for (int i=0;i<10000000;i++)
                        {
                            arrayList.add("kumar");
                            arrayList.add("kumar");
                        }
                        Log.d("main", "Activity: "+arrayList.size());
                        Intent intent=new Intent(MultiThreadExample.this,ActivityOne.class);
                        startActivity(intent);
                    }
                });
                thread.start();
            }
        });
        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MultiThreadExample.this,ActivityTwo.class);
                startActivity(intent);
            }
        });

    }
}