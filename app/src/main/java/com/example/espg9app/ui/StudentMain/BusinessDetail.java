package com.example.espg9app.ui.StudentMain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.espg9app.Business;
import com.example.espg9app.R;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

public class BusinessDetail extends AppCompatActivity
{
    RatingBar rb;
    Business selectedBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_detail);
        getSelectedBusiness();
        setValues();

    }

    private void getSelectedBusiness()
    {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");
        selectedBusiness = StudentMainFragment.businessArraylist.get(Integer.valueOf(parsedStringID));
    }

    private void setValues()
    {
        rb=(RatingBar)findViewById(R.id.ratingBar1);
        TextView tv = (TextView) findViewById(R.id.businessName);
        ImageView iv = (ImageView) findViewById(R.id.businessImage);

        rb.setRating(selectedBusiness.getSusRating());
        tv.setText(selectedBusiness.getName());
//        iv.setImageResource(selectedBusiness.getIconPath());
    }
}