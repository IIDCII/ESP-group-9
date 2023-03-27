package com.example.espg9app.QRcode;

import static org.junit.jupiter.api.Assertions.*;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import com.example.espg9app.VoucherPage;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class QRPageTest {
    String content = "example";
    int width = 1000;
    int height = 1000;


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
    public void testQRSize() throws Exception {
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);

        // Verify that the QR code has the correct size
        assertEquals(width, bitMatrix.getWidth());
        assertEquals(height, bitMatrix.getHeight());
    }


    @Test
    public void testQROutput() throws Exception {
        MultiFormatWriter writer = new MultiFormatWriter();
        QRCodeReader reader = new QRCodeReader();

        BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);

        // Verify that the QR code content is correct
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * width + x] = Color.BLACK;
                } else {
                    pixels[y * width + x] = Color.WHITE;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);

        String result = reader.decode(new BinaryBitmap(new HybridBinarizer(source))).getText();

        assertEquals(content, result);
    }
}