package com.example.espg9app.ui.StudentMain;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.espg9app.Business;
import com.example.espg9app.DBAccess;
import com.example.espg9app.R;
import com.example.espg9app.Voucher;
import com.example.espg9app.VoucherPage;

import com.example.espg9app.ui.BusinessView.BusinessViewAdapter;
import com.google.android.gms.common.util.ArrayUtils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.taufiqrahman.reviewratings.BarLabels;
import com.taufiqrahman.reviewratings.RatingReviews;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BusinessDetail extends AppCompatActivity
{
    RatingBar susrb;
    static Business selectedBusiness;
    ArrayList<Business> BusinessArrayList = new ArrayList<Business>();

    private ListView listView;

    // using a placeholder name for the time being
    String username = "Bob637";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businessdetail);
        getSelectedBusiness();
        setBusinessDetails();
        setOverlay();
        getVoucherList();
        setRatingBar();
        setVoucherList();
        Fragment fragment= new MapFragment();
        // Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.map_container,fragment)
                .commit();
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
    private void setRatingBar() {
        RatingReviews ratingReviews = (RatingReviews) findViewById(R.id.ratingBar);

        int colors[] = new int[]{
                Color.parseColor("#0e9d58"),
                Color.parseColor("#bfd047"),
                Color.parseColor("#ffc105"),
                Color.parseColor("#ef7e14"),
                Color.parseColor("#d36259")};
        //  Generates random review numbers
//        int raters[] = new int[]{
//                new Random().nextInt(100),
//                new Random().nextInt(100),
//                new Random().nextInt(100),
//                new Random().nextInt(100),
//                new Random().nextInt(100)
//        };

        ratingReviews.createRatingBars(100, BarLabels.STYPE1, colors, selectedBusiness.getNumRatingArr());
    }


    private void setVoucherList() {
        ListView listView = (ListView) findViewById(R.id.voucherList);
        BusinessArrayList.add(selectedBusiness);
        BusinessViewAdapter adapter = new BusinessViewAdapter(getApplicationContext(), 0, BusinessArrayList);
        listView.setAdapter(adapter);

        //set up the on-click listener
        voucherOnClick(listView);
    }

    private void voucherOnClick(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DBAccess db = new DBAccess();
                Intent showDetail = new Intent(getApplicationContext(), VoucherPage.class);
                int businessID = selectedBusiness.getId();

                if (db.isVoucherInstance(selectedBusiness.getId(),username)){
                    showDetail.putExtra("instance_id",Integer.toString(db.getVoucherInstanceID(username, businessID)));
                }else {
                    db.createVoucherInstance(selectedBusiness.getId(), username);
                    showDetail.putExtra("instance_id",Integer.toString(db.getVoucherInstanceID(username, businessID)));
                }

                showDetail.putExtra("business_id", (Integer.toString(selectedBusiness.getId())));
                startActivity(showDetail);
            }
        });
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

    private void updateBusinessArr(){

        DBAccess dba = new DBAccess();
        StudentMainFragment.businessArraylist = dba.getAllBusinesses();
        getSelectedBusiness();
        setBusinessDetails();
        setRatingBar();
    }
    /**
     * Allows review overlay to appear and disappear
     * Also Allows user to leave a review
     * Will change review page appearance depending if a previous review exists.
     */
    private void setOverlay() {
        Button submit_button = (Button) findViewById(R.id.submit_button);
        Button review_button = (Button) findViewById(R.id.review_button);
        String username = StudentMainFragment.username;
//        String username="ethanwcit";
        DBAccess db = new DBAccess();
        RatingBar bar = (RatingBar) findViewById(R.id.ratingBar2);
        final SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
//        int currReview = db.getReview(username, selectedBusiness.getId());
//        if (currReview != 0) {
//            bar.setRating(currReview);
//        }
//        review_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                int currReview = db.getReview(username,selectedBusiness.getId());
//
//                if (currReview != -1){
//                    submit_button.setText("Edit Review");
//                    bar.setRating(currReview);
//                }
//                else{
//                    submit_button.setText("Submit Review");
//                }
//            }
//        });
        layout.setDragView(findViewById(R.id.review_button));
        layout.setAnchorPoint(0.22f);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.leaveReview(username,selectedBusiness.getId(), (int) bar.getRating());
                updateBusinessArr();
                if(layout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED){
                    layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
                submit_button.setText("Edit Review");
            }
        });
        findViewById(R.id.layout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBusinessArr();
                if(layout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED){
                    layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                }
            }
        });
        layout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBusinessArr();
                layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }

    private void setBusinessDetails() {

        susrb = (RatingBar)findViewById(R.id.susRatingBar);
        TextView businessName = (TextView) findViewById(R.id.businessName);
        TextView businessDesc = (TextView) findViewById(R.id.businessDesc);
        TextView userRating = (TextView) findViewById(R.id.textView);
        TextView numRating = (TextView) findViewById(R.id.textView6);
        ImageView iv = (ImageView) findViewById(R.id.businessImage);
        susrb.setRating(selectedBusiness.getSusRating());
        businessName.setText(selectedBusiness.getName());
        businessDesc.setText(selectedBusiness.getDescription());
        userRating.setText(String.valueOf(selectedBusiness.getUserRating()));
        numRating.setText(Integer.toString(selectedBusiness.getNumReviews()) + " Review(s)");
        // Tries to set an image as an icon else, set default image
        int id = getResources().getIdentifier("com.example.espg9app:drawable/" + "samplebusinessimage", null, null);
        iv.setImageResource(id);
    }
}