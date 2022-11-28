package com.example.wifindmw1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class PaymentConfirmation extends AppCompatActivity {
    TextView cardNum, cardName, exp, cvv, price;
    Button button1;
    Button button2;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("payments");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);
        cardNum = findViewById(R.id.dummyNumber);
        cardName = findViewById(R.id.dummyName);
        exp = findViewById(R.id.dummyExpr);
        cvv = findViewById(R.id.dummyCVV);
        price = findViewById(R.id.total);

        cardNum.setText(Payment_Selection.cardNumber);
        cardName.setText(Payment_Selection.cardName);
        exp.setText(Payment_Selection.expirationDate);
        cvv.setText(Payment_Selection.cvv);
        price.setText("Your total is : $" + String.valueOf(Buyer_Main.price));
        button1 = findViewById(R.id.cancelButton);
        button2 = findViewById(R.id.confirmButton);
        button1.setOnClickListener(new View.OnClickListener() {
           @Override
             public void onClick(View view) {
               Toast.makeText(PaymentConfirmation.this, "Payment Cancelled", Toast.LENGTH_SHORT).show();
              toPaymentSelection();  //Go back to the payment selection if cancelled
            }
          });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(PaymentConfirmation.this, "Payment Confirmed", Toast.LENGTH_SHORT).show();
                        Random rand = new Random(); //Creating transaction ID
                        int randomTransactionNum = rand.nextInt((10000 - 1) + 1);
                        String transactionNum = String.valueOf(randomTransactionNum);

                        //Adding payment information to the database

                        databaseReference.child(transactionNum).child("card_number").setValue(Payment_Selection.cardNumber);
                        databaseReference.child(transactionNum).child("card_name").setValue(Payment_Selection.cardName);
                        databaseReference.child(transactionNum).child("expiration_date").setValue(Payment_Selection.expirationDate);
                        databaseReference.child(transactionNum).child("seller_user").setValue(Buyer_Main.sellerName);
                        databaseReference.child(transactionNum).child("buyer_user").setValue(Buyer_Login.buyerName);
                        toBuyerWiFi();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(PaymentConfirmation.this, "Failed to connect to database", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public void toPaymentSelection(){
        Intent intent = new Intent(this, Payment_Selection.class);
        startActivity(intent);
    }
    public void toBuyerWiFi(){
        Intent intent = new Intent(this, Buyer_WiFi.class);
        startActivity(intent);
    }
}