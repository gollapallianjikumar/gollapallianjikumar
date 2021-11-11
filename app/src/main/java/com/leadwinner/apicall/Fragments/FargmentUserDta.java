package com.leadwinner.apicall.Fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leadwinner.apicall.DataBase.UserDataDbHelperFragment;
import com.leadwinner.apicall.DataBase.UserDataFragment;
import com.leadwinner.apicall.R;

import org.json.JSONObject;

import java.util.Random;

public class FargmentUserDta extends Fragment
{
    EditText name;
    EditText jobs;
    Button userDataButton;
    JSONObject jsonObject;
    SQLiteDatabase sqLiteDatabase;
    UserDataDbHelperFragment userDataDbHelperFragment;
    String rowId;
    public static FargmentUserDta newInstance(int num)
    {
        FargmentUserDta  fargmentUserDta=new FargmentUserDta();
        Bundle bundle=new Bundle();
        bundle.putInt("num",num);
        fargmentUserDta.setArguments(bundle);
        return fargmentUserDta;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.activity_fragement_user_data,container,false);
       name=view.findViewById(R.id.nameFragment);
       jobs=view.findViewById(R.id.jobsFragment);
       userDataButton=view.findViewById(R.id.userDataButton);
        userDataDbHelperFragment=new UserDataDbHelperFragment(getActivity());
        userDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!name.getText().toString().equals("")) {
                    if (!jobs.getText().toString().equals("")) {
                        sqLiteDatabase = userDataDbHelperFragment.getWritableDatabase();
                        Random random = new Random();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(UserDataFragment.ID, random.nextInt());
                        contentValues.put(UserDataFragment.NAME, name.getText().toString());
                        contentValues.put(UserDataFragment.JOB, jobs.getText().toString());
                        contentValues.put(UserDataFragment.STATUS, "0");
                        long row_id = sqLiteDatabase.insert(UserDataFragment.TABLE_NAME, null, contentValues);
                        Log.d("row_id", "onClick: " + row_id);
                        addDataFragment();
                    }
                    else
                    {

                    }
                }
                else
                {
                    name.setError("Enter Name");
                }
            }
        });
        return view;
    }
    public void addDataFragment()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        UserDataDbHelperFragment userDataDbHelperFragment=new UserDataDbHelperFragment(getActivity());
        String query=" SELECT * FROM "+UserDataFragment.TABLE_NAME+" WHERE "+UserDataFragment.STATUS+" =0 ";
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        while(cursor.moveToNext())
        {
            rowId=cursor.getString(cursor.getColumnIndex(UserDataFragment.ID));
            try
            {
                jsonObject.put("name",cursor.getString(cursor.getColumnIndex(UserDataFragment.NAME)));
                jsonObject.put("job",cursor.getString(cursor.getColumnIndex(UserDataFragment.JOB)));
                Log.d("jsonData", "addData: "+jsonObject);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        cursor.close();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, "https://reqres.in/api/users", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                sqLiteDatabase=userDataDbHelperFragment.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                contentValues.put(UserDataFragment.STATUS,1);

                sqLiteDatabase.update(UserDataFragment.TABLE_NAME,contentValues,UserDataFragment.ID+" = ? ",new String[]{rowId});
                Log.d("Datapass", "onResponse: "+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
