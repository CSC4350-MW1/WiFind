package com.example.wifindmw1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Buyer_Main extends AppCompatActivity implements SelectListener, AdapterView.OnItemSelectedListener{
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private Adapter adapter;
    private ArrayList<Model> list;
    String selectedOption; //For selecting spinner option value
    public static String sellerName, address, city, ssid, password;
    public static long duration, price, bandwidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference = FirebaseDatabase.getInstance().getReference("wifi_services");
        list = new ArrayList<>();
        adapter = new Adapter(this, list, this);
        recyclerView.setAdapter(adapter);
        Spinner spinner = findViewById(R.id.spinner1);
        ArrayAdapter <CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.text, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);
        button = findViewById(R.id.searchBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valToSet = spinner.getSelectedItem().toString();
                if (valToSet.equals("Price")) {
                    databaseReference.orderByChild("price").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Buyer_Main.this, "Error: Connection", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (valToSet.equals("Bandwidth")){
                    databaseReference.orderByChild("bandwidth").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Buyer_Main.this, "Error: Connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if (valToSet.equals("Duration")){
                    databaseReference.orderByChild("duration").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Model model = dataSnapshot.getValue(Model.class);
                                list.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Buyer_Main.this, "Error: Connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    //This is for the action of clicking each card view

    @Override
    public void onItemClicked(Model model) {
        Toast.makeText(this, model.getSeller_user() + " selected", Toast.LENGTH_SHORT).show();

        sellerName =  model.getSeller_user();
        address = model.getAddress();
        city = model.getCity();
        duration = model.getDuration();
        price = model.getPrice();
        bandwidth = model.getBandwidth();
        ssid = model.getSsid();
        password = model.getPassword();
        Intent intent = new Intent(this, BuyerConfirmation.class);
        startActivity(intent);


    }

    //For the Spinner selected text
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        selectedOption = text;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
