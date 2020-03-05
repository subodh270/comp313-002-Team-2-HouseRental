package com.example.houserentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Console;

public class UserProfileActivity extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Button delete = (Button) findViewById(R.id.button14);
        Button update = (Button) findViewById(R.id.button16);
        final EditText nameEditText = findViewById(R.id.editText30);
        final EditText emailEditText = findViewById(R.id.editText35);
        final EditText phoneEditText = findViewById(R.id.editText36);

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = firebaseUser.getUid();
        final DatabaseReference dataUser =  FirebaseDatabase.getInstance().getReference("Users").child(uid);
        final DatabaseReference dataAdvertisement =  FirebaseDatabase.getInstance().getReference("uploads");



        dataUser.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);

                nameEditText.setText(user.getName());
                emailEditText.setText(user.getEmail());
                phoneEditText.setText(user.getPhone().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UserProfileActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_update_profile, null);
                dialogBuilder.setView(dialogView);

                Button updateDialog = (Button) dialogView.findViewById(R.id.button17);
                final EditText nameUpdateText = dialogView.findViewById(R.id.editText34);
                final EditText phoneUpdateText = dialogView.findViewById(R.id.editText37);

                nameUpdateText.setText(user.getName());
                phoneUpdateText.setText(user.getPhone().toString());

                dialogBuilder.setTitle("Update Profile : " + user.getName().substring(0, 1).toUpperCase() + user.getName().substring(1));

                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                updateDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nameUpdate = nameUpdateText.getText().toString();
                        String phoneUpdate = phoneUpdateText.getText().toString();

                        if (TextUtils.isEmpty(nameUpdate)){
                            nameUpdateText.setError("Name required");
                        }
                        if (TextUtils.isEmpty(phoneUpdate)){
                            nameUpdateText.setError("Phone number required");
                        }

                        User userUpdate = new User (uid, user.getEmail(), nameUpdate, Long.parseLong(phoneUpdate));
                        dataUser.setValue(userUpdate);
                        Toast.makeText(UserProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_LONG).show();

                        // refresh profile page with new data
                        dataUser.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User userUpdated = dataSnapshot.getValue(User.class);

                                nameEditText.setText(userUpdated.getName());
                                emailEditText.setText(userUpdated.getEmail());
                                phoneEditText.setText(userUpdated.getPhone().toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                throw databaseError.toException();
                            }
                        });
                        alertDialog.dismiss();
                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(UserProfileActivity.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Account will be permanently removed from the system. All your Ad postings will be deleted");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        // delete user information data from firebase database
                        dataUser.removeValue();

                        // delete every post of user from database and corresponding image from firebase storage
                        Query queryDeleteAdvertisementsOfUser = dataAdvertisement.orderByChild("userId").equalTo(uid);
                        queryDeleteAdvertisementsOfUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                    Advertisement advertisement = postSnapshot.getValue(Advertisement.class);

                                    // delete image from firebase storage of each post
                                    String imgUrl = advertisement.getUrl();
                                    storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imgUrl);
                                    storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // File deleted successfully
//                                            Log.d(TAG, "onSuccess: deleted file");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Uh-oh, an error occurred!
//                                            Log.d(TAG, "onFailure: did not delete file");
                                        }
                                    });

                                    // delete post of user
                                    postSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
//                                          Log.e(TAG, "onCancelled", databaseError.toException());
                            }
                        });

                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

//                                    delete profile info and ads posted and images stored before deleting the firebaseAuth user
//                                    dataUser.removeValue();
//
//                                    Query queryDeleteAdvertisementsOfUser = dataAdvertisement.orderByChild("userId").equalTo(uid);
//                                    queryDeleteAdvertisementsOfUser.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                                                postSnapshot.getRef().removeValue();
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
////                                          Log.e(TAG, "onCancelled", databaseError.toException());
//                                        }
//                                    });

                                    Toast.makeText(UserProfileActivity.this, "Account deleted successfully", Toast.LENGTH_LONG).show();
//                                  Log.d(TAG, "User account deleted.");
                                    Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(UserProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();

            }
        });

//        delete.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(UserProfileActivity.this, "Account deleted successfully", Toast.LENGTH_LONG).show();
////                                  Log.d(TAG, "User account deleted.");
//                                    Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                }
//                                else {
//                                    Toast.makeText(UserProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//
//            }
//        });
    }
}
