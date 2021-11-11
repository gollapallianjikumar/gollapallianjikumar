package com.leadwinner.apicall.MultiThreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.leadwinner.apicall.R;

import java.util.ArrayList;

public class ActivityOne extends AppCompatActivity {
   Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        button1=findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Thread thread=new Thread(new Runnable() {
                  @Override
                  public void run() {
                      ArrayList<String>  strings=new ArrayList<>();

                      for(int i=0;i<100000000;i++)
                      {
                          strings.add("anji");
                          strings.add("anji");
                      }
                      Log.d("loop", "onClick: "+strings.size());
                      Intent intent=new Intent(ActivityOne.this,ActivityThree.class);
                      startActivity(intent);
                  }
              });
              thread.start();
            }
        });
    }
}