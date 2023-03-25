package com.example.espg9app;
import static com.example.espg9app.ui.StudentMain.StudentMainFragment.username;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.databinding.WalletlistBinding;
import com.example.espg9app.ui.StudentMain.StudentMainAdapter;
import com.example.espg9app.ui.StudentMain.StudentMainFragment;

import java.util.ArrayList;


public class WalletList extends AppCompatActivity {

    WalletlistBinding binding;
    ListView listview;

    Business selectedBusiness;

    String username;

    private static ArrayList<Business> businessArraylist = new ArrayList<Business>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = WalletlistBinding.inflate(getLayoutInflater());
        listview = findViewById(R.id.listview);
        setContentView(R.layout.walletlist);
        setContentView(binding.getRoot());

        //so you don't get a connection error in the first place
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ArrayList<VoucherInfo> voucherInfoArrayList = new ArrayList<>();
        setupData(voucherInfoArrayList);
        WalletListAdapter walletListAdapter = new WalletListAdapter(WalletList.this,voucherInfoArrayList);

       binding.listview.setAdapter(walletListAdapter);
       binding.listview.setClickable(true);
       binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

               AlertDialog.Builder builder = new AlertDialog.Builder(WalletList.this);
               Business business = businessArraylist.get(position);
               builder.setTitle(business.getVoucherDescription());

               DBAccess db = new DBAccess();
               String instanceID = Integer.toString(db.getVoucherInstanceID(username, business.getId()));
               builder.setPositiveButton("View Discount", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int j) {
                       Intent i = new Intent(WalletList.this,VoucherPage.class);
                       i.putExtra("business_id",Integer.toString(business.getId()));
                       i.putExtra("instance_id","5");
                       System.out.println("-----------/////------------/////-------/////-----/-/-/-/-/----/");
                       System.out.println(business.getId() +"  " + instanceID);
                       startActivity(i);
                   }
               });
               builder.setNegativeButton("Delete Discount", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int j) {
                       AlertDialog.Builder builder2 = new AlertDialog.Builder(WalletList.this);
                       builder2.setTitle("Are you sure?\nAll loyalty progression will be lost");
                       builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int j) {
                               //fill in
                           }
                       });
                       builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int j) {
                               //fill in
                           }
                       });
                       builder2.show();
                   }
               });
               builder.show();
           }
       });
    }

    private void setupData(ArrayList<VoucherInfo> voucherInfoArrayList){
        DBAccess db = new DBAccess();
        username = db.getUsername("sz2075@bath.ac.uk");
        ArrayList<Business> fullBusinessArraylist = new ArrayList<Business>();
        fullBusinessArraylist = db.getAllBusinesses();
        for (int i = 0; i < fullBusinessArraylist.size(); i++){
            selectedBusiness = fullBusinessArraylist.get(i);
            if (db.isVoucherInstance(selectedBusiness.getId(),username)){
                VoucherInfo voucherInfo = new VoucherInfo(
                        selectedBusiness.getVoucherDescription(),
                        selectedBusiness.isVoucherActive(),
                        selectedBusiness.getName());
                voucherInfoArrayList.add(voucherInfo);
                businessArraylist.add(selectedBusiness);
            }
        }
        System.out.println("------------------------------------------------------------");
        System.out.println(username);
        System.out.println(businessArraylist);
        System.out.println(voucherInfoArrayList);
    }
}