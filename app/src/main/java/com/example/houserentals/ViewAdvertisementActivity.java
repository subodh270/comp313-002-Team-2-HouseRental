package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAdvertisementActivity extends AppCompatActivity {

    //the recyclerview
    RecyclerView recyclerView;

    //adapter object
    private AdvertisementAdapter adapter;

    //database reference
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    //a list to store all the products
    List<Advertisement> advertisementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_advertisement);

        Button open = (Button) findViewById(R.id.openPopup);
        final  RelativeLayout pop = (RelativeLayout)findViewById(R.id.sortByRange);

        final String viewAds = getIntent().getStringExtra("viewAds");

        Button rangeClick1 = (Button) findViewById(R.id.rangeClick1);
        open.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                pop.setVisibility(View.VISIBLE);
            }
        });

        rangeClick1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int minPrice = 0;
                int maxPrice = 0;
                EditText minET = (EditText) findViewById(R.id.min);
                EditText maxET = (EditText) findViewById(R.id.max);
                try{
                    minPrice = Integer.parseInt(minET.getText().toString());
                }catch (Exception e){

                }
                try{
                    maxPrice = Integer.parseInt(maxET.getText().toString());
                }catch (Exception e){

                }
                filterByPrice(minPrice, maxPrice, viewAds);
                pop.setVisibility(View.GONE);
            }
        });


        progressDialog = new ProgressDialog(this);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the advertisementlist
        advertisementList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
        mAuth = FirebaseAuth.getInstance();


        if (viewAds.equals("myAds")){

            //adding an event listener to fetch values
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //dismissing the progress dialog
                    progressDialog.dismiss();

                    //iterating through all the values in database
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Advertisement advertisement = postSnapshot.getValue(Advertisement.class);
//                    Upload upload = postSnapshot.getValue(Upload.class);
                        String userId = advertisement.getUserId();
                        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        if( userId !=null && userId.equals(currentUserId)){
                            advertisementList.add(advertisement);
                        }

                    }
                    //creating adapter
                    adapter = new AdvertisementAdapter(getApplicationContext(), advertisementList, viewAds);

                    //adding adapter to recyclerview
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });
        }
        else {

            //adding an event listener to fetch values
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //dismissing the progress dialog
                    progressDialog.dismiss();

                    //iterating through all the values in database
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Advertisement advertisement = postSnapshot.getValue(Advertisement.class);
//                    Upload upload = postSnapshot.getValue(Upload.class);
                        advertisementList.add(advertisement);
                    }
                    //creating adapter
                    adapter = new AdvertisementAdapter(getApplicationContext(), advertisementList, viewAds);

                    //adding adapter to recyclerview
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });

        }

//        //creating recyclerview adapter
//        AdvertisementAdapter adapter = new AdvertisementAdapter(this, advertisementList);
//
//        //setting adapter to recyclerview
//        recyclerView.setAdapter(adapter);
    }
    private void filterByPrice(int minPrice, int maxPrice, String viewAds){
        List<Advertisement> sortedAdvertisementList = new ArrayList<>();
        List<Advertisement> finalSortedAdvertisementList = new ArrayList<>();
        if(minPrice==0 && maxPrice ==0){
            sortedAdvertisementList =  new ArrayList<>(advertisementList);
        }else {
            for (int i = 0; i < advertisementList.size(); i++) {
                Advertisement adv = advertisementList.get(i);
                if (Float.parseFloat(adv.getPrice()) >= minPrice && Float.parseFloat(adv.getPrice()) <= maxPrice) {
                    sortedAdvertisementList.add(adv);
                }
            }
        }
        CheckBox bhkOne = (CheckBox) findViewById(R.id.bhkOne);
        CheckBox bhkTwo = (CheckBox) findViewById(R.id.bhkTwo);
        if(!bhkOne.isChecked() && !bhkTwo.isChecked()){
            finalSortedAdvertisementList =  new ArrayList<>(sortedAdvertisementList);
        }else {
            for (int i = 0; i < sortedAdvertisementList.size(); i++) {
                Advertisement adv = sortedAdvertisementList.get(i);
                if (bhkOne.isChecked() && adv.getTitle().contains("1")) {
                    finalSortedAdvertisementList.add(adv);
                } else if (bhkTwo.isChecked() && adv.getTitle().contains("2")) {
                    finalSortedAdvertisementList.add(adv);
                }
            }
        }
        adapter = new AdvertisementAdapter(getApplicationContext(), finalSortedAdvertisementList, viewAds);

        //adding adapter to recyclerview
        recyclerView.setAdapter(adapter);


    }
}