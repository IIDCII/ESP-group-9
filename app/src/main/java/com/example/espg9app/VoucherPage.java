package com.example.espg9app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.espg9app.QRcode.QRPage;

public class VoucherPage extends AppCompatActivity {
    Button add_to_wallet;
    TextView description;
    ImageView qr_viewer;
    String voucherName;
    String voucherID;
    String businessName;
    TextView live;
    Boolean liveCheck;


    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.voucherpage);
        QRPage qr = new QRPage();
        voucherID = "Not available";
        voucherName = "N/A";
        add_to_wallet = findViewById(R.id.add_to_wallet);
        description = findViewById(R.id.description);
        qr_viewer = findViewById(R.id.qr_viewer);
        live = findViewById(R.id.live);

        Intent intent = this.getIntent();

        if (intent != null){
            voucherName = intent.getStringExtra("name");
            voucherID = intent.getStringExtra("voucherID");
            liveCheck = intent.getBooleanExtra("liveCheck", false);
            businessName = intent.getStringExtra("businessName");
        }

        description.setText(voucherName);

        if(liveCheck == true){
            live.setText("active");
        }
        else {
            live.setText("inactive");
        }

        qr.generateQR(qr_viewer,voucherID);

//      just add to DB, don't need to open the page
        add_to_wallet.setOnClickListener(v-> {
            System.out.println("Going to add this voucher id to the wallet");
//            change colour to confirm that it's been added
        });
    }
}
