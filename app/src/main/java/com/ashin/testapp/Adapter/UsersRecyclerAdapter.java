package com.ashin.testapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ashin.testapp.R;
import com.ashin.testapp.model.User;

import java.util.List;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;
    ItemCallback callback;
    public interface ItemCallback{
        void getUserName(String name);
    }

    public UsersRecyclerAdapter(List<User> listUsers,ItemCallback itemCallback) {
        this.listUsers = listUsers;
        this.callback=itemCallback;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_ticket, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewName.setText(listUsers.get(position).getName());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPhone.setText(listUsers.get(position).getPhone());

        holder.mLinearItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.getUserName(listUsers.get(position).getName());
            }
        });


    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewEmail;
        public TextView textViewPhone;
        public LinearLayout mLinearItem;

        public UserViewHolder(View view) {
            super(view);
            textViewName =  view.findViewById(R.id.tvUsername);
            textViewEmail = view.findViewById(R.id.tvUserEmail);
            textViewPhone =  view.findViewById(R.id.tvUserPhone);
            mLinearItem=view.findViewById(R.id.llitem);
        }
    }


}
