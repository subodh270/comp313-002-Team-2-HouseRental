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
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        mAuth = FirebaseAuth.getInstance();

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                RadioButton radioButton = (RadioButton) findViewById(R.id.radioButton);
                RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);

                if(radioButton.isChecked()) {

                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                       Intent intent = new Intent(MainActivity.this, UserWelcomeActivity.class);
////                                        intent.putExtra("otp", generatedPassword);
                                       startActivity(intent);
//  ----------------------------------------------------------------------------------------------
//  uncomment this part for sms working
//                                        ProgressBar pb = findViewById(R.id.progress);
//                                        pb.setVisibility(View.VISIBLE);
//                                        LinearLayout root = findViewById(R.id.root);
//                                        root.setVisibility(View.GONE);
//
//                                        Query query = FirebaseDatabase.getInstance().getReference("Users")
//                                                .orderByChild("email")
//                                                .equalTo(username);
//                                        query.addListenerForSingleValueEvent(valueEventListener);
//   -------------------------------------------------------------------------------------------------------

//     ----------------------------------------------------------------------------------------------------------
//                                        Random random = new Random();
//                                        String generatedPassword = String.format("%04d", random.nextInt(10000));
//
//                                        try{
//                                            SmsManager smgr = SmsManager.getDefault();
//                                            smgr.sendTextMessage("5554",null,generatedPassword,null,null);
//                                            Toast.makeText(MainActivity.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();
//                                        }
//                                        catch (Exception e){
//                                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }

//                                        Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.GONE);
//    -----------------------------------------------------------------------------------------------------------------------


                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

//                    if(db.checkUser("User", username, password)) {
//                        SharedPreferences myPreference = getSharedPreferences("MySharedPreferences", 0);
//                        SharedPreferences.Editor prefEditor = myPreference.edit();
//                        prefEditor.putString("Username", username);
//                        prefEditor.commit();
//
//                        Intent intent = new Intent(MainActivity.this, UserWelcomeActivity.class);
//                        startActivity(intent);
//                    }
//                    else {
//                        Toast.makeText(MainActivity.this, "Invalid User Username/Password", Toast.LENGTH_SHORT).show();
//                    }

                }
                else if (radioButton2.isChecked()) {
                    if(db.checkUser("Admin", username, password)) {
                        SharedPreferences myPreference = getSharedPreferences("MySharedPreferences", 0);
                        SharedPreferences.Editor prefEditor = myPreference.edit();
                        prefEditor.putString("Username", username);
                        prefEditor.commit();

                        Intent intent = new Intent(MainActivity.this, UserWelcomeActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Invalid Admin Username/Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Select User/Admin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                RadioButton radioButton = (RadioButton) findViewById(R.id.radioButton);
                RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);

                if(radioButton.isChecked()) {
                    Intent intent = new Intent(MainActivity.this, UserRegistrationActivity.class);
                    startActivity(intent);
                }
                else if (radioButton2.isChecked()) {
                    Intent intent = new Intent(MainActivity.this, AdminRegistrationActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Select User/Admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//------------------------------------------------------------------------------------------------------------------
// uncomment this part for sms working
//    @Override
//    protected void onResume() {
//        super.onResume();
//        ProgressBar pb = findViewById(R.id.progress);
//        pb.setVisibility(View.GONE);
//        LinearLayout root = findViewById(R.id.root);
//        root.setVisibility(View.VISIBLE);
//    }
//
//    ValueEventListener valueEventListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            if (dataSnapshot.exists()) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    user = snapshot.getValue(User.class);
//                    openOtpScreen(user.getPhone().toString());
//                }
//            }
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//
//            ProgressBar pb = findViewById(R.id.progress);
//            pb.setVisibility(View.GONE);
//            LinearLayout root = findViewById(R.id.root);
//            root.setVisibility(View.VISIBLE);
//        }
//    };
//
//    public void sendSMS(View view){
//
//        SmsManager mySmsManager = SmsManager.getDefault();
//        mySmsManager.sendTextMessage("5544",null, "1234", null, null);
//    }
//
//
//    public void openOtpScreen(String phoneNumber){
//        Intent intent = new Intent(MainActivity.this, OtpActivity.class);
//                                        intent.putExtra("phone", phoneNumber);
//                                        startActivity(intent);
//    }
// ----------------------------------------------------------------------------------------------------------



// -----------------------------------------------------------------------------------
// this way sms do not work
//    protected void sendSMSMessage() {
//
//
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.SEND_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.SEND_SMS)) {
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.SEND_SMS},
//                        MY_PERMISSIONS_REQUEST_SEND_SMS);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage("5554", null, "1234", null, null);
//                    Toast.makeText(getApplicationContext(), "OTP sent.",
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "OTP failed, please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }
//
//    }
//-----------------------------------------------------------------------------------------------------------------
}
