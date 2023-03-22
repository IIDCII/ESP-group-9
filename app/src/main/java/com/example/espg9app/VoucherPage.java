package com.example.espg9app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.espg9app.QRcode.QRPage;
import com.example.espg9app.ui.StudentMain.StudentMainFragment;

public class VoucherPage extends AppCompatActivity {
    Button add_to_wallet;
    TextView description;
    ImageView qr_viewer;
    String voucherName;
    int businessID;

    int discount;

    String businessName;
    TextView live;
    Boolean liveCheck;
    Business selectedBusiness;


    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.voucherpage);
        QRPage qr = new QRPage();
        add_to_wallet = findViewById(R.id.add_to_wallet);
        description = findViewById(R.id.description);
        qr_viewer = findViewById(R.id.qr_viewer);
        live = findViewById(R.id.live);

        getSelectedBusiness();
        setValues();

        qr.generateQR(qr_viewer,businessID);

//      just add to DB, don't need to open the page
        add_to_wallet.setOnClickListener(v-> {
            System.out.println("Going to add this voucher id to the wallet");
//            change colour to confirm that it's been added
        });
    }

    private void getSelectedBusiness() {
        DBAccess db = new DBAccess();
        Intent previousIntent = getIntent();
        if (previousIntent != null) {
            String parsedStringID = previousIntent.getStringExtra("business_id");
            String instanceID = previousIntent.getStringExtra("business_id");

            for (int i = 0; i < StudentMainFragment.businessArraylist.size(); i++){
                if(Integer.valueOf(parsedStringID) == Integer.valueOf(StudentMainFragment.businessArraylist.get(i).getId())){
                    selectedBusiness = StudentMainFragment.businessArraylist.get(i);
                };
            }
            discount = db.redeemVoucher(Integer.parseInt(instanceID));
        }
    }

    private void setValues(){
        String briefDesc = selectedBusiness.getVoucherDescription();
        String discountNo = Integer.toString(discount);
        String fullDesc = discountNo + "% off " + briefDesc;
        description.setText(fullDesc);
        businessID = selectedBusiness.getId();

        if(selectedBusiness.isVoucherActive()){
            live.setText("active");
        }
        else {
            live.setText("inactive");
        }
    }

}
