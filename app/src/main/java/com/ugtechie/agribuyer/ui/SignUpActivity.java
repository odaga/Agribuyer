package com.ugtechie.agribuyer.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.api.ProductService;
import com.ugtechie.agribuyer.api.UserService;
import com.ugtechie.agribuyer.fragments.HomeFragment;
import com.ugtechie.agribuyer.models.Product;
import com.ugtechie.agribuyer.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth;
    private Button buttonRegister;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhoneNumber;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private String userId;
    private String userDocumentId;
    private TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        //Setting Up widgets
        buttonRegister = findViewById(R.id.button_register);
        editTextFirstName = findViewById(R.id.edit_text_register_first_name);
        editTextLastName = findViewById(R.id.edit_text_register_last_name);
        editTextEmail = findViewById(R.id.edit_text_register_email);
        editTextPhoneNumber = findViewById(R.id.edit_text_register_phone_number);
        editTextPassword = findViewById(R.id.edit_text_register_password);
        loginLink = findViewById(R.id.text_view_login);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Adds a new user to Firebase
                SignUpNewUser();
            }
        });
    }

    private void SignUpNewUser() {

        //Extracting strings from the editText
        final String firstName = editTextFirstName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        //Validating the user input
        if (firstName.isEmpty()) {
            //Toast.makeText(this, "Please enter First name", Toast.LENGTH_SHORT).show();
            editTextFirstName.setError("First name is required");
        } else if (lastName.isEmpty()) {
            // Toast.makeText(this, "Please enter Last name", Toast.LENGTH_SHORT).show();
            editTextLastName.setError("Last name is required");
        } else if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("Valid Phone Number is required");
        } else if (email.isEmpty()) {
            //Toast.makeText(this, "An email is required", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Email is required");
        } else if (password.isEmpty()) {
            //Toast.makeText(this, "A password is required", Toast.LENGTH_SHORT).show();
            editTextPassword.setError("Password is required");
        } else if (password.length() < 8) {
            editTextPassword.setError("Password must be at least 8 characters");

        } else {
            //Initializing the progressDialog
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Registering user...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                             progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                //Sign in success
                                Log.d(TAG, "onComplete: createUSerWith Email successful");
                                FirebaseUser user = task.getResult().getUser();
                                userId = mAuth.getCurrentUser().getUid();

                                //Add profile data to FireStore users collection
                                CreateUserProfile(userId, firstName, lastName, email, phoneNumber);

                                //Go to home activity
                                startHomeActivity();
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Creating Account failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }

    private void startHomeActivity() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            //Intent intent = new Intent(this, UploadProfileImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    //Save user data to firebase fireStore users collection
    private void CreateUserProfile(String userId, String firstName, String lastName, String email, String phoneNumber) {
        //Get an instance of the user object
        User newUser = new User(
                userId,
                firstName,
                lastName,
                email,
                phoneNumber,
                ""  //To be added after an profile image is uploaded and url is retrieved
        );
        //SETTING UP RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://amis-1.herokuapp.com/") //Add the base url for the api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserService userService = retrofit.create(UserService.class);

        Call<User> call = userService.saveProfile(newUser);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful())
                    Log.d(TAG, "onResponse: failed to save profile");
                //Go to home activity
                startHomeActivity();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: saving buyer profile failed");
                Toast.makeText(SignUpActivity.this, "saving buyer profile failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
