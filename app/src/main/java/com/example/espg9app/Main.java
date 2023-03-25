package com.example.espg9app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        NavController navController = Navigation.findNavController(this,  R.id.fragment);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
