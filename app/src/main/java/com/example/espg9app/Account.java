package com.example.espg9app;
import android.app.Notification;
import android.text.Layout;
import android.view.View;
import android.widget.Switch;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.espg9app.ui.StudentMain.StudentMainFragment;

public class Account extends AppCompatActivity{
    public boolean allowNotifications;

    private Switch notificationSwitch;
    private TextView notificationTxt;
    private Button backButton;
    private Button supportButton;

    private Button walletButton;

    private Button logOutButton;

    private Button yesButton;
    private Button noButton;
    private TextView logOutText;
    private Button loyaltyButton;
    private TextView loyaltyText;
    private boolean loyaltyOnOff = false;
    private boolean logOutOnOff = false;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        allowNotifications = false;

        setContentView(R.layout.account);

        notificationTxt = (TextView) findViewById(R.id.notificationText);
        notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        notificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNotificationAllowed();
            }
        });


        backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });
        supportButton = (Button) findViewById(R.id.supportButton);
        supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSupport();
            }
        });

        walletButton = (Button) findViewById(R.id.walletButton);
        walletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goWallet();
            }
        });

        logOutButton = (Button) findViewById(R.id.profileButton);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogOut(!logOutOnOff);
            }
        });

        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);
        logOutText = (TextView) findViewById(R.id.logOutText);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLogin();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogOut(false);
            }
        });

        loyaltyText = (TextView) findViewById(R.id.loyaltyText);
        loyaltyButton = (Button) findViewById(R.id.loyaltyButton);
        loyaltyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoyalty(!loyaltyOnOff);
            }
        });

        showLogOut(false);
        showLoyalty(false);
    }

    public void showLoyalty(boolean show){
        notificationTxt.setVisibility(View.GONE);
        if(logOutOnOff) {
            showLogOut(false);
        }

        if(show){loyaltyText.setVisibility(View.VISIBLE); loyaltyOnOff=true; return;}
        loyaltyText.setVisibility(View.GONE);
        loyaltyOnOff=false;
    }

    public void setNotificationAllowed(){
        allowNotifications = notificationSwitch.isChecked();
        if(loyaltyOnOff) {showLoyalty(false);}
        if(logOutOnOff) {showLogOut(false);}

        String notificationText = "Notifications Disabled!";
        if(allowNotifications) {
            notificationText = "Notifications Allowed!";
        }

        notificationTxt.setVisibility(View.VISIBLE);
        notificationTxt.setText(notificationText);
    }

    public boolean getNotificationAllowed() {return this.allowNotifications;}

    public void goBack(){
        Intent intent = new Intent(this, StudentMainFragment.class);
        startActivity(intent);
    }

    public void goWallet(){
        /*Intent intent = new Intent(this, WalletList.class);
        startActivity(intent);

         */

    }

    public void goSupport(){
        Intent intent = new Intent(this, Support.class);
        startActivity(intent);
    }

    public void showLogOut(boolean show){
        if(loyaltyOnOff) {
            showLoyalty(false);
        }
        notificationTxt.setVisibility(View.GONE);

        if(show) {
            yesButton.setVisibility(View.VISIBLE);
            noButton.setVisibility(View.VISIBLE);
            logOutText.setVisibility(View.VISIBLE);
            logOutOnOff = true;
            return;
        }

        yesButton.setVisibility(View.GONE);
        noButton.setVisibility(View.GONE);
        logOutText.setVisibility(View.GONE);
        logOutOnOff = false;
    }

    public void goLogin() {
        /*Intent intent = new Intent(this, Login.class);
        startActivity(intent);*/
    }



}
