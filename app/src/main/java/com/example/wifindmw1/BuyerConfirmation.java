package com.example.wifindmw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BuyerConfirmation extends AppCompatActivity {
    TextView sName, bandwidth, city, price, address, duration;
    Button button1, button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_confirmation);

        //Finding the view of fields of the card
        sName = findViewById(R.id.cSellerName);
        bandwidth = findViewById(R.id.cBandwidth);
        city = findViewById(R.id.cCity);
        price = findViewById(R.id.cPrice);
        address = findViewById(R.id.cAddress);
        duration = findViewById(R.id.cDuration);

        //Setting the value from the static variables from Buyer_Main class into fields of the card

        sName.setText(Buyer_Main.sellerName);
        bandwidth.setText(String.valueOf(Buyer_Main.bandwidth));
        city.setText(Buyer_Main.city);
        price.setText(String.valueOf(Buyer_Main.price));
        address.setText(String.valueOf(Buyer_Main.address));
        duration.setText(String.valueOf(Buyer_Main.duration));

        button1 = findViewById(R.id.noButton);
        button2 = findViewById(R.id.yesButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toBuyerMain();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPaymentSelection();
            }
        });

    }
    public void toBuyerMain(){
        Intent intent = new Intent(this, Buyer_Main.class);
        startActivity(intent);
    }
    public void toPaymentSelection(){
        Intent intent = new Intent(this, Payment_Selection.class);
        startActivity(intent);
    }


}