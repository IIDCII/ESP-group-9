package com.example.espg9app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// need to pull from my section before I can do this
public class WalletListAdapter extends ArrayAdapter<VoucherInfo> {
    public WalletListAdapter(Context context, ArrayList<VoucherInfo> voucherInfoArrayList){
        super(context,R.layout.walletlistitem,voucherInfoArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        VoucherInfo voucherInfo = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.walletlistitem,parent,false);
        }

        TextView voucherTitle = convertView.findViewById(R.id.voucher_title);
        TextView businessName = convertView.findViewById(R.id.business_name);

        voucherTitle.setText(voucherInfo.voucherName);
        businessName.setText(voucherInfo.businessName);

        return convertView;
    }
}
