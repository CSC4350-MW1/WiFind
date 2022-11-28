package com.example.wifindmw1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

//For getting the list of iformation for our database
import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    ArrayList<Model> mLst;
    Context context;
    private SelectListener listener; //For making clickable listener in the recycle view

    public Adapter(Context context, ArrayList<Model> mLst, SelectListener listener){
        this.context = context;
        this.mLst = mLst;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Making the list resizable
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Model model =  mLst.get(position);
        holder.seller_user.setText(String.valueOf(model.getSeller_user()));  //Getting username of seller from Model class
        holder.address.setText(model.getAddress());  //Getting address from Model class
        holder.state.setText(model.getCity());  //Getting state from Model class
        holder.bandwidth.setText(String.valueOf(model.getBandwidth()));  //Getting bandwidth from Model class
        holder.price.setText(String.valueOf(model.getPrice()));  //Getting price from Model class
        holder.duration.setText(String.valueOf(model.getDuration()));  //Getting duration of WIFI from Model class
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(mLst.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return mLst.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView seller_user, address, state, price, bandwidth, password, duration;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            seller_user = itemView.findViewById(R.id.dummySeller);
            address = itemView.findViewById(R.id.dummyAddress);
            state = itemView.findViewById(R.id.dummyCity);
            price = itemView.findViewById(R.id.dummyPrice);
            bandwidth = itemView.findViewById(R.id.dummyBandwidth);
            duration = itemView.findViewById(R.id.dummyDuration);
            cardView = itemView.findViewById(R.id.main_container);


        }
    }
}
