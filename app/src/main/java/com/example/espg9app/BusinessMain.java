package com.example.espg9app;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class BusinessMain extends AppCompatActivity{
    private ListView Vouchers;
    private Button addBtn;
    private EditText itemEdt;
    private ArrayList<String> lngList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.businessmain);
        Vouchers = findViewById(R.id.idLVLanguages);
        addBtn = findViewById(R.id.btn_Add);
        itemEdt = findViewById(R.id.voucherName);
        lngList = new ArrayList<>();
        lngList.add("voucher 1");
        lngList.add("voucher 2");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lngList);

        Vouchers.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String item = itemEdt.getText().toString();

                if (!item.isEmpty()) {
                    lngList.add(item);
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

    }


