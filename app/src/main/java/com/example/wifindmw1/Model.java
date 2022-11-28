package com.example.wifindmw1;

import com.google.firebase.database.IgnoreExtraProperties;


public class Model {
    String seller_user, address, city, password, ssid;
    long bandwidth, price, duration;

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public long getBandwidth() {
        return bandwidth;
    }

    public long getPrice() {
        return price;
    }
    public long getDuration(){return duration;}

    public String getSeller_user() {
        return seller_user;
    }
    public String getPassword(){
        return password;
    }
    public String getSsid(){
        return ssid;
    }


}
