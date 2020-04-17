package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdvertisementDetailActivity extends AppCompatActivity {

    String address ;
    String userId ;
    Advertisement advertisement;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement_detail);

        final TextView textViewTitle = findViewById(R.id.textViewTitle);
        final TextView textViewShortDesc = findViewById(R.id.textViewShortDesc);
        final TextView textViewRating = findViewById(R.id.textViewRating);
        final TextView textViewPrice = findViewById(R.id.textViewPrice);
        final TextView textViewContact = findViewById(R.id.textViewContact);
        ImageView imageView = findViewById(R.id.imageView);
        Button map_navigation = (Button) findViewById(R.id.button12);
        Button email = (Button) findViewById(R.id.button25);
        Button call = (Button) findViewById(R.id.button26);


        final String advId = getIntent().getStringExtra("advId");
        final DatabaseReference dataAdvertisement =  FirebaseDatabase.getInstance().getReference("uploads").child(advId);
        address = getIntent().getStringExtra("city");

        dataAdvertisement.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                advertisement = dataSnapshot.getValue(Advertisement.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
        String userId = getIntent().getStringExtra("userId");
        final DatabaseReference dataUser =  FirebaseDatabase.getInstance().getReference("Users").child(userId);
        dataUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                textViewTitle.setText(getIntent().getStringExtra("title"));
                textViewShortDesc.setText(getIntent().getStringExtra("description"));
                textViewRating.setText(getIntent().getStringExtra("city"));
                textViewPrice.setText("CAD "+getIntent().getStringExtra("price"));
                textViewContact.setText("Email : " + user.getEmail() + "\nPhone : " + user.getPhone());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
        address = getIntent().getStringExtra("city");

        Glide.with(this)
                .asBitmap()
                .load(getIntent().getStringExtra("image_url"))
                .into(imageView);


        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String[] addresses = {user.getEmail()};
                composeEmail(addresses, advertisement.getTitle());
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dialPhoneNumber(user.getPhone().toString());
            }
        });

        map_navigation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(AdvertisementDetailActivity.this, MapActivity.class);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });


    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
