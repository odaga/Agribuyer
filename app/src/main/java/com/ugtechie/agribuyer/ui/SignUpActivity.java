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
import com.ugtechie.agribuyer.fragments.HomeFragment;
import com.ugtechie.agribuyer.models.User;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth;
    private Button buttonRegister;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhoneNumber;
    private EditText editTextEmail;
    private  EditText editTextPassword;
    private String userId;
    private String userDocumentId;

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
        }
        else if (lastName.isEmpty()) {
            // Toast.makeText(this, "Please enter Last name", Toast.LENGTH_SHORT).show();
            editTextLastName.setError("Last name is required");
        }
        else if(phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("Valid Phone Number is required");
        }
        else if (email.isEmpty()) {
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
                                CreateUserProfile(userId,firstName, lastName, email, phoneNumber);

                                //Go to home activity
                                startHomeActivity(user);
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Creating Account failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }

    private void startHomeActivity(FirebaseUser user) {

        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            //Intent intent = new Intent(this, UploadProfileImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("UserDocumentId", userDocumentId);
            startActivity(intent);
            finish();
        }
    }

    //Save user data to firebase fireStore users collection
     private void CreateUserProfile(String userId, String firstName, String lastName, String email, String phoneNumber) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Get an instance of the user object
        User newUser = new User(
                userId,
                firstName,
                lastName,
                email,
                phoneNumber,
                ""  //To be added after an profile image is uploaded and url is retrieved
        );
        //Add the new user object to firebase users collection
        db.collection("users")
                .add(newUser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "New user added with ID:  "+ documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding user", e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        try {
                             userDocumentId = task.getResult().getId();
                             //Send the user document id to the home fragment
                            Bundle bundle = new Bundle();
                            bundle.putString("UserDocumentID", userDocumentId);
                            HomeFragment homeFragment = new HomeFragment();
                            homeFragment.setArguments(bundle);

                        }
                        catch (Exception e) {
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


     /*
    //Save user data to the firebase realtime database
    private void saveUser(String firstName, String lastName, String email, String phoneNumber) {
        //Get an instance of the user object
        User newUser = new User(
                firstName,
                lastName,
                email,
                phoneNumber
        );
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );
        reference.setValue(newUser).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.e("Sign up status","Successful");
                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                            finish();
                        }
                        else {
                            Log.e("Sign up status","Unsuccessful");
                            Toast.makeText(SignUpActivity.this, "Could not sign up\nPlease try again later",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }
    */

}
