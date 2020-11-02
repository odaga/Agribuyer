package com.ugtechie.agribuyer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.models.User;
import com.ugtechie.agribuyer.ui.ProductCategory1Activity;

public class HomeFragment extends Fragment {

    private TextView textViewHomeUserName;
    private ImageView imageViewNotificationBell, userProfilePicture;
    private CardView cardViewCat1, cardViewCat2, cardViewCat3, cardViewCat4;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        //Setting up widgets
        textViewHomeUserName = v.findViewById(R.id.text_view_user_name);
        imageViewNotificationBell = v.findViewById(R.id.image_view_notification_bell);
        userProfilePicture = v.findViewById(R.id.profile_image);
        cardViewCat1 = v.findViewById(R.id.card_view_category_one);
        cardViewCat2 = v.findViewById(R.id.card_view_category_two);
        cardViewCat3 = v.findViewById(R.id.card_view_category_three);
        cardViewCat4 = v.findViewById(R.id.card_view_category_four);
        final TextView cat1Text = v.findViewById(R.id.cat1_text);

        //Update user info on the home fragment
        updateUserInfo();

        //Setting Click Listeners in Home Fragment
        cardViewCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                ProductsFragment productsFragment = new ProductsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, productsFragment, "productsFragment")
                        .addToBackStack(null)
                        .commit();

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProductsFragment()).commit();
                */
                //cat1Text.setText("Category one card");
                Intent intent = new Intent(getActivity(), ProductCategory1Activity.class);
                startActivity(intent);


            }
        });

        cardViewCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        cardViewCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardViewCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cardViewCat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }

    private void updateUserInfo() {
        //Initialize firebase FireStore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference users = db.collection("Users");
        /*
        try {
            users.whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser()).get()
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed to load user data", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User userProfile = (User) document.getData();

                                    textViewHomeUserName.setText("Hello, " + userProfile.getFirstName());
                                }
                            }
                        }
                    });
        }
        catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        */
        try {
            //Get the user Document reference from the sign up activity
            String userDocId = getArguments().getString("UserDocumentID");

            users.document(userDocId).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User user = new User();
                            user = documentSnapshot.toObject(User.class);

                            //Update UI with user info
                            //textViewHomeUserName.setText(user.getFirstName());
                            textViewHomeUserName.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed to get User info", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        catch(Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
