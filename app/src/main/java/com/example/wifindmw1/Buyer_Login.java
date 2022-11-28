package com.example.wifindmw1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Buyer_Login extends AppCompatActivity {
    TextView textView;
    Button button;
    EditText usernameText;
    EditText passwordText;
    public static String buyerName;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("buyer_user"); //Getting the connection from personal url
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_login);
        textView = findViewById(R.id.BuyerCreateAcc);
        button = findViewById(R.id.BuyerSignIn);
        usernameText = (EditText) findViewById(R.id.BuyerUsernameInput);
        passwordText = (EditText) findViewById(R.id.BuyerPasswordInput);

        //The text view will lead to a new activity based on registering buyer

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationPage();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString().toLowerCase();
                String password = passwordText.getText().toString();
                if(username.isEmpty() || password.isEmpty()){   //Checking if the user placed missing fields
                    Toast.makeText(Buyer_Login.this, "You have missing fields", Toast.LENGTH_SHORT).show();
                } else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Reading from the Database

                            if(snapshot.hasChild(username)){
                                //Get the specific password for that specific user
                                String getPassword = snapshot.child(username).child("password").getValue(String.class);
                                if(getPassword.equals(password)){
                                    Toast.makeText(Buyer_Login.this, "Successful Login", Toast.LENGTH_SHORT).show();
                                    buyerName = username;
                                    openBuyerMainPage();
                                }else{
                                    Toast.makeText(Buyer_Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Buyer_Login.this, "Username Not found", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            //Throw a error if connection to database is bad
                            Toast.makeText(Buyer_Login.this, "Error with Connection", Toast.LENGTH_SHORT).show();


                        }
                    });
                }

            }
        });
    }
    public void openRegistrationPage(){
        Intent intent = new Intent(this, Buyer_Registration.class);
        startActivity(intent);
    }
    public void openBuyerMainPage(){
        Intent intent = new Intent(this, Buyer_Main.class);
        startActivity(intent);
    }
}