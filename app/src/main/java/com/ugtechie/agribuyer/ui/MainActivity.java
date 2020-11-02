package com.ugtechie.agribuyer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ugtechie.agribuyer.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button buttonLogin, buttonSignUp;
    private ConstraintLayout firstActivity;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Setting up widgets
        buttonLogin = findViewById(R.id.button_login_activity);
        buttonSignUp = findViewById(R.id.button_sign_up);
        firstActivity = findViewById(R.id.First_activity);

        //initializing firebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Check for Internet Connection
        if (isConnected()) {
            // Snackbar snackbar = Snackbar.make(firstActivity, "Internet Connection", Snackbar.LENGTH_LONG);
            // snackbar.show();

        } else {
            Snackbar snackbar = Snackbar.make(firstActivity, "No Internet Connection", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Check if user already signed in
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}
