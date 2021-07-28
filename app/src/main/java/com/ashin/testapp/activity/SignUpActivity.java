package com.ashin.testapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.ashin.testapp.R;
import com.ashin.testapp.dao.UserDao;
import com.ashin.testapp.database.UserDatabase;
import com.ashin.testapp.databinding.ActivitySignUpBinding;
import com.ashin.testapp.model.User;

public class SignUpActivity extends AppCompatActivity {
    private Activity activity=this;
    private ActivitySignUpBinding binding;
    String email,password,place,name,phone,confirmPass;
    private User user;
    private UserDao userDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        initObjects();
        initListeners();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void initObjects()
    {
        userDao = Room.databaseBuilder(this, UserDatabase.class, "mi-database.db").allowMainThreadQueries()
                .build().getUserDao();
    }
    private void initListeners()
    {
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();
            }
        });
    }
    private void userSignUp()
    {
         binding.etEmail.setError(null);
         binding.etName.setError(null);
         binding.etPlace.setError(null);
         binding.etPhone.setError(null);
         binding.etPassword.setError(null);
         binding.etConfirmPass.setError(null);
         boolean cancel = false;
         View focusView = null;
         email=binding.etEmail.getText().toString().trim();
         password=binding.etPassword.getText().toString().trim();
         place=binding.etPlace.getText().toString().trim();
         name=binding.etName.getText().toString().trim();
         phone=binding.etPhone.getText().toString().trim();
         confirmPass=binding.etConfirmPass.getText().toString().trim();

        if(TextUtils.isEmpty(name))
        {
            binding.etName.setError(getString(R.string.error_field_required));
            focusView=binding.etName;
            cancel=true;
        }
        if(TextUtils.isEmpty(email))
        {
            binding.etEmail.setError(getString(R.string.error_field_required));
            focusView=binding.etEmail;
            cancel=true;
        }
        else if(!isValidEmail(email))
        {
            binding.etEmail.setError(getString(R.string.email_invalid));
            focusView=binding.etEmail;
            cancel=true;
        }
        if(TextUtils.isEmpty(phone))
        {
            binding.etPhone.setError(getString(R.string.error_field_required));
            focusView=binding.etPhone;
            cancel=true;
        }
        if(TextUtils.isEmpty(place))
        {
            binding.etPlace.setError(getString(R.string.error_field_required));
            focusView=binding.etPlace;
            cancel=true;
        }
        if(TextUtils.isEmpty(password))
        {
            binding.etPassword.setError(getString(R.string.error_field_required));
            focusView=binding.etPassword;
            cancel=true;
        }
        if(TextUtils.isEmpty(confirmPass))
        {
            binding.etConfirmPass.setError(getString(R.string.error_field_required));
            focusView=binding.etConfirmPass;
            cancel=true;
        }
        if(!confirmPass.equalsIgnoreCase(password))
        {
            binding.etConfirmPass.setError(getString(R.string.missmatch_pass));
            focusView=binding.etConfirmPass;
            cancel=true;
        }
        if(cancel)
        {
            focusView.requestFocus();
        }
        else
        {
            User user = new User(name,email,password,place,phone);
            userDao.insert(user);
            startActivity(new Intent(activity,MainActivity.class));
            finish();
        }

    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}