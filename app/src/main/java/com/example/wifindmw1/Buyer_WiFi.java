package com.example.wifindmw1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Buyer_WiFi extends AppCompatActivity {
    Button button;
    Context context = this;
    private TextView countDownText;
    private CountDownTimer countDownTimer;

    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_wi_fi);
        button = (Button) findViewById(R.id.buttonWifi);
        long dur = TimeUnit.MINUTES.toMillis(Buyer_Main.duration);
        countDownText = findViewById(R.id.countdown_text);
        String getDurationCount = String.valueOf(Buyer_Main.duration) +":"+"00";
        countDownText.setText(getDurationCount);    //Setting the timer text that the Buyer User paid for

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final WifiNetworkSuggestion suggestion1 =
                        new WifiNetworkSuggestion.Builder()
                                .setSsid(Buyer_Main.ssid)
                                .setWpa2Passphrase(Buyer_Main.password)
                                .setIsAppInteractionRequired(true) // Optional (Needs location permission)
                                .build();
                // configure passpointConfig to include a valid Passpoint configuration
                final PasspointConfiguration passpointConfig = new PasspointConfiguration();
                List<WifiNetworkSuggestion> list = new ArrayList<>();
                list.add(suggestion1);

                final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                final int status = wifiManager.addNetworkSuggestions(list);

                // Error handling based on status. If there is an error, go back to previous activity

                if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
                    Toast.makeText(Buyer_WiFi.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(dur, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            countDownText.setText(String.format("%02d : %02d",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                        }

                        @Override
                        public void onFinish() {
                            countDownText.setVisibility(View.GONE);
                            Toast.makeText(Buyer_WiFi.this, "WIFI Service ends", Toast.LENGTH_SHORT).show();
                        }
                    }.start();


                }

                // Optional (Wait for post connection broadcast to one of your suggestions)
                final IntentFilter intentFilter = new IntentFilter(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION);
                final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (!intent.getAction().equals(WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION)) {
                            return;
                        }
                    }
                };
                context.registerReceiver(broadcastReceiver, intentFilter);
                new CountDownTimer(dur, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        countDownText.setText(String.format("%02d : %02d",
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    }


                    @Override
                    public void onFinish() {
                        countDownText.setVisibility(View.GONE);
                        Toast.makeText(Buyer_WiFi.this, "WIFI Service ends", Toast.LENGTH_SHORT).show();
                    }
                }.start();


            }
        });



    }
    public void toPaymentConfirmation(){
        Intent intent = new Intent(this, PaymentConfirmation.class);
        startActivity(intent);
    }

}
