package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewUsersActivity extends AppCompatActivity {

    //the recyclerview
    RecyclerView recyclerView;

    //adapter object
    private UsersAdapter adapter;

    //database reference
    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    //a list to store all the products
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        progressDialog = new ProgressDialog(this);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the advertisementlist
        userList = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();



       //adding an event listener to fetch values
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //dismissing the progress dialog
                    progressDialog.dismiss();

                    //iterating through all the values in database
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        User user = postSnapshot.getValue(User.class);
//                    Upload upload = postSnapshot.getValue(Upload.class);
                        if (! user.getEmail().equalsIgnoreCase("admin@hrental.ca"))
                        {
                            userList.add(user);
                        }

                    }
                    //creating adapter
                    adapter = new UsersAdapter(getApplicationContext(), userList);

                    //adding adapter to recyclerview
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });


    }
}
