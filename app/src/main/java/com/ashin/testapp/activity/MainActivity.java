package com.ashin.testapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ashin.testapp.R;
import com.ashin.testapp.dao.UserDao;
import com.ashin.testapp.database.UserDatabase;
import com.ashin.testapp.databinding.ActivityMainBinding;
import com.ashin.testapp.model.User;
import com.ashin.testapp.utils.PreferenceManager;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Activity activity=this;
    private ActivityMainBinding binding;
    String email,password;
    UserDao userDao;
    UserDatabase dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initObjects();
        initListeners();


    }

    private void initObjects() {
        dataBase = Room.databaseBuilder(this, UserDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();

        userDao = dataBase.getUserDao();
    }

    private void initListeners() {
        binding.tvSignuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity,SignUpActivity.class));
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             userLogin();
            }
        });
    }



    public void userLogin()
    {
        binding.etEmail.setError(null);
        binding.etPassword.setError(null);
        boolean cancel = false;
        View focusView = null;
        email=binding.etEmail.getText().toString().trim();
        password=binding.etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            binding.etEmail.setError(getString(R.string.error_field_required));
            focusView=binding.etEmail;
            cancel=true;
        }
        if(TextUtils.isEmpty(password))
        {
            binding.etPassword.setError(getString(R.string.error_field_required));
            focusView=binding.etPassword;
            cancel=true;
        }
        if (cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            Log.d("email",email+password);
            User user = userDao.getUser(email, password);
            if (user != null) {
                PreferenceManager.getInstance().setUserDetails(activity,user);
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }else{
                Toast.makeText(MainActivity.this, "User not valid", Toast.LENGTH_SHORT).show();
            }
        }

    }

}