package com.ugtechie.agribuyer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.ui.LoginActivity;
import com.ugtechie.agribuyer.ui.MainActivity;

public class AccountFragment extends Fragment {
    private static final String TAG = "AccountFragment";

    private Toolbar mActionBarToolbar;
    //Initialising Firebase
    final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //initializing widgets
    CircularImageView profileImage;
    LoaderTextView nameField;
    LoaderTextView emailField;
    LoaderTextView phoneField;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        //Setting up Toolbar
        //Initializing the toolbar
        mActionBarToolbar = v.findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("My Account");

        //Setting up widgets
        LinearLayout logoutBtn = v.findViewById(R.id.log_out_button);
        profileImage = v.findViewById(R.id.circle_image_account_profile_image);
        nameField = v.findViewById(R.id.name_field);
        emailField = v.findViewById(R.id.email_field);
        phoneField = v.findViewById(R.id.phone_field);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
       // nameField.setText(mAuth.getCurrentUser().getDisplayName());
       // phoneField.setText(mAuth.getCurrentUser().getPhoneNumber());
       // emailField.setText(mAuth.getCurrentUser().getEmail());

        //Populate use data fields
         retrieveUseInfo();

        return v;
    }

    private void retrieveUseInfo() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference userInfoRef = db.collection("users");


       /*
        userInfoRef.whereEqualTo("userId", mAuth.getCurrentUser().getUid()).get()
               .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                   @Override
                   public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       DocumentSnapshot document = (DocumentSnapshot) queryDocumentSnapshots.getDocuments();
                       nameField.setText(document.getString("firstName") +" "+ document.getString("lastName"));
                       emailField.setText(document.getString("email"));
                       phoneField.setText(document.getString("phoneNumber"));

                   }
               });
       */

    }


}


