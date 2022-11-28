package com.example.wifindmw1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Seller_Registration extends AppCompatActivity {
    private EditText username, password, firstName, lastName, email;
    private Button button;
    private String uName, uPass, uFirst, uLast, uEmail;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("seller_user");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Getting all the ids from the text box
        setContentView(R.layout.activity_seller_registration);
        username = (EditText) findViewById(R.id.RUsernameInput);
        password = (EditText) findViewById(R.id.RPasswordInput);
        firstName = (EditText) findViewById(R.id.RFirstNameInput);
        lastName = (EditText) findViewById(R.id.RLastNameInput);
        email = (EditText) findViewById(R.id.REmailInput);
        button = (Button) findViewById(R.id.BRegButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Getting all the values from the User Input

                uName = username.getText().toString().toLowerCase();
                uPass = password.getText().toString();
                uFirst = firstName.getText().toString().toLowerCase();
                uLast = lastName.getText().toString().toLowerCase();
                uEmail = email.getText().toString().toLowerCase();

                //Checking if the fields are empty or not

                if(uName.isEmpty() || uPass.isEmpty() || uFirst.isEmpty() || uLast.isEmpty() || uEmail.isEmpty()){
                    Toast.makeText(Seller_Registration.this, "All categories needs to be filled", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(uName)){
                                Toast.makeText(Seller_Registration.this, "User already exists", Toast.LENGTH_SHORT).show();
                            }else{
                                databaseReference.child(uName).child("password").setValue(uPass);
                                databaseReference.child(uName).child("first_name").setValue(uFirst);
                                databaseReference.child(uName).child("last_name").setValue(uLast);
                                databaseReference.child(uName).child("email").setValue(uEmail);
                                Toast.makeText(Seller_Registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                        //Throw a error message if database couldn't connect
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Seller_Registration.this, "Error: Connection", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });

    }

}