package com.ugtechie.agribuyer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.ugtechie.agribuyer.R;

public class TopUpActivity extends AppCompatActivity {
    private static final String TAG = "TopUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        //Initializing the toolbar
        Toolbar mActionBarToolbar = findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("Top up");
    }
}