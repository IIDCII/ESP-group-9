package com.example.espg9app.ui.StudentMain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.espg9app.R;

import java.util.List;

public class StudentMainAdapter extends ArrayAdapter<Business>
{

    public StudentMainAdapter(Context context, int resource, List<Business> BusinessList)
    {
        super(context,resource,BusinessList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Business business = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.studentmainitem, parent, false);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.text_main);
        ImageView iv = (ImageView) convertView.findViewById(R.id.image_main);

        tv.setText(business.getName());
        iv.setImageResource(business.getImage());


        return convertView;
    }
}