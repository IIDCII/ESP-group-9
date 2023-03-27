package com.example.espg9app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.QRcode.QRReader;

public class BusinessMain extends AppCompatActivity {

    TextView account;
    Button addBusiness;
    Button editBusiness;
    Button scanDiscount;
    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.businessmain);

        addBusiness = findViewById(R.id.add_business);
        editBusiness = findViewById(R.id.edit_business);
        scanDiscount = findViewById(R.id.scan_discount);
        logOut = findViewById(R.id.log_out);

        logOut.setOnClickListener(v->{
            Intent i = new Intent(BusinessMain.this,Login.class);
            startActivity(i);
                });
        editBusiness.setOnClickListener(v->{
            Intent i = new Intent(BusinessMain.this,EditProfileBusiness.class);
            startActivity(i);
        });
        addBusiness.setOnClickListener(v->{
            Intent i = new Intent(BusinessMain.this,EditProfileBusiness.class);
            startActivity(i);
        });
        scanDiscount.setOnClickListener(v->{
            Intent i = new Intent(BusinessMain.this, QRReader.class);
            startActivity(i);
        });
    }

}
