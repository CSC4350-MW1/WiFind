package com.example.wifindmw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Seller_Main extends AppCompatActivity {
    TextView welcomeSeller;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        welcomeSeller = findViewById(R.id.WelcomeSeller);
        welcomeSeller.setText("Welcome, " + Seller_Login.globalSeller);
        button = findViewById(R.id.AddButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWifiPage();
            }
        });

    }
    public void addWifiPage(){
        Intent intent = new Intent(this, WiFi_Add.class);
        startActivity(intent);
    }
}