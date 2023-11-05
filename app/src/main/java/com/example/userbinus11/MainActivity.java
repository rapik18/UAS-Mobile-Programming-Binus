package com.example.userbinus11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    ArrayList<String> logo = new ArrayList<>();
    ArrayList<String> regionName = new ArrayList<>();
    ArrayList<String> address = new ArrayList<>();
    ArrayList<Double> longitude = new ArrayList<>();
    ArrayList<Double> latitude = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        try {
            JSONObject obj = new JSONObject(loadJSONfromAssets()); // Change string JSON to JSONObject

            // Parse the JSON data and populate the regionList
            JSONArray jsonArray = obj.getJSONArray("region");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                logo.add(jsonObject.getString("logo"));
                regionName.add(jsonObject.getString("name"));
                address.add(jsonObject.getString("address_office"));

                JSONObject coordinate = jsonObject.getJSONObject("coordinate");
                System.out.println("coordinate = " + coordinate);
                longitude.add((coordinate.getDouble("longitude")));
                latitude.add((coordinate.getDouble("latitude")));

            }
        } catch (JSONException e) {
            Log.e("JSON Read Error", "Error reading JSON file: " + e.getMessage());
        }


        CustomAdapter customAdapter = new CustomAdapter(logo,regionName,address,longitude,latitude, MainActivity.this);
        recyclerView.setAdapter(customAdapter);
    }

    private String loadJSONfromAssets(){
        String json = null;
        try {
        // Load JSON data from the "regions.json" file in the "assets" folder
        AssetManager assetManager = getResources().getAssets();
        InputStream is = assetManager.open("region.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e("JSON Read Error", "Error reading JSON file: " + e.getMessage());
            return null;
        }
        return json;
    }
}