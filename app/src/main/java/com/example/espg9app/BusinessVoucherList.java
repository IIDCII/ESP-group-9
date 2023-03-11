package com.example.espg9app;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.databinding.BusinessvoucherlistBinding;

import java.util.ArrayList;


public class BusinessVoucherList extends AppCompatActivity {

    BusinessvoucherlistBinding binding;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       binding = BusinessvoucherlistBinding.inflate(getLayoutInflater());
       listview = findViewById(R.id.listview);
       setContentView(R.layout.businessvoucherlist);
       setContentView(binding.getRoot());


//     this will just get extracted from the the DB
       String [] name = {"50% off all products","100% off mega munch sale!","Navi discount","Bee Movie","Vin Diesel family discount"};
       String [] voucherID = {"50% off all products","100% off mega munch sale!","Navi discount","Bee Movie","Vin Diesel family discount"};
       Boolean [] liveCheck = {true,false,true,true,false};

       ArrayList<VoucherInfo> voucherInfoArrayList = new ArrayList<>();

       for (int i = 0; i< name.length; i++){
           VoucherInfo voucherInfo = new VoucherInfo(name[i], liveCheck[i],voucherID[i]);
           voucherInfoArrayList.add(voucherInfo);
       }

       BusinessVoucherListAdapter businessVoucherListAdapter = new BusinessVoucherListAdapter(BusinessVoucherList.this,voucherInfoArrayList);
       
       binding.listview.setAdapter(businessVoucherListAdapter);
       binding.listview.setClickable(true);
       binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               Intent i = new Intent(BusinessVoucherList.this,VoucherPage.class);
               i.putExtra("name",name[position]);
               i.putExtra("voucherID",voucherID[position]);
               i.putExtra("liveCheck",liveCheck[position]);
               startActivity(i);
           }
       });
    }
}