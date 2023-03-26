package com.example.espg9app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Support extends AppCompatActivity {
    private Button backButton;
    private Button qrButton;
    private Button loyaltyButton;
    private Button contactButton;
    private TextView qrText;
    private TextView emailText;
    private TextView phoneText;

    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.support);

        qrText = (TextView) findViewById(R.id.qrCodeText);
        emailText = (TextView) findViewById(R.id.textEmail);
        phoneText = (TextView) findViewById(R.id.textPhone);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        qrButton = (Button) findViewById(R.id.qrButton);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showQR(true);
                showLoyalty(false);
            }
        });

        contactButton = (Button) findViewById(R.id.contactButton);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoyalty(true);
                showQR(false);
            }
        });
        showQR(false);
        showLoyalty(false);
    }

    public void goBack(){
        Intent intent = new Intent(this, Account.class);
        startActivity(intent);
    }

    public void showQR(boolean show){
        if(show) {
            qrText.setVisibility(View.VISIBLE);
        }else {
            qrText.setVisibility(View.GONE);
        }
    }

    public void showLoyalty(boolean show){
        if(show){
            emailText.setVisibility(View.VISIBLE);
            phoneText.setVisibility(View.VISIBLE);
        }else {
            emailText.setVisibility(View.GONE);
            phoneText.setVisibility(View.GONE);
        }
    }
}
