package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserWelcomeActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_welcome);


        firebaseAuth = FirebaseAuth.getInstance();



        Button postAd = (Button) findViewById(R.id.button9);
        Button viewAds = (Button) findViewById(R.id.button10);
        Button myAds = (Button) findViewById(R.id.button15);
        Button profile = (Button) findViewById(R.id.button13);
        Button logOut = (Button) findViewById(R.id.button11);

        viewAds.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                    Intent intent = new Intent(UserWelcomeActivity.this, ViewAdvertisementActivity.class);
                    intent.putExtra("viewAds", "allAds");
                    startActivity(intent);

            }
        });

        postAd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(UserWelcomeActivity.this, PostAdvertisementActivity.class);
                startActivity(intent);

            }
        });

        myAds.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(UserWelcomeActivity.this, ViewAdvertisementActivity.class);
                intent.putExtra("viewAds", "myAds");
                startActivity(intent);

            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(UserWelcomeActivity.this, UserProfileActivity.class);
                startActivity(intent);

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                firebaseAuth.signOut();

                finish();

                startActivity(new Intent(UserWelcomeActivity.this, MainActivity.class));

            }
        });
    }
}
