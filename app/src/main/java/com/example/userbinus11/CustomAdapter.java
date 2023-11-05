package com.example.userbinus11;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    ArrayList<String> logo;
    ArrayList<String> regionName;
    ArrayList<String> address;
    ArrayList<Double> latitude;
    ArrayList<Double> longitude;
    Context ctx;

    public CustomAdapter(ArrayList<String> logo, ArrayList<String> regionName, ArrayList<String> address, ArrayList<Double> longitude, ArrayList<Double> latitude, Context ctx) {
        this.logo = logo;
        this.regionName = regionName;
        this.address = address;
        this.ctx = ctx;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_region, parent,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        System.out.println("position = " + position);
        //set the data in items
        // Load the region logo using Glide (you need to add the Glide dependency to your project)
        Glide.with(ctx)
                .load(logo.get(position))
                .into(holder.regionLogo);

        holder.regionName.setText(regionName.get(position));
        holder.regionAddress.setText(address.get(position));

        //Adding onClickListener event on item view
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ctx, MapsActivity.class);

                // Add latitude and longitude data as extras to the intent
                intent.putExtra("longitude", longitude.get(position));
                intent.putExtra("latitude", latitude.get(position));

                // Start the MapsActivity with the intent
                ctx.startActivity(intent);
//                Toast.makeText(ctx,regionName.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return regionName.size();
    }

    //MyHolder Class
    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView regionLogo;
        TextView regionName, regionAddress;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            regionLogo = itemView.findViewById(R.id.regionLogo);
            regionName = itemView.findViewById(R.id.regionName);
            regionAddress = itemView.findViewById(R.id.regionAddress);
        }
    }
}