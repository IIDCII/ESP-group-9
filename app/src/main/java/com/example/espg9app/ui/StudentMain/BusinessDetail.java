package com.example.espg9app.ui.StudentMain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.espg9app.Business;
import com.example.espg9app.DBAccess;
import com.example.espg9app.R;
import com.example.espg9app.Voucher;
import com.example.espg9app.ui.BusinessView.BusinessViewAdapter;

import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.List;

public class BusinessDetail extends AppCompatActivity
{
    RatingBar rb;
    Business selectedBusiness;
    ArrayList<Voucher> voucherArrayList = new ArrayList<Voucher>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businessdetail);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        getSelectedBusiness();
        setBusinessDetails();

        getVoucherList();
        setVoucherList();

    }

    private void getVoucherList(){
        Voucher burger_deal = new Voucher("epic burger deal", "50% off burger");
        voucherArrayList.add(burger_deal);

        Voucher hair_deal = new Voucher("hair time", "20% off haircut on tuesday for bob only");
        voucherArrayList.add(hair_deal);

        TextView emptyText = findViewById(R.id.voucherEmptyText);
//        DBAccess dba = new DBAccess();
//        dba.openConnection();
//        voucherArrayList = dba.get();
        System.out.println(voucherArrayList);
//        dba.closeConnection();
        if (!voucherArrayList.isEmpty()) {
            emptyText.setText("");
        } else {
            emptyText.setText("Nothing to see here today, check again later");
        }
    }

    private void setVoucherList() {
        ListView listView = (ListView) findViewById(R.id.voucherList);

        BusinessViewAdapter adapter = new BusinessViewAdapter(getApplicationContext(), 0, businessList);
        listView.setAdapter(adapter);
    }

    private void getSelectedBusiness()
    {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedBusiness = StudentMainFragment.businessArraylist.get(Integer.valueOf(parsedStringID));
    }

    private void setBusinessDetails()
    {
        rb = (RatingBar)findViewById(R.id.ratingBar);
        TextView businessName = (TextView) findViewById(R.id.businessName);
        TextView businessDesc = (TextView) findViewById(R.id.businessDesc);
        ImageView iv = (ImageView) findViewById(R.id.businessImage);

        rb.setRating(selectedBusiness.getSusRating());
        businessName.setText(selectedBusiness.getName());
        businessDesc.setText(selectedBusiness.getDescription());
//        iv.setImageResource(selectedBusiness.getIconPath());
    }
}