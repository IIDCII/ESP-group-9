package com.example.espg9app.ui.StudentMain;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.espg9app.R;

import java.util.ArrayList;

public class StudentMainFragment extends AppCompatActivity{

    public static ArrayList<Business> businessArraylist = new ArrayList<Business>();
    private ListView listView;

    private String selectedFilter = "all";
    private String currentSearchText = "";
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentmainfragment);

        initSearchWidgets();
        setupData();
        setUpList();
        //setUpOnclickListener();
    }
    private void initSearchWidgets()
    {
        searchView = (SearchView) findViewById(R.id.StudentMainSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                currentSearchText = s;
                ArrayList<Business> filteredBusinesses= new ArrayList<Business>();

                for(Business business: businessArraylist)
                {
                    if(business.getName().toLowerCase().contains(s.toLowerCase()))
                    {
                        if(selectedFilter.equals("all"))
                        {
                            filteredBusinesses.add(business);
                        }
                        else
                        {
                            if(business.getName().toLowerCase().contains(selectedFilter))
                            {
                                filteredBusinesses.add(business);
                            }
                        }
                    }
                }
                StudentMainAdapter adapter = new StudentMainAdapter(getApplicationContext(), 0, filteredBusinesses);
                listView.setAdapter(adapter);
                return false;
            }
        });

    }
    private void setupData() {
        Business fishandchip = new Business("0", "fishandchip", R.drawable.fish);
        businessArraylist.add(fishandchip);

        Business hairdresser = new Business("1", "hairdresser", R.drawable.fish);
        businessArraylist.add(hairdresser);
    }
    private void setUpList()
    {
        listView = (ListView) findViewById(R.id.BusinessListView);

        StudentMainAdapter adapter = new StudentMainAdapter(getApplicationContext(), 0, businessArraylist);
        listView.setAdapter(adapter);
    }

}
