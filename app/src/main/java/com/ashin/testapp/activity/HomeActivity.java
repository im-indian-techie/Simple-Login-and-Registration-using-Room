package com.ashin.testapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ashin.testapp.Adapter.UsersRecyclerAdapter;
import com.ashin.testapp.R;
import com.ashin.testapp.dao.UserDao;
import com.ashin.testapp.database.UserDatabase;
import com.ashin.testapp.databinding.ActivityHomeBinding;
import com.ashin.testapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements UsersRecyclerAdapter.ItemCallback{
    private Activity activity=this;
    private ActivityHomeBinding binding;
    private UserDao userDao;
    private UserDatabase dataBase;
    private List<User> listUsers;
    private UsersRecyclerAdapter usersRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        initObjects();
        initViews();
        initListers();
    }

    private void initListers() {
        binding.ivProfilelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(activity,ProfileActivity.class));
            }
        });
    }

    private void initViews() {
    }

    private void initObjects() {
        dataBase = Room.databaseBuilder(this, UserDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();

        userDao = dataBase.getUserDao();
        listUsers = new ArrayList<>();


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.rvUserList.setLayoutManager(mLayoutManager);
        binding.rvUserList.setItemAnimator(new DefaultItemAnimator());
        binding.rvUserList.setHasFixedSize(true);
        getUsersList();
    }

    private void getUsersList() {
      listUsers.clear();
      listUsers=userDao.getAll();
      usersRecyclerAdapter=new UsersRecyclerAdapter(listUsers,this);
      binding.rvUserList.setAdapter(usersRecyclerAdapter);
    }

    @Override
    public void getUserName(String name) {
        binding.tvClickedUser.setText(name);
    }
}