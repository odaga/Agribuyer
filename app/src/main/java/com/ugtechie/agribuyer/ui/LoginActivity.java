package com.ugtechie.agribuyer.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ugtechie.agribuyer.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView signUpLink;
    private Button buttonLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        //setting up widgets
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonLogin = findViewById(R.id.button_login);
        signUpLink = findViewById(R.id.text_view_SignUp_link);

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the Login process
                login();
            }
        });
    }


    private void login() {
        //Extracting the strings from the editTexts
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //Validating the string before initiating the login process
        if (email.isEmpty()) {
            // Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Please enter your email");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Please enter a valid email");
        } else if (password.isEmpty()) {
            // Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
            editTextPassword.setError("Please enter a valid password");

        } else {
            //Initialise the progress dialog
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Logging in...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    progressDialog.dismiss();

                    if (task.isSuccessful()) {

                        FirebaseUser user = task.getResult().getUser();
                        startHomeActivity(user);
                        finish();

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Could not login", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }


    //start Home Activity in a separate method
    private void startHomeActivity(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
