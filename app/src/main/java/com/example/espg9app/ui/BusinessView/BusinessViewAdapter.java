package com.example.espg9app.ui.BusinessView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.espg9app.Business;
import com.example.espg9app.R;

import java.util.ArrayList;

public class BusinessViewAdapter extends ArrayAdapter<Business>
{

    public BusinessViewAdapter(Context context, int resource, ArrayList<Business> BusinessList)
    {
        super(context,resource,BusinessList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Business business = getItem(position);

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.businessdetailvoucher, parent, false);
        }

        TextView voucherName = (TextView) convertView.findViewById(R.id.voucherName);
        TextView voucherDetail = (TextView) convertView.findViewById(R.id.voucherDetail);

        voucherName.setText(business.getName());
        voucherDetail.setText(business.getName());

        return convertView;
    }
}