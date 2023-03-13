package com.example.espg9app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.ui.StudentMain.StudentMainFragment;

public class NavigationBar extends AppCompatActivity {
    TextView home;
    TextView account;
    TextView aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.navigationbar);
        home = findViewById(R.id.home);
        account = findViewById(R.id.account);
        aboutUs = findViewById(R.id.about_us);
    }

    public void setOnClickListener(Context packageContext){
        TextView home = findViewById(R.id.home);
        TextView account = findViewById(R.id.account);
        TextView aboutUs = findViewById(R.id.about_us);

        TextView [] menu = {home,account,aboutUs};

        for (int i = 0; i<menu.length;i++){
            Intent intent = new Intent(packageContext,WalletList.class);
            menu[i].setClickable(true);
            menu[i].setOnClickListener(v-> startActivity(intent));
        }
    }

}
