package com.example.houserentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditAdvertisementActivity extends AppCompatActivity {

    Advertisement advertisement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_advertisement);

        final TextView textViewTitle = findViewById(R.id.textView2);
        final TextView textViewShortDesc = findViewById(R.id.textView3);
        final TextView textViewCity = findViewById(R.id.textView4);
        final TextView textViewPrice = findViewById(R.id.textView5);
        ImageView imageView = findViewById(R.id.imageView2);
        Button updateAd = (Button) findViewById(R.id.button18);
        Button deleteAd = (Button) findViewById(R.id.button19);

        final String advId = getIntent().getStringExtra("advId");
        final String imgUrl = getIntent().getStringExtra("image_url");
        final DatabaseReference dataAdvertisement =  FirebaseDatabase.getInstance().getReference("uploads").child(advId);
        final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imgUrl);

        dataAdvertisement.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                advertisement = dataSnapshot.getValue(Advertisement.class);

                textViewTitle.setText(advertisement.getTitle());
                textViewShortDesc.setText(advertisement.getShortdesc());
                textViewCity.setText(advertisement.getCity());
                textViewPrice.setText("CAD " + advertisement.getPrice());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
//        textViewTitle.setText(getIntent().getStringExtra("title"));
//        textViewShortDesc.setText(getIntent().getStringExtra("description"));
//        textViewCity.setText(getIntent().getStringExtra("city"));
//        textViewPrice.setText(getIntent().getStringExtra("price"));
//        textViewTitle.setText(getIntent().getStringExtra("image_url"));
        Glide.with(this)
                .asBitmap()
                .load(imgUrl)
                .into(imageView);


        updateAd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditAdvertisementActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_update_advertisement, null);
                dialogBuilder.setView(dialogView);

                Button updateDialog = (Button) dialogView.findViewById(R.id.button20);
                final EditText titleUpdateText = dialogView.findViewById(R.id.editText38);
                final EditText shortDescUpdateText = dialogView.findViewById(R.id.editText39);
                final EditText cityUpdateText = dialogView.findViewById(R.id.editText40);
                final EditText priceUpdateText = dialogView.findViewById(R.id.editText41);

                titleUpdateText.setText(advertisement.getTitle());
                shortDescUpdateText.setText(advertisement.getShortdesc());
                cityUpdateText.setText(advertisement.getCity());
                priceUpdateText.setText(advertisement.getPrice());
                
                final String userId = advertisement.getUserId();

                dialogBuilder.setTitle("Update Post : " + advertisement.getTitle());

                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                updateDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String titleUpdate = titleUpdateText.getText().toString();
                        String shortDescUpdate = shortDescUpdateText.getText().toString();
                        String cityUpdate = cityUpdateText.getText().toString();
                        String priceUpdate = priceUpdateText.getText().toString();

                        if (TextUtils.isEmpty(titleUpdate)){
                            titleUpdateText.setError("Name required");
                        }
                        if (TextUtils.isEmpty(shortDescUpdate)){
                            shortDescUpdateText.setError("Phone number required");
                        }
                        if (TextUtils.isEmpty(shortDescUpdate)){
                            cityUpdateText.setError("Phone number required");
                        }
                        if (TextUtils.isEmpty(shortDescUpdate)){
                            priceUpdateText.setError("Phone number required");
                        }

                        Advertisement advertisementUpdate = new Advertisement(advId, titleUpdate, shortDescUpdate, cityUpdate, priceUpdate, userId, imgUrl);

                        dataAdvertisement.setValue(advertisementUpdate);
                        Toast.makeText(EditAdvertisementActivity.this, "Post Updated Successfully", Toast.LENGTH_LONG).show();

                        // refresh profile page with new data
                        dataAdvertisement.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Advertisement advertisementUpdated = dataSnapshot.getValue(Advertisement.class);

                                textViewTitle.setText(advertisementUpdated.getTitle());
                                textViewShortDesc.setText(advertisementUpdated.getShortdesc());
                                textViewCity.setText(advertisementUpdated.getCity());
                                textViewPrice.setText(advertisementUpdated.getPrice());
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

        deleteAd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditAdvertisementActivity.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Post will be permanently deleted ");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

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
                    dataAdvertisement.removeValue();

                    Toast.makeText(EditAdvertisementActivity.this, "Post deleted successfully", Toast.LENGTH_LONG).show();
                    finish();
//                    Intent intent = new Intent(EditAdvertisementActivity.this, ViewAdvertisementActivity.class);
                        Intent intent = new Intent(EditAdvertisementActivity.this, UserWelcomeActivity.class);
                    intent.putExtra("viewAds", "myAds");
                    startActivity(intent);


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
    }
}
