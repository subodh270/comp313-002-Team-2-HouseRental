package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class AdvertisementDetailActivity extends AppCompatActivity {

    String address ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement_detail);

        TextView textViewTitle = findViewById(R.id.textViewTitle);
        TextView textViewShortDesc = findViewById(R.id.textViewShortDesc);
        TextView textViewRating = findViewById(R.id.textViewRating);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        ImageView imageView = findViewById(R.id.imageView);

        address = getIntent().getStringExtra("city");

        textViewTitle.setText(getIntent().getStringExtra("title"));
        textViewShortDesc.setText(getIntent().getStringExtra("description"));
        textViewRating.setText(getIntent().getStringExtra("city"));
        textViewPrice.setText(getIntent().getStringExtra("price"));
//        textViewTitle.setText(getIntent().getStringExtra("image_url"));
        Glide.with(this)
                .asBitmap()
                .load(getIntent().getStringExtra("image_url"))
                .into(imageView);

        Button map_navigation = (Button) findViewById(R.id.button12);

        map_navigation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(AdvertisementDetailActivity.this, MapActivity.class);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });


    }
}
