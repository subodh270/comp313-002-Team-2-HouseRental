package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class AdminWelcomeActivity extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();


        Button viewAds = (Button) findViewById(R.id.button21);
        Button viewUsers = (Button) findViewById(R.id.button22);
        Button logOut = (Button) findViewById(R.id.button23);

        viewAds.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(AdminWelcomeActivity.this, ViewAdvertisementActivity.class);
                intent.putExtra("viewAds", "allAdsAdmin");
                startActivity(intent);

            }
        });

        viewUsers.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //starting login activity
                startActivity(new Intent(AdminWelcomeActivity.this, MainActivity.class));

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(AdminWelcomeActivity.this, MainActivity.class));

            }
        });

    }
}
