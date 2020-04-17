package com.example.houserentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.msg91.sendotpandroid.library.SendOtpVerification;
import com.msg91.sendotpandroid.library.VerificationListener;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity  {


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private FirebaseAuth mAuth;

    private User user;

    DatabaseReference dbArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        final DatabaseManager db = new DatabaseManager(this);

        final EditText usernameEditText = findViewById(R.id.editText);
        final EditText passwordEditText = findViewById(R.id.editText2);

        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        mAuth = FirebaseAuth.getInstance();

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(username.equalsIgnoreCase("admin@hrental.ca")) {

                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, AdminWelcomeActivity.class);

                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }
                else {

                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MainActivity.this, UserWelcomeActivity.class);

                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                    Intent intent = new Intent(MainActivity.this, UserRegistrationActivity.class);
                    startActivity(intent);

            }
        });
    }

}
