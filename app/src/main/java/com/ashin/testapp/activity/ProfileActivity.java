package com.ashin.testapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.ashin.testapp.databinding.ActivityProfileBinding;
import com.ashin.testapp.model.User;
import com.ashin.testapp.utils.PreferenceManager;

import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ProfileActivity extends AppCompatActivity {
    private Activity activity=this;
    private ActivityProfileBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initListeners();
        initViews();
    }

    private void initViews() {
        User user= PreferenceManager.getInstance().getUserDetails(activity);
        binding.etEmail.setText(user.getEmail());
        binding.etName.setText(user.getName());
        binding.etPlace.setText(user.getPlace());
        binding.etPhone.setText(user.getPhone());

    }

    private void initListeners() {
        binding.ivEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(ContextCompat.checkSelfPermission(activity,READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
             {
               openGallery();
             }
             else
             {
                 ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
             }
            }
        });
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            binding.ivProfileImage.setImageURI(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {

                    ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
                }
                return;
            }
        }
    }
}