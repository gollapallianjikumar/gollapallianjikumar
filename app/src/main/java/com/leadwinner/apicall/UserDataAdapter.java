package com.leadwinner.apicall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.leadwinner.apicall.DataBase.EditData;

import java.util.ArrayList;

public class UserDataAdapter extends BaseAdapter {
    ArrayList<UserEntity> userEntities;
    LayoutInflater layoutInflater;
    Context context;
    public UserDataAdapter(Context context, ArrayList<UserEntity> userEntities) {
        layoutInflater = LayoutInflater.from(context);
        this.userEntities = userEntities;
        this.context=context;
    }

    @Override
    public int getCount() {
        return userEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserHolder userHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listviewuserdata, parent, false);
            userHolder = new UserHolder();
            userHolder.name = convertView.findViewById(R.id.name);
            userHolder.job = convertView.findViewById(R.id.job);
            userHolder.editButton=convertView.findViewById(R.id.editButton);
            convertView.setTag(userHolder);
        } else {
            userHolder = (UserHolder) convertView.getTag();
        }
        UserEntity userEntity = userEntities.get(position);
        userHolder.name.setText(userEntity.getName());
        userHolder.job.setText(userEntity.getJob());
        userHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity1=userEntities.get(position);
                Intent  intent=new Intent(context, EditData.class);
                intent.putExtra("userUpdate",userEntity1);
                ((Activity)context).startActivity(intent);
            }
        });
        return convertView;
    }

    class UserHolder {
        TextView name;
        TextView job;
        Button editButton;
    }
}
