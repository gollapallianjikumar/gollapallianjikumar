package com.leadwinner.apicall.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import com.leadwinner.apicall.DataBase.EditData;
import com.leadwinner.apicall.DataBase.UserDataDbHelper;
import com.leadwinner.apicall.DataBase.UserDataclass;
import com.leadwinner.apicall.ListViewJson;
import com.leadwinner.apicall.R;
import com.leadwinner.apicall.UserDataAdapter;
import com.leadwinner.apicall.UserEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentGetData extends Fragment
{
    ListView jsonView;
    public static FragmentGetData newInstance(int num)
    {
        FragmentGetData fragmentGetData=new FragmentGetData();
        Bundle bundle=new Bundle();
        bundle.putInt("num",num);
        fragmentGetData.setArguments(bundle);
        return fragmentGetData;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.activity_list_view_json,container,false);
        jsonView=view.findViewById(R.id.jsonData);
        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                            /*if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("userUpdate")) {


                                Intent intent = new Intent(getActivity(), EditData.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {*/
                                // Inserting record
                                contentValues.put(UserDataclass.Status, "1");
                                contentValues.put(UserDataclass.ID, jsonObject.getString("id"));
                                contentValues.put(UserDataclass.NAME, jsonObject.getString("first_name"));
                                contentValues.put(UserDataclass.JOB, jsonObject.getString("last_name"));
                                long row = sqLiteDatabase.insert(UserDataclass.TABLE_NAME, null, contentValues);
                                 Log.v("rowid", "" + row);

                        }
                        UserDataDbHelper userDataDbHelper = new UserDataDbHelper(getActivity());
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
                        UserDataAdapter userDataAdapter = new UserDataAdapter(getActivity(), arrayList);
                        jsonView.setAdapter(userDataAdapter);
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
        return view;
    }
}
