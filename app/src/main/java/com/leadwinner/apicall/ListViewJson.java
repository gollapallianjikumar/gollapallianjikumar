package com.leadwinner.apicall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leadwinner.apicall.DataBase.EditData;
import com.leadwinner.apicall.DataBase.UserDataDbHelper;
import com.leadwinner.apicall.DataBase.UserDataclass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewJson extends AppCompatActivity {
    ListView jsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_json);
        jsonData = findViewById(R.id.jsonData);
        getData();
    }

    public void getData() {
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://reqres.in/api/users?page=1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SQLiteDatabase sqLiteDatabase = userDataDbHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                try {


                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String userId = jsonObject.getString("id");
//                        String query1=" SELECT * FROM "+UserDataclass.TABLE_NAME+" WHERE "+UserDataclass.ID+" = ? ";
                        Cursor c = sqLiteDatabase.rawQuery(" SELECT * FROM " + UserDataclass.TABLE_NAME + " WHERE " + UserDataclass.ID + " = " + userId, null);
                        if (c.moveToNext()) {

                        } else {
                            if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("userUpdate")) {


                                Intent intent = new Intent(ListViewJson.this, EditData.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Inserting record
                                contentValues.put(UserDataclass.Status, "1");
                                contentValues.put(UserDataclass.ID, jsonObject.getString("id"));
                                contentValues.put(UserDataclass.NAME, jsonObject.getString("first_name"));
                                contentValues.put(UserDataclass.JOB, jsonObject.getString("last_name"));
                                long row = sqLiteDatabase.insert(UserDataclass.TABLE_NAME, null, contentValues);
                                Log.v("rowid", "" + row);
                            }
                        }
                        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(ListViewJson.this);
                        SQLiteDatabase sqLiteDatabasereable = userDataDbHelper.getReadableDatabase();
                        String query = " SELECT Name,Job,Id,Status FROM " + UserDataclass.TABLE_NAME;
                        Cursor cursor = sqLiteDatabasereable.rawQuery(query, null);
                        Log.v("sqliteData", "" + DatabaseUtils.dumpCursorToString(cursor));
                        ArrayList<UserEntity> arrayList = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            UserEntity userEntity = new UserEntity();
                            userEntity.setName(cursor.getString(cursor.getColumnIndex(UserDataclass.NAME)));
                            userEntity.setJob(cursor.getString(cursor.getColumnIndex(UserDataclass.JOB)));
                            arrayList.add(userEntity);
                        }
                        UserDataAdapter userDataAdapter = new UserDataAdapter(ListViewJson.this, arrayList);
                        jsonData.setAdapter(userDataAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("JsonData", "" + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}