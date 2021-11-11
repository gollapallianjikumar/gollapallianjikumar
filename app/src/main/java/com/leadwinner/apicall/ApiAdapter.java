package com.leadwinner.apicall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ApiAdapter extends BaseAdapter
{
    ArrayList<ApiEntity> apiEntities;
    LayoutInflater layoutInflater;
    public ApiAdapter(Context context,ArrayList<ApiEntity> apiEntities)
    {
        layoutInflater=LayoutInflater.from(context);
        this.apiEntities=apiEntities;
    }

    @Override
    public int getCount() {
        return apiEntities.size();
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
        ApiViewHolder apiViewHolder;
        if(convertView==null)
        {
            convertView=layoutInflater.inflate(R.layout.apidata,parent,false);
            apiViewHolder=new ApiViewHolder();
            apiViewHolder.idApi=convertView.findViewById(R.id.idApi);
            apiViewHolder.nameApi=convertView.findViewById(R.id.nameApi);
            apiViewHolder.yearApi=convertView.findViewById(R.id.yearApi);
            apiViewHolder.colorApi=convertView.findViewById(R.id.colorApi);
            apiViewHolder.patoneApi=convertView.findViewById(R.id.patoneApi);
            convertView.setTag(apiViewHolder);
        }
        else
        {
            apiViewHolder= (ApiViewHolder) convertView.getTag();
        }
        ApiEntity apiEntity=apiEntities.get(position);
        apiViewHolder.idApi.setText(apiEntity.getId());
        apiViewHolder.nameApi.setText(apiEntity.getName());
        apiViewHolder.yearApi.setText(apiEntity.getYear());
        apiViewHolder.colorApi.setText(apiEntity.getColor());
        apiViewHolder.patoneApi.setText(apiEntity.getPantone_value());
        return convertView;
    }
    class ApiViewHolder
    {
        TextView idApi;
        TextView nameApi;
        TextView yearApi;
        TextView colorApi;
        TextView patoneApi;
    }
}
