package com.example.espg9app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.QRcode.QRPage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class VoucherPage extends AppCompatActivity {
    Button add_to_wallet;
    TextView description;
    ImageView qr_viewer;
    String text;
    TextView live;
    Boolean liveCheck = false;


    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.voucherpage);

        QRPage qr = new QRPage();
        text = "In the jungle the mighty jungle";

        add_to_wallet = findViewById(R.id.add_to_wallet);
        description = findViewById(R.id.description);
        qr_viewer = findViewById(R.id.qr_viewer);
        live = findViewById(R.id.live);


        description.setText("Butchers\n10% off all products\nActive");

        if(liveCheck == true){
            live.setText("active");
        }
        else {
            live.setText("inactive");
        }

        qr.generateQR(qr_viewer,text);

//      just add to DB, don't need to open the page
        add_to_wallet.setOnClickListener(v-> {
            System.out.println("Going to add this voucher id to the wallet");
//            change colour to confirm that it's been added
        });
    }
}
