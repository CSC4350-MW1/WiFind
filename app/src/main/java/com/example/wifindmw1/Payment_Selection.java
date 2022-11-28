package com.example.wifindmw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Payment_Selection extends AppCompatActivity {
Button button;
public static String cardNumber, expirationDate, cvv, cardName;
EditText cardNum1, cardNum2, cardNum3, cardNum4, exp1, exp2, cVV, cardN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_selection);

        button = findViewById(R.id.confirmPaymentBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardNum1 = findViewById(R.id.firstDigit);
                cardNum2 = findViewById(R.id.secondDigit);
                cardNum3 = findViewById(R.id.thirdDigit);
                cardNum4 = findViewById(R.id.fourthDigit);
                cardN = findViewById(R.id.CardHolderInput);
                exp1 = findViewById(R.id.monthInput);
                exp2 = findViewById(R.id.yearInput);
                cVV = findViewById(R.id.cvvInput);
                cardNumber = cardNum1.getText().toString() + "-"   + cardNum2.getText().toString() + "-" + cardNum3.getText().toString() + "-" + cardNum4.getText().toString();
                expirationDate = exp1.getText().toString() + "/" + exp2.getText().toString();
                cvv = cVV.getText().toString();
                cardName = cardN.getText().toString();
                if(cardNumber.length() == 19 && expirationDate.length()==5 && cVV.length() == 3 && !cardName.equals("")){
                    toPaymentConfirmation();
                }
                else{
                    Toast.makeText(Payment_Selection.this, "Missing fields", Toast.LENGTH_SHORT);
                }


            }
        });
    }
    public void toPaymentConfirmation(){
        Intent intent = new Intent(this, PaymentConfirmation.class);
        startActivity(intent);
    }
}