package com.example.espg9app.ui.StudentMain;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.AboutUs;
import com.example.espg9app.Account;
import com.example.espg9app.Business;
import com.example.espg9app.DBAccess;
import com.example.espg9app.R;
import com.example.espg9app.StudentUser;
import com.example.espg9app.WalletList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class StudentMainFragment extends AppCompatActivity {

    public static ArrayList<Business> businessArraylist = new ArrayList<Business>();
    private ListView listView;

    private String selectedFilter = "all";
    private String currentSearchText = "";
    private SearchView searchView;
    public static String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentmainfragment);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StudentUser su = new StudentUser();
        username = su.getUsername();
        initSearchWidgets();
        setupData();
        setUpList();
        navbar();
        setUpOnclickListener();
    }



    private void initSearchWidgets() {
        searchView = (SearchView) findViewById(R.id.StudentMainSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                listView.startAnimation(aniFade);
                currentSearchText = s;
                ArrayList<Business> filteredBusinesses = new ArrayList<Business>();

                for (Business business : businessArraylist) {
                    if (business.getName().toLowerCase().contains(s.toLowerCase())) {
                        if (selectedFilter.equals("all")) {
                            filteredBusinesses.add(business);
                        } else {
                            if (business.getTags().toLowerCase().contains(selectedFilter) && business.getName().toLowerCase().contains(s.toLowerCase())) {
                                filteredBusinesses.add(business);
                            }
                        }
                    }
                }
                StudentMainAdapter adapter = new StudentMainAdapter(getApplicationContext(), 0, filteredBusinesses);
                listView.setAdapter(adapter);
                TextView tv1 = findViewById(R.id.voucherEmptyText);
                if (!filteredBusinesses.isEmpty()){
                    tv1.setText("");
                }
                else{
                    tv1.setText("Nothing to see here today, check again later");
                }
                return false;
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

    private void setUpList() {
        listView = (ListView) findViewById(R.id.voucherList);

        StudentMainAdapter adapter = new StudentMainAdapter(getApplicationContext(), 0, businessArraylist);
        listView.setAdapter(adapter);
    }

    private void setUpOnclickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Business selectBusiness = (Business) (listView.getItemAtPosition(position));
                Intent showDetail = new Intent(getApplicationContext(), BusinessDetail.class);
                showDetail.putExtra("id", (Integer.toString(selectBusiness.getId())));
//                System.out.println(selectBusiness.getId());
                startActivity(showDetail);
            }
        });
    }
    private void filterList(String filter)
    {
        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        listView.startAnimation(aniFade);
        selectedFilter = filter;

        ArrayList<Business> filteredBusinesses = new ArrayList<Business>();

        for(Business business: businessArraylist)
        {
            if(business.getTags().toLowerCase().contains(filter))
            {
                if(currentSearchText == "")
                {
                    filteredBusinesses.add(business);
                }
                else
                {
                    if(business.getName().toLowerCase().contains(currentSearchText.toLowerCase()))
                    {
                        filteredBusinesses.add(business);
                    }
                }
            }
        }
        StudentMainAdapter adapter = new StudentMainAdapter(getApplicationContext(), 0, filteredBusinesses);
        listView.setAdapter(adapter);
        TextView tv1 = findViewById(R.id.voucherEmptyText);
        if (!filteredBusinesses.isEmpty()){
            tv1.setText("");
        }
        else{
            tv1.setText("Nothing to see here today, check again later");
        }

    }
    public void allFilterTapped(View view)
    {
        selectedFilter = "all";
        StudentMainAdapter adapter = new StudentMainAdapter(getApplicationContext(), 0, businessArraylist);
        listView.setAdapter(adapter);
        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        listView.startAnimation(aniFade);

    }

    public void FoodandDrinkFilterTapped(View view)
    {
        Animation aniFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        listView.startAnimation(aniFadeOut);
        filterList("foodanddrink");

    }

    public void RetailFilterTapped(View view)
    {
        Animation aniFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        listView.startAnimation(aniFadeOut);
        filterList("retail");
    }

    public void BeautyFilterTapped(View view)
    {
        Animation aniFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        listView.startAnimation(aniFadeOut);
        filterList("beauty");
    }

    private void navbar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.aboutUs:
                        Intent i = new Intent(StudentMainFragment.this, AboutUs.class);
                        startActivity(i);
                        return true;
                    case R.id.studentMainFragment:
                        return true;
                    case R.id.account:
                        Intent j = new Intent(StudentMainFragment.this, Account.class);
                        startActivity(j);
                        return true;
                }
                return false;
            }
        });
    }


//    public void rectangleFilterTapped(View view)
//    {
//        filterList("rectangle");
//    }
//
//    public void circleFilterTapped(View view)
//    {
//        filterList("circle");
//    }
}
