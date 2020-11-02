package com.ugtechie.agribuyer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.fragments.HomeFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class UploadProfileImageActivity extends AppCompatActivity {
    private static final String TAG = "UploadProfileImageActivity";

    private CircleImageView ProfileImagePreview;
    private Button chooseProfileImage;
    private TextView skipProfileImageUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile_image);

        //Setting the toolbar with title and widgets
        Toolbar mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Upload a profile Picture");

        ProfileImagePreview = findViewById(R.id.profile_image_upload_preview);
        chooseProfileImage = findViewById(R.id.button_choose_profile_image);
        skipProfileImageUpload = findViewById(R.id.skip_profile_image_upload);

        skipProfileImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


    }
}