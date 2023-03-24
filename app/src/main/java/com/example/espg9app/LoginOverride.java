package com.example.espg9app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.ui.StudentMain.StudentMainFragment;

// just used for the sake of testing the code without interruptions
public class LoginOverride extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        //adding new instance of a business like so
        addNewBusiness();

        //starting the student main fragment
        Intent showDetail = new Intent(getApplicationContext(), StudentMainFragment.class);
        startActivity(showDetail);
    }

    private void addNewBusiness(){
        DBAccess db = new DBAccess();
        Coordinates coordinates = new Coordinates((float)51.372323, (float)-2.387209);
        db.addBusiness(
                "BensButchers@gmail.com",
                "Ben's Butchers",
                "samplebusinessimage.png",
                "FoodAndDrink",
                "we are Ben's butchers that have the finest cuts of meat in Bath. Please make sure to look at our vast",
                3.5,
                coordinates,
                true,
                "60,70,80",
                "Discount on all of the oxtail we sell"
        );
    }
}
