package com.example.espg9app.ui.BusinessView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.espg9app.DBAccess;
import com.example.espg9app.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class studentreviewpage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.studentreviewpage);
//        setUpRB();
        System.out.println("TEST");
//        setUpOnClick();


    }


    private void setUpOnClick(){
        Button submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBAccess db = new DBAccess();
                RatingBar bar = (RatingBar) view;
                System.out.println("CLICK");
                submit_button.setText("value is " + bar.getRating());
            }
        });
    }

}
