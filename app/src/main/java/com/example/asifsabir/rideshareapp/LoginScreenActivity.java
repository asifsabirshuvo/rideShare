package com.example.asifsabir.rideshareapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by asifsabir on 1/21/18.
 */


public class LoginScreenActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button loginBtn,riderRegBtn,driverRegBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        riderRegBtn = (Button)findViewById(R.id.button_rider_reg);
        driverRegBtn = (Button)findViewById(R.id.button_driver_reg);

        addListenerOnButton();

        riderRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RiderRegistrationActivity.class));
            }
        });
        driverRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DriverRegistrationActivity.class));
            }
        });
    }



    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radio);
        loginBtn = (Button) findViewById(R.id.button_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);

                Toast.makeText(getApplicationContext(),
                        radioButton.getText(), Toast.LENGTH_SHORT).show();

            }

        });

    }
}