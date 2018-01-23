package com.example.asifsabir.rideshareapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by asifsabir on 1/21/18.
 */

public class RiderMainAcitivity extends AppCompatActivity {
    TextView tvRiderName, tvRiderMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_main);
        tvRiderName = (TextView) findViewById(R.id.tv_rider_name);
        tvRiderMobile = (TextView) findViewById(R.id.tv_rider_mobile);

        //retrieving phone data
        String riderName = getIntent().getExtras().getString("riderName", null);
        String riderPhone = getIntent().getExtras().getString("riderPhone", null);

        tvRiderName.setText(riderName);
        tvRiderMobile.setText(riderPhone);

    }
}