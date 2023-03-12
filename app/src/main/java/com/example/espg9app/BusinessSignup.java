package com.example.espg9app;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class BusinessSignup extends AppCompatActivity {

    EditText et_username, et_password;
    Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businessregister);
        Register();
    }

    void Register() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ATTEMPTING");
                DBAccess DB = new DBAccess();
                if(DB.encrypt(et_username.getText().toString(),et_password.getText().toString(),false)){
                    Toast.makeText(BusinessSignup.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                    System.out.println("SUCCESS");
                    Intent intent = new Intent(BusinessSignup.this, BusinessLogin.class);
                    startActivity(intent);
                }else{
                    System.out.println("FAIL");
                    Toast.makeText(BusinessSignup.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}