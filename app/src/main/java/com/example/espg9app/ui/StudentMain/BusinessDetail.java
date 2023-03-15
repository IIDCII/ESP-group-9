package com.example.espg9app.ui.StudentMain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.espg9app.Business;
import com.example.espg9app.DBAccess;
import com.example.espg9app.R;
import com.example.espg9app.Voucher;
import com.example.espg9app.ui.BusinessView.BusinessViewAdapter;
import com.example.espg9app.ui.BusinessView.studentreviewpage;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;

import android.widget.RatingBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class BusinessDetail extends AppCompatActivity
{
    RatingBar susrb;
    Business selectedBusiness;
    ArrayList<Voucher> voucherArrayList = new ArrayList<Voucher>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businessdetail);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        getSelectedBusiness();
        setBusinessDetails();
        setOverlay();
        getVoucherList();
        setRatingBar();
        setVoucherList();

    }

    private void getVoucherList(){
//        Voucher burger_deal = new Voucher("epic burger deal", "50% off burger");
//        voucherArrayList.add(burger_deal);
//
//        Voucher hair_deal = new Voucher("hair time", "20% off haircut on tuesday for bob only");
//        voucherArrayList.add(hair_deal);

        TextView emptyText = findViewById(R.id.voucherEmptyText);
//        DBAccess dba = new DBAccess();
//        dba.openConnection();
//        voucherArrayList =
//        System.out.println(voucherArrayList);
//        dba.closeConnection();
        if (selectedBusiness.isVoucherActive()) {
            emptyText.setText("");
        } else {
            emptyText.setText("Nothing to see here today, check again later");
        }
    }
    /**
    *Sets random values for the rating bar
     **/
    private void setRatingBar(){
        RatingReviews ratingReviews = (RatingReviews) findViewById(R.id.ratingBar);

        int colors[] = new int[]{
                Color.parseColor("#0e9d58"),
                Color.parseColor("#bfd047"),
                Color.parseColor("#ffc105"),
                Color.parseColor("#ef7e14"),
                Color.parseColor("#d36259")};

        int raters[] = new int[]{
                new Random().nextInt(100),
                new Random().nextInt(100),
                new Random().nextInt(100),
                new Random().nextInt(100),
                new Random().nextInt(100)
        };

        ratingReviews.createRatingBars(100, BarLabels.STYPE1, colors, raters);
    }

    private void setVoucherList() {
        ListView listView = (ListView) findViewById(R.id.voucherList);

        BusinessViewAdapter adapter = new BusinessViewAdapter(getApplicationContext(), 0, selectedBusiness);
        listView.setAdapter(adapter);
    }

    private void getSelectedBusiness()
    {
        Intent previousIntent = getIntent();
        String parsedStringID = previousIntent.getStringExtra("id");

        // Bug fix!!! This gets the correct business from the businessArraylist using the id
        for (int i = 0; i < StudentMainFragment.businessArraylist.size(); i++){
            if(Integer.valueOf(parsedStringID) == Integer.valueOf(StudentMainFragment.businessArraylist.get(i).getId())){
                selectedBusiness = StudentMainFragment.businessArraylist.get(i);
            };
        }
    }
    /**
     * Allows review overlay to appear and disappear
     * Also Allows user to leave a review
     */
    private void setOverlay() {
        final SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        layout.setDragView(findViewById(R.id.review_button));
        layout.setAnchorPoint(0.22f);
        Button submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBAccess db = new DBAccess();
                RatingBar bar = (RatingBar) findViewById(R.id.ratingBar2);
                System.out.println("CLICK");
                submit_button.setText("value is " + bar.getRating());
            }
        });
        findViewById(R.id.layout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED){
                    layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            }
        });
        layout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

    }

    private void setBusinessDetails() {
        susrb = (RatingBar)findViewById(R.id.susRatingBar);
        TextView businessName = (TextView) findViewById(R.id.businessName);
        TextView businessDesc = (TextView) findViewById(R.id.businessDesc);
        TextView userRating = (TextView) findViewById(R.id.textView);
        ImageView iv = (ImageView) findViewById(R.id.businessImage);
        susrb.setRating(selectedBusiness.getSusRating());
        businessName.setText(selectedBusiness.getName());
        businessDesc.setText(selectedBusiness.getDescription());
        userRating.setText((int) selectedBusiness.getUserRating());
        iv.setImageResource(Integer.parseInt(selectedBusiness.getIconPath()));
    }
}
