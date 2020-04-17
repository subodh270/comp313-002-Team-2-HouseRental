package com.example.houserentals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdminRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        final DatabaseManager db = new DatabaseManager(this);
        final String fields[] = {"employeeId", "username", "password", "firstName","lastName", "phone"};
        final String record[] = new String[6];

        final EditText employeeIdEditText = findViewById(R.id.editText12);
        final EditText usernameEditText = findViewById(R.id.editText13);
        final EditText passwordEditText = findViewById(R.id.editText14);
        final EditText firstNameEditText = findViewById(R.id.editText15);
        final EditText lastNameEditText = findViewById(R.id.editText16);
        final EditText phoneEditText = findViewById(R.id.editText17);


        Button registerAdmin = (Button) findViewById(R.id.button4);

        registerAdmin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String employeeId = employeeIdEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();

                record[0]= employeeId;
                record[1]= username;
                record[2]= password;
                record[3]= firstName;
                record[4]= lastName;
                record[5]= phone;
                Log.d("Username: ", record[1]);

                ContentValues values = new ContentValues();

                db.addRecord(values, "Admin", fields, record);

                Intent intent = new Intent(AdminRegistrationActivity.this, AdminInformationActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}
