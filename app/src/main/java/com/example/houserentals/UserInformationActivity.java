package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserInformationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);


        final DatabaseManager db = new DatabaseManager(this);
        Button userDone = (Button) findViewById(R.id.button5);
        final EditText emailEditText = findViewById(R.id.editText26);
        final EditText firstNameEditText = findViewById(R.id.editText17);
        final EditText lastNameEditText = findViewById(R.id.editText18);
        final EditText phoneEditText = findViewById(R.id.editText22);
        final EditText addresseEditText = findViewById(R.id.editText19);
        final EditText cityEditText = findViewById(R.id.editText20);
        final EditText postalCodeEditText = findViewById(R.id.editText21);

        Intent intent = getIntent();
        List table = db.getUser("User", intent.getStringExtra("username"));

        for (Object o : table) {
            ArrayList row = (ArrayList)o;

            emailEditText.setText(row.get(0).toString());
            firstNameEditText.setText(row.get(3).toString());
            lastNameEditText.setText(row.get(4).toString());
            phoneEditText.setText(row.get(5).toString());
            addresseEditText.setText(row.get(6).toString());
            cityEditText.setText(row.get(7).toString());
            postalCodeEditText.setText(row.get(8).toString());
        }

        userDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                Intent intent = new Intent(UserInformationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
