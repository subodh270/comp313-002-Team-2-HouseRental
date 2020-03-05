package com.example.houserentals;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the Advertisements in a list
    private List<Advertisement> AdvertisementList;

    private String adsType;

    //getting the context and Advertisement list with constructor
    public AdvertisementAdapter(Context mCtx, List<Advertisement> AdvertisementList, String adsType) {
        this.mCtx = mCtx;
        this.AdvertisementList = AdvertisementList;
        this.adsType = adsType;
    }

    @Override
    public AdvertisementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_ads, null);
        return new AdvertisementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdvertisementViewHolder holder, int position) {
        //getting the Advertisement of the specified position
        final Advertisement Advertisement = AdvertisementList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(Advertisement.getTitle());
        holder.textViewShortDesc.setText(Advertisement.getShortdesc());
        holder.textViewRating.setText(String.valueOf(Advertisement.getCity()));
        holder.textViewPrice.setText(String.valueOf(Advertisement.getPrice()));

//        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(Advertisement.getImage()));


//        Upload upload = uploads.get(position);
//
//        holder.textViewName.setText(upload.getName());
//
//        Glide.with(mCtx).load(Advertisement.getUrl()).into(holder.imageView);

        Glide.with(mCtx)
                .asBitmap()
                .load(Advertisement.getUrl())
                .into(holder.imageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));

//                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();

                Intent intent;
                if(adsType.equals("myAds")) {
                    intent = new Intent(mCtx, EditAdvertisementActivity.class);
                }
                else {
                    intent = new Intent(mCtx, AdvertisementDetailActivity.class);
                }

                intent.putExtra("advId", Advertisement.getAdvertisementId());
                intent.putExtra("title", Advertisement.getTitle());
                intent.putExtra("description", Advertisement.getShortdesc());
                intent.putExtra("city", Advertisement.getCity());
                intent.putExtra("price", Advertisement.getPrice());
                intent.putExtra("userId", Advertisement.getUserId());
                intent.putExtra("image_url", Advertisement.getUrl());
                mCtx.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return AdvertisementList.size();
    }


    class AdvertisementViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;
        LinearLayout parentLayout;

        public AdvertisementViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
