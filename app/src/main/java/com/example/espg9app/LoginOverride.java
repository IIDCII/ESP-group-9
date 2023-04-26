package com.example.espg9app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.ui.StudentMain.StudentMainFragment;


// just used for the sake of testing the code without interruptions
public class LoginOverride extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        startUp();
    }

    private void startUp(){
        DBAccess db = new DBAccess();
        StudentUser su = new StudentUser();
        su.setUsername(db.getUsername("sz2075@bath.ac.uk"));
        Intent showDetail = new Intent(getApplicationContext(), WelcomePage.class);
        startActivity(showDetail);
    }

    private void addNewBusiness(){
        DBAccess db = new DBAccess();
        Coordinates coordinates = new Coordinates((float)52.372323, (float)-3.387209);
        db.addBusiness(
                "kumran225@gmail.com",
                "Kumran",
                "@drawable/samplebusinessimage.png",
                "FoodAndDrink",
                "Man just work ffs",
                1.5,
                coordinates,
                true,
                "60,70,80",
                "Kumran do nice offer"
        );
    }
}
