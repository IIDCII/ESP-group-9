package com.example.espg9app.QRcode;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class QRReaderTest {
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
    public void testInputCodeReceived(){
        String contentQR = "24" +","+ "Bob";
        List<String> businessIDAndUsername = new ArrayList<String>(Arrays.asList(contentQR.split(",")));
        String username = businessIDAndUsername.get(1);
        String businessID = businessIDAndUsername.get(0);
        assertEquals(username,"Bob");
        assertEquals(businessID,"24");
    }
}