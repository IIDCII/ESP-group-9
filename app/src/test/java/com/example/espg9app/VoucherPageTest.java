package com.example.espg9app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoucherPageTest {

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
    public void testDiscountLayout(){
        int totalDiscount = 70;
        String fullDesc = totalDiscount + "% off\n" + "Discount on all products";

        assertEquals(fullDesc,"70% off\nDiscount on all products");
    }
}