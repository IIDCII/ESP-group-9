package com.example.espg9app.QRcode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.DBAccess;
import com.example.espg9app.R;
import com.example.espg9app.VoucherPage;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QRReader extends AppCompatActivity {

    Button btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrreader);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        scanCode();

    }

    private void scanCode(){
        IntentIntegrator options = new IntentIntegrator(QRReader.this);
        options.setPrompt("Turn volume up to turn the flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        options.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (intentResult.getContents() != null) {
            DBAccess db = new DBAccess();
            List<String> businessIDAndUsername = new ArrayList<String>(Arrays.asList(intentResult.getContents().split(",")));
            String username = businessIDAndUsername.get(1);
            String businessID = businessIDAndUsername.get(0);

            //currently doesn't check what business is using it since business profiles still need to be made
            if (db.isVoucherInstance(Integer.parseInt(businessID),username)){
                Intent openVoucher = new Intent(QRReader.this,VoucherPage.class);
                openVoucher.putExtra("business_id",businessID);
                openVoucher.putExtra("instance_id",db.getVoucherInstanceID(username,Integer.parseInt(businessID)));
                db.redeemVoucher(db.getVoucherInstanceID(username, Integer.parseInt(businessID)));
                startActivity(openVoucher);
            }
        } else {
            Toast.makeText(this, "this qr code is not available", Toast.LENGTH_SHORT).show();
        }

    }


}
