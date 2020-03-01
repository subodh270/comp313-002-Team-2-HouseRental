package com.example.houserentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        final DatabaseManager db = new DatabaseManager(this);
        final String fields[] = {"emailId", "username", "password", "firstName","lastName", "phone", "address", "city", "postalCode"};
        final String record[] = new String[9];

        final EditText emailEditText = findViewById(R.id.editText4);
        final EditText usernameEditText = findViewById(R.id.editText5);
        final EditText passwordEditText = findViewById(R.id.editText6);
        final EditText nameEditText = findViewById(R.id.editText7);
        final EditText lastNameEditText = findViewById(R.id.editText8);
        final EditText phoneEditText = findViewById(R.id.editText3);
        final EditText addresseEditText = findViewById(R.id.editText9);
        final EditText cityEditText = findViewById(R.id.editText10);
        final EditText postalCodeEditText = findViewById(R.id.editText11);

        Button registerUser = (Button) findViewById(R.id.button);

        mAuth = FirebaseAuth.getInstance();

//        findViewById(R.id.button).setOnClickListener(this);
        registerUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String email = emailEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                final  String name = nameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                final String phone = phoneEditText.getText().toString();
                String address = addresseEditText.getText().toString();
                String city = cityEditText.getText().toString();
                String postalCode = postalCodeEditText.getText().toString();

                record[0]= email;
                record[1]= username;
                record[2]= password;
                record[3]= name;
                record[4]= lastName;
                record[5]= phone;
                record[6]= address;
                record[7]= city;
                record[8]= postalCode;
                Log.d("Username: ", record[1]);
                //populate the row with some values
                ContentValues values = new ContentValues();
                for (int i=1;i<record.length;i++)
                values.put(fields[i],record[i]);
                //add the row to the database
                db.addRecord(values, "User", fields, record);


                if (name.isEmpty()) {
                    nameEditText.setError(getString(R.string.input_error_name));
                    nameEditText.requestFocus();
                    return;
                }

                if (email.isEmpty()) {
                    emailEditText.setError(getString(R.string.input_error_email));
                    emailEditText.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError(getString(R.string.input_error_email_invalid));
                    emailEditText.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    passwordEditText.setError(getString(R.string.input_error_password));
                    passwordEditText.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    passwordEditText.setError(getString(R.string.input_error_password_length));
                    passwordEditText.requestFocus();
                    return;
                }

                if (phone.isEmpty()) {
                    phoneEditText.setError(getString(R.string.input_error_phone));
                    phoneEditText.requestFocus();
                    return;
                }

//                if (phone.length() != 10) {
//                    phoneEditText.setError(getString(R.string.input_error_phone_invalid));
//                    phoneEditText.requestFocus();
//                    return;
//                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    User user = new User(
                                            userId,
                                            email,
                                            name,
                                            Long.parseLong(phone)
                                    );

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(UserRegistrationActivity.this, "registration_success", Toast.LENGTH_LONG).show();
                                            } else {
                                                //display a failure message
                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(UserRegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });


//                String id= databaseUsers.push().getKey();
//                User user= new User(email,name,phone);
//                databaseUsers.child(id).setValue(user);
//                Toast.makeText(UserRegistrationActivity.this,"User Added",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(UserRegistrationActivity.this, UserInformationActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (mAuth.getCurrentUser() != null) {
//            //handle the already login user
//        }
//    }
}
