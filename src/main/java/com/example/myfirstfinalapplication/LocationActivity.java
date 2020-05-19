package com.example.myfirstfinalapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    double latitude, longitude;
    Button get, send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        get = findViewById(R.id.getlocationButton);
        send = findViewById(R.id.sendButton);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (!haveCoarseLocationPermission())
            haveCoarseLocationPermission();
        if(!haveFineLocationPermission())
            haveFineLocationPermission();

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (longitude != 0 && latitude != 0) {
                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT,
                            "http://maps.google.com/?q="+ latitude +"," + longitude);
                    try {
                        startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(LocationActivity.this,
                                "WhatsApp have not been installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void getLastLocation() {
        fusedLocationClient.getLastLocation().addOnCompleteListener(
                new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            Toast.makeText(LocationActivity.this, "Location is null.", Toast.LENGTH_SHORT).show();
                        } else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            String msg = "Latitude: " + String.valueOf(latitude) + " Longitude: " + String.valueOf(longitude);
                            Toast.makeText(LocationActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public boolean haveFineLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 4);
                return false;
            }
        }
        return true;
    }

    public boolean haveCoarseLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 4);
                return false;
            }
        }
        return true;
    }

}
