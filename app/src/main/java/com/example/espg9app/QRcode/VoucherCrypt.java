package com.example.espg9app.QRcode;

import java.time.LocalDate;
import java.time.LocalTime;

public class VoucherCrypt {
    private String voucherTitle;
    private int discount;
    private String user;
    private LocalDate date;
    private LocalTime time;

    public VoucherCrypt(String vid,String bid){
        String voucherId = vid;
        String businessId = bid;
    }

    public String encrypt(){
        return "ohhh";
    }

    public String decrypt(){
        return "chill";
    }
}
