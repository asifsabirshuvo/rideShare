package com.example.asifsabir.rideshareapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by asifsabir on 2/1/18.
 */


public class ShowDriverRequest extends AppCompatActivity {
    TextView tvRiderName, tvRiderPhone, tvRiderNid, tvRiderRating,
            tvDriverName, tvDriverPhone, tvDriverNid, tvDriverRating,
            tvDistance, tvFare, tvRideStatus;
    LinearLayout rateLayout;
    String riderName, riderPhone, riderRating, riderNid;
    String driverName, driverPhone, driverRating, driverNid;
    String fare, distance, status;
    Button btnStartRide, btnEndRide, btnCancelRide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_show_request);
        getSupportActionBar().setTitle("Show ride Request");

        tvRiderName = (TextView) findViewById(R.id.tv_driver_rider_name);
        tvRiderPhone = (TextView) findViewById(R.id.tv_driver_rider_phone);
        tvRiderNid = (TextView) findViewById(R.id.tv_driver_rider_nid);
        tvRiderRating = (TextView) findViewById(R.id.tv_driver_rider_rating);

        tvDriverName = (TextView) findViewById(R.id.tv_driver_driver_name);
        tvDriverPhone = (TextView) findViewById(R.id.tv_driver_driver_phone);
        tvDriverNid = (TextView) findViewById(R.id.tv_driver_driver_nid);
        tvDriverRating = (TextView) findViewById(R.id.tv_driver_driver_rating);

        tvDistance = (TextView) findViewById(R.id.tv_driver_distance);
        tvFare = (TextView) findViewById(R.id.tv_driver_fare);
        tvRideStatus = (TextView) findViewById(R.id.tv_driver_ride_status);

        btnStartRide = (Button) findViewById(R.id.btn_start_ride);
        btnEndRide = (Button) findViewById(R.id.btn_end_ride);
        btnCancelRide = (Button) findViewById(R.id.btn_cancel_ride);


        SharedPreferences prefs = getSharedPreferences("driverData", MODE_PRIVATE);
        int driverPhoneSaved = prefs.getInt("driverPhone", 0);

        final String reqKey = getIntent().getExtras().getString("reqKey", null);


        //fetching this drivers data

        final DatabaseReference DriverRef = FirebaseDatabase.getInstance().getReference("Driver").child(String.valueOf(driverPhoneSaved));
        DriverRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                DriverReg driverReg = snapshot.getValue(DriverReg.class);
                driverName = driverReg.fullName;
                driverPhone = driverReg.nid;
                driverNid = driverReg.rating;
                driverRating = driverReg.rating;

                //setting on TextView

                tvDriverName.setText(driverName);
                tvDriverPhone.setText(driverPhone);
                tvDriverNid.setText(driverNid);
                tvDriverRating.setText(driverRating);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowDriverRequest.this, "Error happened in fetching driver data!", Toast.LENGTH_SHORT).show();
            }

        });

        //fetching rides data (riders data)
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("rideRequest").child(reqKey);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                RideRequest rideRequest = snapshot.getValue(RideRequest.class);

                riderName = rideRequest.riderName;
                riderPhone = rideRequest.riderPhone;
                riderNid = rideRequest.riderNid;
                riderRating = rideRequest.riderRating;

                distance = rideRequest.distance;
                fare = rideRequest.fare;


                //setting on textview

                tvRiderName.setText(riderName);
                tvRiderPhone.setText(riderPhone);
                tvRiderNid.setText(riderNid);
                tvRiderRating.setText(riderRating);

                tvDistance.setText(rideRequest.distance + " Km");
                tvFare.setText(rideRequest.fare + " Tk.");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowDriverRequest.this, "Error happened in fetching ride data!", Toast.LENGTH_SHORT).show();
            }

        });


        btnStartRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancelRide.setVisibility(View.GONE);
                btnStartRide.setVisibility(View.GONE);
                btnEndRide.setVisibility(View.VISIBLE);
                Toast.makeText(ShowDriverRequest.this, "Ride Started", Toast.LENGTH_SHORT).show();

                //updating ride data with driver data
                DatabaseReference reqRef = FirebaseDatabase.getInstance().getReference("rideRequest").child(reqKey);

                RideRequest rideRequest = new RideRequest(riderName, riderPhone, riderNid, riderRating,
                        driverName, driverPhone, driverNid, driverRating,
                        distance, fare, "1", reqKey);
                reqRef.setValue(rideRequest);

                tvRideStatus.setText("Ride Started");
                tvRideStatus.setTextColor(Color.BLUE);
            }
        });

        btnEndRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //incrementing status
                final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("rideRequest").child(reqKey).child("rideStatus");
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String count = snapshot.getValue(String.class);
                        int countNum = Integer.parseInt(count);
                        ++countNum;
                        rootRef.setValue(String.valueOf(countNum));


                        tvRideStatus.setText("Ride Ended");
                        tvRideStatus.setTextColor(Color.GREEN);
                        Toast.makeText(ShowDriverRequest.this, "Please rate the rider!", Toast.LENGTH_SHORT).show();
                        //enable rating button stuff
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ShowDriverRequest.this, "Error happened in fetching updating data!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnCancelRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ShowDriverRequest.this, "Ride Cancelled", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
