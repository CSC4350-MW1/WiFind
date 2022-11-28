package com.example.wifindmw1;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSellerLogin();
            }
        });
        button2 = (Button) findViewById(R.id.button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBuyerLogin();
            }
        });
    }
    public void openSellerLogin(){
        Intent intent = new Intent(this, Seller_Login.class);
        startActivity(intent);

    }
    public void openBuyerLogin(){
        Intent intent = new Intent(this, Buyer_Login.class);
        startActivity(intent);
    }

}