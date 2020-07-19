package com.example.getum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<RecordData> rd;

    public MyAdapter(Context context, ArrayList<RecordData> data){
        mContext = context;
        rd = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return rd.size();
    }

    @Override
    public Object getItem(int i) {
        return rd.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View converview, ViewGroup viewGroup) {
        View view = mLayoutInflater.inflate(R.layout.simple_record_view, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.rent_return);
        TextView location = (TextView)view.findViewById(R.id.tv_location);
        TextView timestamp = (TextView)view.findViewById(R.id.tv_timestamp);

        if (rd.get(i).getRentflag() == 0) {
            imageView.setImageResource(R.drawable.btn_rent);
        }
        else{
            imageView.setImageResource(R.drawable.btn_return);
        }

        location.setText(rd.get(i).getLocation());
        timestamp.setText(rd.get(i).getTimestamp());

        return view;
    }
}
