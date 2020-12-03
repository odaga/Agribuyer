package com.ugtechie.agribuyer.fragments;

import android.content.Intent;
import android.net.Uri;
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

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.models.User;
import com.ugtechie.agribuyer.ui.ProductCategory1Activity;

public class HomeFragment extends Fragment {

    private TextView textViewHomeUserName;
    private ImageView imageViewNotificationBell, userProfilePicture;
    private CardView cardViewCat1, cardViewCat2, cardViewCat3;
    private LoaderImageView homeBannerImage, categoryOneImage, categoryTwoImage, categoryThreeImage;


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
        final TextView cat1Text = v.findViewById(R.id.cat1_text);
        homeBannerImage = v.findViewById(R.id.home_banner_image);
        categoryOneImage = v.findViewById(R.id.product_category_one_image);
        categoryTwoImage = v.findViewById(R.id.product_category_two_image);
        categoryThreeImage = v.findViewById(R.id.product_category_three_image);

        //load home activity images
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Ffresh%20vegetables.jpg?alt=media&token=243ced8c-c209-4096-8c27-e11360cef8b7")).into(homeBannerImage);
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Fanimal%20products.jpg?alt=media&token=d53917df-ced5-48d0-97cd-ffa9391d578e")).into(categoryOneImage);
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Fvegetables.jpg?alt=media&token=de236cf3-876e-4249-90b9-fb5dee10a6ff")).into(categoryTwoImage);
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Ffruits%20category.jpg?alt=media&token=d4bbfddb-dd8f-426f-95d8-fb306932f772")).into(categoryThreeImage);


        //Update user info on the home fragment
        updateUserInfo();

        //Setting Click Listeners in Home Fragment
        cardViewCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategory1Activity.class);
                startActivity(intent);
            }
        });

        cardViewCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategory1Activity.class);
                startActivity(intent);

            }
        });

        cardViewCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategory1Activity.class);
                startActivity(intent);
            }
        });

        cardViewCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategory1Activity.class);
                startActivity(intent);
            }
        });


        return v;
    }

    private void updateUserInfo() {
        //show user profile information like username and profile picture
    }
}
