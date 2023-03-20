package com.example.espg9app;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

    int businessID;

    Business selectedBusiness;

    public static ArrayList<Business> businessArraylist = new ArrayList<Business>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       binding = WalletlistBinding.inflate(getLayoutInflater());
       listview = findViewById(R.id.listview);
       setContentView(R.layout.walletlist);
       setContentView(binding.getRoot());

       setupData();


//     this will just get extracted from the the DB
       String [] businessName = {"Barry's","Bath town maidens", "cranberry stew","misogynist tailors", "komo ifi yabare"};
       String [] voucherName = {"50% off all products","100% off mega munch sale!","Navi discount","Bee Movie","Vin Diesel family discount"};
       String [] voucherID = {"50% off all products","100% off mega munch sale!","Navi discount","Bee Movie","Vin Diesel family discount"};
       Boolean [] liveCheck = {true,false,true,true,false};

       ArrayList<VoucherInfo> voucherInfoArrayList = new ArrayList<>();

       for (int i = 0; i< voucherID.length; i++){
           selectedBusiness = StudentMainFragment.businessArraylist.get(i);
           VoucherInfo voucherInfo = new VoucherInfo(selectedBusiness.getVoucherDescription(), selectedBusiness.isVoucherActive(),voucherID[i], businessName[i]);
           voucherInfoArrayList.add(voucherInfo);
       }

       WalletListAdapter walletListAdapter = new WalletListAdapter(WalletList.this,voucherInfoArrayList);
       
       binding.listview.setAdapter(walletListAdapter);
       binding.listview.setClickable(true);
       binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

               AlertDialog.Builder builder = new AlertDialog.Builder(WalletList.this);

               builder.setTitle(voucherName[position]);
               builder.setPositiveButton("View Discount", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int j) {
                       Intent i = new Intent(WalletList.this,VoucherPage.class);
                       i.putExtra("id",voucherName[position]);
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
                           }
                       });
                       builder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int j) {
                           }
                       });
                       builder2.show();
                   }
               });
               builder.show();
           }
       });
    }

    private void setupData(){
//        Business fishandchip = new Business("0", "fishandchip", R.drawable.fish, "foodanddrink", 5.0F);
//        businessArraylist.add(fishandchip);
//
//        Business hairdresser = new Business("1", "hairdresser", R.drawable.fish, "beauty",3.5F);
//        businessArraylist.add(hairdresser);

        TextView tv1 = findViewById(R.id.voucherEmptyText);
        DBAccess dba = new DBAccess();
        businessArraylist = dba.getAllBusinesses();
//        System.out.println(businessArraylist);
//        for (int i = 0; i < businessArraylist.size(); i++) businessArraylist.get(i).soutBusiness();
        if (!businessArraylist.isEmpty()) {
            tv1.setText("");
        } else {
            tv1.setText("Nothing to see here today, check again later");
        }
    }
}