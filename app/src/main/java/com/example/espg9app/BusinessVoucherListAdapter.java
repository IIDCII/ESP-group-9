package com.example.espg9app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// need to pull from my section before I can do this
public class BusinessVoucherListAdapter extends ArrayAdapter<VoucherPage> {
    public BusinessVoucherListAdapter(Context context, ArrayList<VoucherPage> voucherPageArrayList){
        super(context,R.layout.voucherlistitem,voucherPageArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        VoucherPage voucherPage = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.voucherlistitem,parent,false);

        }
//        at 9:28 in the video

        return super.getView(position, convertView, parent);
    }
}
