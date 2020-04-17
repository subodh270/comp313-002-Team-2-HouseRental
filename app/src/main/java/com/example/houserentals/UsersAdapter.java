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

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_users, null);
        return new UsersAdapter.UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersAdapter.UsersViewHolder holder, int position) {

        final User user = userList.get(position);


        holder.textViewProfileName.setText(user.getName());
        holder.textViewProfileEmail.setText(user.getEmail());


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;

            }
        });
    }


    @Override
    public int getItemCount() {
        return userList.size();
    }


    class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView textViewProfileName, textViewProfileEmail;

        LinearLayout parentLayout;

        public UsersViewHolder(View itemView) {
            super(itemView);

            textViewProfileName = itemView.findViewById(R.id.textViewProfileName);
            textViewProfileEmail = itemView.findViewById(R.id.textViewProfileEmail);

            parentLayout = itemView.findViewById(R.id.users_layout);
        }
    }
}
