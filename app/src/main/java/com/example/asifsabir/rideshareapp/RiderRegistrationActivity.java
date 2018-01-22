package com.example.asifsabir.rideshareapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by asifsabir on 1/21/18.
 */

public class RiderRegistrationActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    Button registerButton;
    EditText etName, etPhone, etPassword, etNid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_registration);
        getSupportActionBar().setTitle("Rider Registration");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etName = (EditText) findViewById(R.id.et_fullName);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPhone = (EditText) findViewById(R.id.et_mobile);
        etNid = (EditText) findViewById(R.id.et_nid);
        registerButton = (Button) findViewById(R.id.button_register_rider);




        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude = "10", longitude = "10", fullName, password,phoneNumber,nid;

                fullName = etName.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                phoneNumber = etPhone.getText().toString().trim();
                nid = etNid.getText().toString().trim();




                if (fullName.equals("") || password.equals("") ||
                        phoneNumber.equals("") || nid.equals("") |
                        latitude.equals("") || longitude.equals("")) {

                    if (latitude.equals("") || longitude.equals("")) {
                        Toast.makeText(getApplicationContext(), "Error getting location!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    DatabaseReference myRef = database.getReference("Riders").push();
                    RiderReg rider = new RiderReg(fullName, password, phoneNumber,
                            nid,latitude, longitude);
                    myRef.setValue(rider);
                    Toast.makeText(RiderRegistrationActivity.this, "Successful Registration", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RiderRegistrationActivity.this, LoginScreenActivity.class));
                    finish();

                }
            }
        });

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(this, LoginScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityIfNeeded(intent, 0);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
