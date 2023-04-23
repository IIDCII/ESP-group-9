package com.example.espg9app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.ui.BusinessView.BusinessViewAdapter;
import com.example.espg9app.ui.StudentMain.BusinessDetail;
import com.example.espg9app.ui.StudentMain.StudentMainAdapter;
import com.example.espg9app.ui.StudentMain.StudentMainFragment;

import java.util.ArrayList;

public class WelcomePage extends AppCompatActivity {
    public static ArrayList<Business> businessArraylist = new ArrayList<Business>();
    static Business selectedBusiness;
    private String selectedFilter = "all";

    String username;

    TextView welcome_user;
    ListView voucher_list;
    Button view_businesses;
    Button view_wallet;
    Button account;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);
        getUsername();
        selectBusiness(getFilter());

        welcome_user = findViewById(R.id.welcome_user);
        voucher_list = findViewById(R.id.voucher_list);
        view_businesses = findViewById(R.id.view_businesses);
        view_wallet = findViewById(R.id.view_wallet);
        account = findViewById(R.id.account);

        welcome_user.setText("Welcome " + username);
        setUpList();
        setUpButtons();
        setUpData();
        setUpOnclickListener();
    }



    private void setUpData(){
        businessArraylist.add(selectedBusiness);
    }

    private void getUsername(){
        StudentUser su = new StudentUser();
        username =  su.getUsername();
    }

    private void setUpButtons(){
        view_businesses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this, StudentMainFragment.class));
            }
        });

        view_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this, WalletList.class));
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this, Account.class));
            }
        });
    }

    private void setUpList() {
        voucher_list = (ListView) findViewById(R.id.voucher_list);

        StudentMainAdapter adapter = new StudentMainAdapter(getApplicationContext(), 0, businessArraylist);
        voucher_list.setAdapter(adapter);
    }

    private void selectBusiness(String filter){
        selectedFilter = filter;

        for(Business business: businessArraylist)
        {
            if(business.getTags().toLowerCase().contains(filter))
            {
                selectedBusiness = business;
            }
        }
    }

    private String getFilter(){
        StudentUser su = new StudentUser();
        username = su.getUsername();

        DBAccess db = new DBAccess();

        ArrayList<Business> fullBusinessArraylist = new ArrayList<Business>();
        fullBusinessArraylist = db.getAllBusinesses();
        for (int i = 0; i < fullBusinessArraylist.size(); i++){
            selectedBusiness = fullBusinessArraylist.get(i);
            if (db.isVoucherInstance(selectedBusiness.getId(),username)){
                return selectedBusiness.getTags().toLowerCase();
            }
        }
        return "all";
    }

    private void setUpOnclickListener() {
        voucher_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent showDetail = new Intent(getApplicationContext(), BusinessDetail.class);
                System.out.println(Integer.toString(selectedBusiness.getId()));
                showDetail.putExtra("id", (Integer.toString(selectedBusiness.getId())));
                startActivity(showDetail);
            }
        });
    }
}
