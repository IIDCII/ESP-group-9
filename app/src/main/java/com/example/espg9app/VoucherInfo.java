package com.example.espg9app;

public class VoucherInfo {

    String voucherName;
    Boolean liveCheck;
    String voucherID;
    String businessName;

    public VoucherInfo(String voucherName, Boolean liveCheck, String businessName ){
        this.voucherName = voucherName;
        this.liveCheck = liveCheck;
        this.businessName = businessName;
    }


}
