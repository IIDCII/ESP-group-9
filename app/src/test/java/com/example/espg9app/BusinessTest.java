package com.example.espg9app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class BusinessTest {
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
    public void testDiscountTiers(){
        Business b = new Business();
        b.setDiscountTiers("30,40,50");
        List<String> tiersArrayList = new ArrayList<String>(Arrays.asList(b.getDiscountTiers().split(",")));
        assertEquals("30",tiersArrayList.get(0));
    }

    @Test
    public void testNumRatingArr(){

    }
}