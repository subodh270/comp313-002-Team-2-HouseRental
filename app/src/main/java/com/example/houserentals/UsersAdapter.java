package com.example.houserentals;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the Advertisements in a list
    private List<User> userList;

    public UsersAdapter(Context mCtx, List<User> userList) {
        this.mCtx = mCtx;
        this.userList = userList;
    }

    @Override
    public UsersAdapter.UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_users, null);
        return new UsersAdapter.UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersAdapter.UsersViewHolder holder, int position) {
        //getting the Advertisement of the specified position
        final User user = userList.get(position);

        //binding the data with the viewholder views
        holder.textViewProfileName.setText(user.getName());
        holder.textViewProfileEmail.setText(user.getEmail());


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
//                if(adsType.equals("allAdsAdmin")) {
//                    intent = new Intent(mCtx, EditAdvertisementActivity.class);
//                }
//                else if(adsType.equals("myAds")) {
//                    intent = new Intent(mCtx, EditAdvertisementActivity.class);
//                }
//                else {
//                    intent = new Intent(mCtx, AdvertisementDetailActivity.class);
//                }

//                intent.putExtra("advId", Advertisement.getAdvertisementId());
//                intent.putExtra("title", Advertisement.getTitle());
//                intent.putExtra("description", Advertisement.getShortdesc());
//                intent.putExtra("city", Advertisement.getCity());
//                intent.putExtra("price", Advertisement.getPrice());
//                intent.putExtra("userId", Advertisement.getUserId());
//                intent.putExtra("image_url", Advertisement.getUrl());
//                mCtx.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }


    class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView textViewProfileName, textViewProfileEmail;
//        ImageView imageView;
        LinearLayout parentLayout;

        public UsersViewHolder(View itemView) {
            super(itemView);

            textViewProfileName = itemView.findViewById(R.id.textViewProfileName);
            textViewProfileEmail = itemView.findViewById(R.id.textViewProfileEmail);

            parentLayout = itemView.findViewById(R.id.users_layout);
        }
    }
}
