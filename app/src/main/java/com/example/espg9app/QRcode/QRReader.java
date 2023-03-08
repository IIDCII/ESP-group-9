package com.example.espg9app.QRcode;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.R;
import com.example.espg9app.VoucherPage;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRReader extends AppCompatActivity {

    Button btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrreader);

        btn_scan = findViewById(R.id.btn_scan);
        btn_scan.setOnClickListener(v->{
            scanCode();
        });

    }

    private void scanCode(){
        IntentIntegrator options = new IntentIntegrator(QRReader.this);
        options.setPrompt("Turn volume up to turn the flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        options.initiateScan();
    }
// check this part of the code again
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

//      when implementing, you need to change the code inside here for it to open up the voucher
        if (intentResult.getContents() != null) {
//          decrypt the qr code to get the voucher id and show the contents of it and that's all
            AlertDialog.Builder builder = new AlertDialog.Builder(QRReader.this);

            builder.setTitle("voucher");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        } else {
            Toast.makeText(this, "this qr code is not available", Toast.LENGTH_SHORT).show();
        }

    }


}