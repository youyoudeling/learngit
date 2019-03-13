package com.example.a13162.activitytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TagAdapter extends ArrayAdapter {
    private int resourceId;

    public TagAdapter(Context context, int textViewResourceId, List<TagClass>objects) {

        super(context, textViewResourceId,objects);
        resourceId=textViewResourceId;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        this.notifyDataSetChanged();
        TagClass tag =(TagClass) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView title=(TextView) view.findViewById(R.id.title);
        TextView text=(TextView) view.findViewById(R.id.text);
        title.setText(tag.getTitle());
        text.setText(tag.getText());
        return view;
    }
}
