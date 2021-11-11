package com.leadwinner.apicall.FragmentsImages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.leadwinner.apicall.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter
{
    LayoutInflater layoutInflater;
    ArrayList<String> strings;
    Context context;

    public ImageAdapter(Context context,ArrayList<String> strings)
    {
       layoutInflater=LayoutInflater.from(context);
       this.strings=strings;
       this.context=context;
    }

    @Override
    public int getCount() {
        return strings.size();
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageHolder imageHolder;
        if (convertView==null)
        {
            convertView=layoutInflater.inflate(R.layout.imagesview,parent,false);
            imageHolder=new ImageHolder();
            imageHolder.imageViewex=convertView.findViewById(R.id.imageViewex);
            convertView.setTag(imageHolder);
//
        }
        else
        {
            imageHolder= (ImageHolder) convertView.getTag();
        }
        Glide.with(context).load(strings.get(position)).into((imageHolder.imageViewex));

        return convertView;
    }
    class ImageHolder
    {
        ImageView imageViewex;
    }
}
