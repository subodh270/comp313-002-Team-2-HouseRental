package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class UserWelcomeActivity extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_welcome);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
//        if(firebaseAuth.getCurrentUser() == null){
//            //closing this activity
//            finish();
//            //starting login activity
//            startActivity(new Intent(UserWelcomeActivity.this, MainActivity.class));
//        }

        Button postAd = (Button) findViewById(R.id.button9);
        Button viewAds = (Button) findViewById(R.id.button10);
        Button profile = (Button) findViewById(R.id.button13);
        Button logOut = (Button) findViewById(R.id.button11);

        viewAds.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                    Intent intent = new Intent(UserWelcomeActivity.this, ViewAdvertisementActivity.class);
                    startActivity(intent);

            }
        });

        postAd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(UserWelcomeActivity.this, PostAdvertisementActivity.class);
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

                //logging out the user
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(UserWelcomeActivity.this, MainActivity.class));

            }
        });
    }
}
