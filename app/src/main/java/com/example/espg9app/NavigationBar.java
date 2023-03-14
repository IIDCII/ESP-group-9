package com.example.espg9app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.ui.StudentMain.StudentMainFragment;

public class NavigationBar extends ContextWrapper {

    public NavigationBar(Context base) {
        super(base);
    }

    public void setOnClickListener(TextView home,TextView account,TextView aboutUs){

        TextView [] menu = {home,account,aboutUs};
        Class [] classes = {WalletList.class,WalletList.class,WalletList.class};

        for (int i = 0; i<menu.length;i++){

            Intent intent = new Intent(this,classes[i]);
            menu[i].setClickable(true);
            menu[i].setOnClickListener(v-> startActivity(intent));
        }
    }

}
