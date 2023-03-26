package com.example.espg9app;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.ui.StudentMain.StudentMainFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        navbar();
    }

    private void navbar(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.studentMainFragment:
                        Intent i = new Intent(AboutUs.this, StudentMainFragment.class);
                        startActivity(i);
                        return true;
                    case R.id.aboutUs:
                        return true;
                    case R.id.walletList:
                        Intent j = new Intent(AboutUs.this, WalletList.class);
                        startActivity(j);
                        return true;
                }
                return false;
            }
        });
    }
}
