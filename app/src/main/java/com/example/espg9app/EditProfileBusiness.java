package com.example.espg9app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileBusiness extends AppCompatActivity {

    EditText businessName;
    EditText businessDescription;
    EditText totalWaste;
    EditText co2Emissions;
    Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.editprofilebusiness);
        Intent prevInt = getIntent();

        businessName = findViewById(R.id.business_name);
        businessDescription = findViewById(R.id.business_description);
        totalWaste = findViewById(R.id.total_waste);
        co2Emissions = findViewById(R.id.co2_emissions);
        saveChanges = findViewById(R.id.save_business_profile_edit);
        update();
    }

//  will access the database and make the changes needed
    private void update(){
        saveChanges.setOnClickListener(v->{
            update();

            AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileBusiness.this);

            builder.setTitle("saved");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        });
    }
}
