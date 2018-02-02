package com.example.asifsabir.rideshareapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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

public class ShowRiderRequest extends AppCompatActivity {
    TextView tvRiderName,tvRiderPhone,tvRiderNid,tvRiderRating,
            tvDriverName,tvDriverPhone,tvDriverNid,tvDriverRating,
            tvDistance,tvFare,tvRideStatus;
    LinearLayout driverLayout;
    Button btnRateDriver;
    RatingBar rateDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_show_request);
        getSupportActionBar().setTitle("My Ride Request");

        rateDriver = (RatingBar)findViewById(R.id.rt_bar_to_driver);
        btnRateDriver =(Button)findViewById(R.id.btn_rate_driver);

        driverLayout = (LinearLayout)findViewById(R.id.driver_layout);
        tvRiderName = (TextView)findViewById(R.id.tv_my_name);
        tvRiderPhone = (TextView)findViewById(R.id.tv_my_phone);
        tvRiderNid = (TextView)findViewById(R.id.tv_my_nid);
        tvRiderRating = (TextView)findViewById(R.id.tv_my_rating);

        tvDriverName = (TextView)findViewById(R.id.tv_my_driver_name);
        tvDriverPhone = (TextView)findViewById(R.id.tv_my_driver_phone);
        tvDriverNid = (TextView)findViewById(R.id.tv_my_driver_nid);
        tvDriverRating = (TextView)findViewById(R.id.tv_my_driver_rating);

        tvDistance = (TextView)findViewById(R.id.tv_my_distance);
        tvFare = (TextView)findViewById(R.id.tv_my_fare);
        tvRideStatus = (TextView)findViewById(R.id.tv_my_rider_status);


//rendering  ride data

        String reqKey = getIntent().getExtras().getString("reqKey", null);

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("rideRequest").child(reqKey);
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                RideRequest rideRequest = snapshot.getValue(RideRequest.class);

                tvRiderName.setText(rideRequest.riderName);
                tvRiderPhone.setText(rideRequest.riderPhone);
                tvRiderNid.setText(rideRequest.riderNid);
                tvRiderRating.setText(rideRequest.riderRating);

                tvDriverName.setText(rideRequest.driverName);
                tvDriverPhone.setText(rideRequest.driverPhone);
                tvDriverNid.setText(rideRequest.driverNid);
                tvDriverRating.setText(rideRequest.driverRating);

                tvDistance.setText(rideRequest.distance+" Km");
                tvFare.setText(rideRequest.fare+" Tk.");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowRiderRequest.this, "Error happened in fetching ride data!", Toast.LENGTH_SHORT).show();
            }

        });


        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RideRequest rideRequest = dataSnapshot.getValue(RideRequest.class);

                tvRiderName.setText(rideRequest.riderName);
                tvRiderPhone.setText(rideRequest.riderPhone);
                tvRiderNid.setText(rideRequest.riderNid);
                tvRiderRating.setText(rideRequest.riderRating);

                tvDriverName.setText(rideRequest.driverName);
                tvDriverPhone.setText(rideRequest.driverPhone);
                tvDriverNid.setText(rideRequest.driverNid);
                tvDriverRating.setText(rideRequest.driverRating);

                tvDistance.setText(rideRequest.distance+" Km");
                tvFare.setText(rideRequest.fare+" Tk.");

                int status = Integer.parseInt(rideRequest.rideStatus);
                if(status==1){
                    tvDriverName.setText(rideRequest.driverName);
                    tvDriverPhone.setText(rideRequest.driverPhone);
                    tvDriverNid.setText(rideRequest.driverNid);
                    tvDriverRating.setText(rideRequest.driverRating);

                    tvRideStatus.setText("Ride Started");
                    tvRideStatus.setTextColor(Color.BLUE);
                    driverLayout.setVisibility(View.VISIBLE);
                }
                if(status==2){
                    tvRideStatus.setText("Ride Ended!");
                    tvRideStatus.setTextColor(Color.GREEN);
                    btnRateDriver.setVisibility(View.VISIBLE);
                    rateDriver.setVisibility(View.VISIBLE);
                    //enable rating layout;
                    Toast.makeText(ShowRiderRequest.this, "Please rate the driver", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(ShowRiderRequest.this, "Error happened in updating ride data!", Toast.LENGTH_SHORT).show();

            }
        });

        btnRateDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float value = (rateDriver.getRating()+Float.parseFloat(tvDriverRating.getText().toString()))/2;

                //updating rating
                final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Driver")
                        .child(tvDriverPhone.getText().toString()).child("rating");
                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
//                        String count = snapshot.getValue(String.class);
//                        float countNum = Float.parseFloat(count);
                        rootRef.setValue(String.valueOf(value));
                        Toast.makeText(ShowRiderRequest.this, "Driver rated! \nThanks", Toast.LENGTH_LONG).show();
                        //sending data to rider activity

                         //sending data to rider activity

                        Intent i = new Intent(ShowRiderRequest.this, RiderMainAcitivity.class);
                        i.putExtra("riderName", tvRiderName.getText().toString());
                        i.putExtra("riderPhone", tvRiderPhone.getText().toString());
                        startActivity(i);
                        finish();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ShowRiderRequest.this, "Error rating driver!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
