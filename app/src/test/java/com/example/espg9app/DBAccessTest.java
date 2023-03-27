package com.example.espg9app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DBAccessTest {

    DBAccess db = new DBAccess();

    @BeforeAll
    static void setUpBeforeClass() throws Exception{
        System.out.println("Set Up Before Class - @BeforeAll");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception{
        System.out.println("Tear Down After Class - @AfterAll");
    }

    @BeforeEach
    void setUp() throws Exception{
        System.out.println("Set Up @BeforeEach");
    }

    @AfterEach
    void tearDown() throws Exception{
        System.out.println("Tear Down @AfterEach");
    }

    @Test
    public void testConnection(){
        DBAccess db = new DBAccess();
        db.openConnection();
        assertNotEquals(db.conn,null);
        db.closeConnection();
    }

    @Test
    public void testVoucherInstanceAndActiveCheck(){
        db.createVoucherInstance(34,"John");
        db.createVoucherInstance(3472,"Elipso'as");
        db.createVoucherInstance(-574,"Makiri");
        db.createVoucherInstance(40,"Zanzibar");

        assertTrue(db.checkVoucherInstanceExistsAndActive(db.getVoucherInstanceID("John",34)));
        assertFalse(db.checkVoucherInstanceExistsAndActive(db.getVoucherInstanceID("Elipso'as",3472)));
        assertFalse(db.checkVoucherInstanceExistsAndActive(db.getVoucherInstanceID("Makiri",-574)));
        assertTrue(db.checkVoucherInstanceExistsAndActive(db.getVoucherInstanceID("Zanzibar",40)));
    }

    @Test
    public void testGetAllBusinesses(){
        ArrayList<Business> businessArraylist = db.getAllBusinesses();
        assertEquals(businessArraylist.size(),7);
    }

    @Test
    public void testGetUsername(){
        String username = db.getUsername("sz2075@bath.ac.uk");
        System.out.println(username);
        assertEquals("salmaanzhang", username);
    }

    @Test
    public void testVoucherInstanceCheck(){
        db.createVoucherInstance(34,"John");
        db.createVoucherInstance(100,"Elipso'as");
        db.createVoucherInstance(-574,"Makiri");
        db.createVoucherInstance(40,"Zanzibar");

        assertTrue(db.isVoucherInstance(34,"John"));
        assertTrue(db.isVoucherInstance(40,"Zanzibar"));

        assertFalse(db.isVoucherInstance(100, "Elipso'as"));
        assertFalse(db.isVoucherInstance(-574,"Makiri"));
    }

    @Test
    public void testLeaveReview(){
        assertTrue(db.leaveReview("salmaanzhang",34,3));
        assertFalse(db.leaveReview("salmaanzhang",34,50));
    }

    @Test
    public void testVoucherActivation(){
        assertTrue(db.activateVoucher(34));
        assertFalse(db.activateVoucher(-124125815));
    }

    @Test
    public void testDeleteVoucherInstance(){
        assertTrue(db.deleteVoucherInstance(34,"salmaanzhang"));
    }


}