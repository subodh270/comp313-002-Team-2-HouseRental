package com.example.houserentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;

    private FirebaseAuth mAuth;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();
        final EditText phone = findViewById(R.id.editText28);
        final EditText otp = findViewById(R.id.editText27);
        Button signIn = findViewById(R.id.button7);
        Button generateOtp = findViewById(R.id.button8);
        String phoneNumber = getIntent().getStringExtra("phone");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+" + phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);

//        StartFirebaseLogin();
//        generateOtp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String phoneNumber=phone.getText().toString();
//                PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                        phoneNumber,                     // Phone number to verify
//                        60,                           // Timeout duration
//                        TimeUnit.SECONDS,                // Unit of timeout
//                        OtpActivity.this,        // Activity (for callback binding)
//                        mCallback);                      // OnVerificationStateChangedCallbacks
//            }
//        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText otp = findViewById(R.id.editText27);
                //verifying the code
                verifyVerificationCode(otp.getText().toString());
//                if(otp.getText().toString().equals(getIntent().getStringExtra("otp")))
//                {
//                    startActivity(new Intent(OtpActivity.this,UserWelcomeActivity.class));
//                }
//                else
//                {
//                    Toast.makeText(OtpActivity.this,"Incorrect OTP", Toast.LENGTH_SHORT).show();
//                }
//                String otpText=otp.getText().toString();
//                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otpText);
//                SigninWithPhone(credential);
            }
        });
    }

//    private void SigninWithPhone(PhoneAuthCredential credential) {
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            startActivity(new Intent(OtpActivity.this,UserWelcomeActivity.class));
//                            finish();
//                        } else {
//                            Toast.makeText(OtpActivity.this,"Incorrect OTP", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//
//    private void StartFirebaseLogin() {
//        auth = FirebaseAuth.getInstance();
//        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                Toast.makeText(OtpActivity.this,"verification completed",Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                Toast.makeText(OtpActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
//                verificationCode = s;
//                Toast.makeText(OtpActivity.this,"Code sent",Toast.LENGTH_SHORT).show();
//            }
//        };
//    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {

                EditText otp = findViewById(R.id.editText27);
                otp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OtpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            Intent intent = new Intent(OtpActivity.this, UserWelcomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }


                            Toast.makeText(OtpActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
