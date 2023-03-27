package com.example.espg9app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.espg9app.QRcode.QRPage;
import com.example.espg9app.ui.StudentMain.StudentMainFragment;

import java.util.ArrayList;

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

    String username;

    private static ArrayList<Business> businessArraylist = new ArrayList<Business>();
    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.voucherpage);
        QRPage qr = new QRPage();
        description = findViewById(R.id.description);
        qr_viewer = findViewById(R.id.qr_viewer);
        live = findViewById(R.id.live);
        StudentUser su = new StudentUser();
        username = su.getUsername();

        getSelectedBusiness();
        setValues();

        String contentQR = businessID +","+ username;
        qr.generateQR(qr_viewer,contentQR);
    }

    private void getSelectedBusiness() {
        DBAccess db = new DBAccess();
        businessArraylist = db.getAllBusinesses();
        Intent previousIntent = getIntent();
        if (previousIntent != null) {
            String parsedStringID = previousIntent.getStringExtra("business_id");
            String instanceID = previousIntent.getStringExtra("instance_id");
            for (int i = 0; i < businessArraylist.size(); i++){
                if(Integer.valueOf(parsedStringID) == Integer.valueOf(businessArraylist.get(i).getId())){
                    selectedBusiness = businessArraylist.get(i);
                };
            }
            discount = db.getDiscountPercent(Integer.parseInt(instanceID));
        }
    }

    private void setValues(){
        String briefDesc = selectedBusiness.getVoucherDescription();
        int totalDiscount = discount;
        String fullDesc = totalDiscount + "% off\n" + briefDesc;
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
