package com.example.espg9app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DBAccessTest {

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
        DBAccess db = new DBAccess();
        db.createVoucherInstance(34,"John");
        db.createVoucherInstance(3472,"Elipso'as");
        db.createVoucherInstance(-574,"Makiri");
        db.createVoucherInstance(0,"Zanzibar");

        assertTrue(db.checkVoucherInstanceExistsAndActive(db.getVoucherInstanceID("John",34)));
        assertTrue(db.checkVoucherInstanceExistsAndActive(db.getVoucherInstanceID("Elipso'as",3472)));
        assertTrue(db.checkVoucherInstanceExistsAndActive(db.getVoucherInstanceID("Makiri",-574)));
        assertTrue(db.checkVoucherInstanceExistsAndActive(db.getVoucherInstanceID("Zanzibar",0)));
    }

    @Test
    public void testGetAllBusinesses(){
        DBAccess db = new DBAccess();
        ArrayList<Business> businessArraylist = db.getAllBusinesses();
        assertEquals(businessArraylist.size(),3);
    }

    @Test
    public void testGetUsername(){
        DBAccess db = new DBAccess();
        String username = db.getUsername("sz2075@bath.ac.uk");
        System.out.println(username);
        assertEquals("salmaanzhang", username);
    }

    @Test
    public void testVoucherInstanceCheck(){
        DBAccess db = new DBAccess();
        db.createVoucherInstance(34,"John");
        db.createVoucherInstance(3472,"Elipso'as");
        db.createVoucherInstance(-574,"Makiri");
        db.createVoucherInstance(0,"Zanzibar");

        assertTrue(db.isVoucherInstance(34,"John"));
        assertTrue(db.isVoucherInstance(3472, "Elipso'as"));
        assertTrue(db.isVoucherInstance(-574,"Makiri"));
        assertTrue(db.isVoucherInstance(0,"Zanzibar"));
    }

}