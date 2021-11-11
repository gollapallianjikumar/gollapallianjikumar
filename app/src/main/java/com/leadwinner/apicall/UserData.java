package com.leadwinner.apicall;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leadwinner.apicall.DataBase.UserDataDbHelper;
import com.leadwinner.apicall.DataBase.UserDataclass;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class UserData extends AppCompatActivity {
    EditText userName;
    Spinner userSpinner;
    Button userButton;
    String[] jobs = {"Select One", "Android Developer", "Web Developer", "AWS", "Testing"};
    String rowId;
    JSONObject jsonObject;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        userName = findViewById(R.id.userName);
        userSpinner = findViewById(R.id.userSpinner);
        userButton = findViewById(R.id.userButton);
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, jobs);
        userSpinner.setAdapter(arrayAdapter);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userName.getText().toString().equals("")) {
                    if (userSpinner.getSelectedItemPosition() != 0) {
//                        int status=0;
                         sqLiteDatabase = userDataDbHelper.getWritableDatabase();
                        Random random = new Random();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(UserDataclass.ID, random.nextInt());
                        contentValues.put(UserDataclass.NAME, userName.getText().toString());
                        contentValues.put(UserDataclass.JOB, userSpinner.getSelectedItem().toString());
                        contentValues.put(UserDataclass.Status, "0");

                        long row = sqLiteDatabase.insert(UserDataclass.TABLE_NAME, null, contentValues);
                        Log.d("row id", "" + row);
                        addData();

                    } else {

                    }
                } else {
                    userName.setError("Enter User Name");
                }
            }
        });

    }

    public void addData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(this);
        ArrayList<UserEntity> userEntities = new ArrayList<>();


         sqLiteDatabase = userDataDbHelper.getReadableDatabase();
        String query = " SELECT * FROM " + UserDataclass.TABLE_NAME+" WHERE "+UserDataclass.Status+"=0";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext()) {
//             jsonObject = new JSONObject();
//            UserEntity userEntity = new UserEntity();
            rowId= cursor.getString(cursor.getColumnIndex(UserDataclass.ID));
//            userEntity.setName(cursor.getString(cursor.getColumnIndex(UserDataclass.NAME)));
//            userEntity.setJob(cursor.getString(cursor.getColumnIndex(UserDataclass.JOB)));
//            userEntity.setStatus(cursor.getString(cursor.getColumnIndex(UserDataclass.Status)));
//            userEntities.add(userEntity);
//            Log.e("userData",""+ DatabaseUtils.dumpCursorToString(cursor));
//            Log.e("",""+rowId);

            try{

                jsonObject.put("name",cursor.getString(cursor.getColumnIndex(UserDataclass.NAME)));
                jsonObject.put("job",cursor.getString(cursor.getColumnIndex(UserDataclass.JOB)));
                Log.v("Objectdata",""+jsonObject);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        cursor.close();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://reqres.in/api/users", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sqLiteDatabase=userDataDbHelper.getWritableDatabase();
               ContentValues contentValues=new ContentValues();
               contentValues.put(UserDataclass.Status,1);

               sqLiteDatabase.update(UserDataclass.TABLE_NAME,contentValues,UserDataclass.ID+ " =? ",new String[]{rowId});
               Log.e("Datapass",""+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        Intent intent=new Intent(UserData.this,ListViewJson.class);
        startActivity(intent);
    }
}