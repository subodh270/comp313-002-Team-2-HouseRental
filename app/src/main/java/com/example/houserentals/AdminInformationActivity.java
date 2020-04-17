package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class AdminInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_information);

        final DatabaseManager db = new DatabaseManager(this);
        Button adminDone = (Button) findViewById(R.id.button6);

        final EditText employeeIdEditText = findViewById(R.id.editText22);
        final EditText firstNameEditText = findViewById(R.id.editText23);
        final EditText lastNameEditText = findViewById(R.id.editText24);
        final EditText phoneEditText = findViewById(R.id.editText25);

        Intent intent = getIntent();
        List table = db.getUser("Admin", intent.getStringExtra("username"));

        for (Object o : table) {
            ArrayList row = (ArrayList)o;

            employeeIdEditText.setText(row.get(0).toString());
            firstNameEditText.setText(row.get(3).toString());
            lastNameEditText.setText(row.get(4).toString());
            phoneEditText.setText(row.get(5).toString());
        }

        adminDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(AdminInformationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
