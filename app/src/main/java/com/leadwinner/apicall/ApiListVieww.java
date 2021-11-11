package com.leadwinner.apicall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
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
import com.leadwinner.apicall.DataBase.GetApiData;
import com.leadwinner.apicall.DataBase.GetApiDataDbHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiListVieww extends AppCompatActivity
{
    ListView apiListView;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_list_vieww);
        apiListView=findViewById(R.id.apiListView);
        getData();
    }
    public void getData() {
        apiListView =findViewById(R.id.apiListView);
        GetApiDataDbHelper getApiDataDbHelper = new GetApiDataDbHelper(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://reqres.in/api/unknown", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                SQLiteDatabase sqLiteDatabase = getApiDataDbHelper.getWritableDatabase();
                ContentValues contentValues1 = new ContentValues();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        Log.d("Stringid", "onResponse: " + id);
                        String query = " SELECT * FROM " + GetApiData.TABLE_NAME + " WHERE " + GetApiData.ID + " = " + id;
                        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
                        Log.d("jsonObject", "onResponse: " + jsonObject);
                        if (cursor.moveToNext()) {
                            contentValues1.put(GetApiData.ID, jsonObject.getString("id"));
                            contentValues1.put(GetApiData.NAME, jsonObject.getString("name"));
                            contentValues1.put(GetApiData.YEAR, jsonObject.getString("year"));
                            contentValues1.put(GetApiData.COLOR, jsonObject.getString("color"));
                            long row_id = sqLiteDatabase.insert(GetApiData.TABLE_NAME, null, contentValues1);
                            Log.v("Row", "onResponse: " + row_id);
                        }
                        GetApiDataDbHelper getApiDataDbHelper1 = new GetApiDataDbHelper(ApiListVieww.this);
                        SQLiteDatabase sqLiteDatabase1 = getApiDataDbHelper1.getReadableDatabase();
                        String Query = " SELECT * FROM " + GetApiData.TABLE_NAME;
                        Cursor cursor1 = sqLiteDatabase1.rawQuery(Query, null);
                        Log.d("Cursor Data", "onResponse: " + DatabaseUtils.dumpCursorToString(cursor1));
                        ArrayList<ApiEntity> apiEntities = new ArrayList<>();
                        while (cursor1.moveToNext()) {
                            ApiEntity apiEntity = new ApiEntity();
                            apiEntity.setId(cursor1.getString(cursor1.getColumnIndex(GetApiData.ID)));
                            apiEntity.setName(cursor1.getString(cursor1.getColumnIndex(GetApiData.NAME)));
                            apiEntity.setYear(cursor1.getString(cursor1.getColumnIndex(GetApiData.YEAR)));
                            apiEntity.setColor(cursor1.getString(cursor1.getColumnIndex(GetApiData.COLOR)));
                            apiEntities.add(apiEntity);
                            Log.d("arraylistdatasize", "onResponse: " + apiEntities.size());
                            Log.d("arraylistdata", "onResponse: " + apiEntities.size());
                        }
                        ApiAdapter apiAdapter = new ApiAdapter(ApiListVieww.this, apiEntities);
                        apiListView.setAdapter(apiAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("responseData", "onResponse: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}