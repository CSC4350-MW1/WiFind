package com.example.wifindmw1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
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

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class WiFi_Add extends AppCompatActivity {
    EditText ssidText, passwordText, priceText, addressText, stateText, durationText, cityText;
    String ssid, password, price, address, state, duration, city;
    Button button;
    WifiManager wifiManager;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("wifi_services");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi_add);
        ssidText = findViewById(R.id.SSIDInput);
        passwordText = findViewById(R.id.WifiPassInput);
        priceText = findViewById(R.id.PriceInput);
        addressText = findViewById(R.id.WifiAddressInput);
        stateText = findViewById(R.id.WifiStateInput);
        cityText = findViewById(R.id.WifiCityInput);
        durationText = findViewById(R.id.DurationInput);
        button = findViewById(R.id.SubmitWifiBtn);
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Getting all the values from the text box and converting them into strings

                ssid = ssidText.getText().toString();
                password = passwordText.getText().toString();
                price = priceText.getText().toString();
                address = addressText.getText().toString();
                state = stateText.getText().toString();
                duration = durationText.getText().toString();
                city = cityText.getText().toString();
                final WifiNetworkSuggestion suggestion1 = new WifiNetworkSuggestion.Builder().setSsid(ssid).setWpa2Passphrase(password).setIsAppInteractionRequired(true).build();
                ArrayList<WifiNetworkSuggestion> networkSuggestions = new ArrayList<>();
                networkSuggestions.add(suggestion1);
                final int status = wifiManager.addNetworkSuggestions(networkSuggestions);  //Getting the Status number
                if(!ssid.equals("") && !password.equals("") && !price.equals("") && !address.equals("") && !state.equals("") && !city.equals("") && !duration.equals("")) {

                    //We'll still fake the process of validating internet connection, since emulators cant connect to real internet
                    if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {

                        //Remove multiline comments to add the wifi information to database even though it's incorrect. You don't need to however
                        /*
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(ssid)) {
                                    Toast.makeText(WiFi_Add.this, "SSID already exists", Toast.LENGTH_SHORT).show();
                                }else {
                                    Random rand = new Random(); //Creating Random Function for generating WIFI ID and fake bandwidth
                                    int wifiID = rand.nextInt((10000 - 1) + 1);
                                    databaseReference.child(String.valueOf(wifiID)).child("password").setValue(password);
                                    databaseReference.child(String.valueOf(wifiID)).child("price").setValue(Integer.parseInt(price));
                                    databaseReference.child(String.valueOf(wifiID)).child("address").setValue(address);
                                    databaseReference.child(String.valueOf(wifiID)).child("city").setValue(city + ", " + state);
                                    databaseReference.child(String.valueOf(wifiID)).child("duration").setValue(Integer.parseInt(duration));
                                    databaseReference.child(String.valueOf(wifiID)).child("ssid").setValue(ssid);
                                    int randomBandwidth = rand.nextInt((950 - 1) + 1); //Creating random bandwidth
                                    databaseReference.child(String.valueOf(wifiID)).child("bandwidth").setValue(randomBandwidth);
                                    databaseReference.child(String.valueOf(wifiID)).child("seller_user").setValue(Seller_Login.globalSeller);

                                    Toast.makeText(WiFi_Add.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(WiFi_Add.this, "Error with connection", Toast.LENGTH_SHORT).show();
                            }
                        });

                         */
                        Toast.makeText(WiFi_Add.this, "Internet connection invalid", Toast.LENGTH_SHORT).show(); //should show internet connection "not" valid

                    } else {
                        //This is for a real mobile device. Checking validating connection should work
                        databaseReference.child("wifi_services").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(ssid)) {    //Checking if SSID already exists during registration
                                    Toast.makeText(WiFi_Add.this, "SSID already exists", Toast.LENGTH_SHORT).show();
                                }else {
                                    Random rand = new Random(); //Creating Random Function for generating WIFI ID and fake bandwidth
                                    int wifiID = rand.nextInt((10000 - 1) + 1);
                                    databaseReference.child(String.valueOf(wifiID)).child("password").setValue(password);
                                    databaseReference.child(String.valueOf(wifiID)).child("price").setValue(Integer.parseInt(price));
                                    databaseReference.child(String.valueOf(wifiID)).child("address").setValue(address);
                                    databaseReference.child(String.valueOf(wifiID)).child("city").setValue(city + ", " + state);
                                    databaseReference.child(String.valueOf(wifiID)).child("duration").setValue(Integer.parseInt(duration));
                                    databaseReference.child(String.valueOf(wifiID)).child("ssid").setValue(ssid);

                                    //This part is for getting the network connection speed of a physical device

                                    WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                    int linkSpeed = wifiManager.getConnectionInfo().getRssi();
                                    int level = WifiManager.calculateSignalLevel(linkSpeed, 5);
                                    level = level * 100;    //Converting speed to Mbps

                                    //Adding it into our database

                                    databaseReference.child(String.valueOf(wifiID)).child("bandwidth").setValue(level);
                                    databaseReference.child(String.valueOf(wifiID)).child("seller_user").setValue(Seller_Login.globalSeller);
                                    Toast.makeText(WiFi_Add.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(WiFi_Add.this, "Error with connection", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Toast.makeText(WiFi_Add.this, "Internet connection valid", Toast.LENGTH_SHORT).show(); //should show internet connection "not" valid
                        Toast.makeText(WiFi_Add.this, "Internet connection valid", Toast.LENGTH_SHORT).show();

                    }
                } else{
                    Toast.makeText(WiFi_Add.this, "You have missing fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}