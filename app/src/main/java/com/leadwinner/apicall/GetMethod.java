package com.leadwinner.apicall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leadwinner.apicall.DataBase.UserDataDbHelper;
import com.leadwinner.apicall.DataBase.UserDataclass;

import org.json.JSONArray;
import org.json.JSONObject;



public class GetMethod extends AppCompatActivity {
    Button listViewButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_method);
        listViewButton = findViewById(R.id.listViewButton);
        listViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent(GetMethod.this,ListViewJson.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

}