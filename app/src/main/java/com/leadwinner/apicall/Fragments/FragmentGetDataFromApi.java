package com.leadwinner.apicall.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leadwinner.apicall.ApiAdapter;
import com.leadwinner.apicall.ApiEntity;
import com.leadwinner.apicall.DataBase.GetApiData;
import com.leadwinner.apicall.DataBase.GetApiDataDbHelper;
import com.leadwinner.apicall.DataBase.UserDataclass;
import com.leadwinner.apicall.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentGetDataFromApi extends Fragment {
    ListView apiListView;

    public static FragmentGetDataFromApi newInstance(int num) {
        FragmentGetDataFromApi fragmentGetDataFromApi = new FragmentGetDataFromApi();
        Bundle bundle = new Bundle();
        bundle.putInt("num", num);
        fragmentGetDataFromApi.setArguments(bundle);
        return fragmentGetDataFromApi;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_api_list_vieww, container, false);
        apiListView = view.findViewById(R.id.apiListView);
        GetApiDataDbHelper getApiDataDbHelper = new GetApiDataDbHelper(getActivity());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
//                        String query = " SELECT * FROM " + GetApiData.TABLE_NAME + " WHERE " + GetApiData.ID + " = " + id;
                        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + GetApiData.TABLE_NAME + " WHERE " + GetApiData.ID + " = " + id, null);
                        Log.d("jsonObject", "onResponse: " + jsonObject);
                        if (cursor.moveToNext()) {

                        }
                        else
                        {
                            contentValues1.put(GetApiData.ID, jsonObject.getString("id"));
                            contentValues1.put(GetApiData.NAME, jsonObject.getString("name"));
                            contentValues1.put(GetApiData.YEAR, jsonObject.getString("year"));
                            contentValues1.put(GetApiData.COLOR, jsonObject.getString("color"));
                            contentValues1.put(GetApiData.PANTONE_VALUE, jsonObject.getString("pantone_value"));
                            long row_id = sqLiteDatabase.insert(GetApiData.TABLE_NAME, null, contentValues1);
                            Log.d("Row", "onResponse: " + row_id);
                        }
                        GetApiDataDbHelper getApiDataDbHelper1 = new GetApiDataDbHelper(getActivity());
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
                            apiEntity.setPantone_value(cursor1.getString(cursor1.getColumnIndex(GetApiData.PANTONE_VALUE)));
                            apiEntities.add(apiEntity);
                            Log.d("arraylistdatasize", "onResponse: " + apiEntities.size());
                            Log.d("arraylistdata", "onResponse: " + apiEntities.size());
                        }
                        ApiAdapter apiAdapter = new ApiAdapter(getActivity(), apiEntities);
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
        return view;
    }
}
