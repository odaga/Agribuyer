package com.ugtechie.agribuyer.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.squareup.picasso.Picasso;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.ui.ProductCategoryActivity;

public class HomeFragment extends Fragment {

    private TextView textViewHomeUserName;
    private ImageView imageViewNotificationBell, userProfilePicture;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        //Setting up widgets
        textViewHomeUserName = v.findViewById(R.id.text_view_user_name);
        imageViewNotificationBell = v.findViewById(R.id.image_view_notification_bell);
        userProfilePicture = v.findViewById(R.id.profile_image);
        CardView cardViewCat1 = v.findViewById(R.id.card_view_category_one);
        CardView cardViewCat2 = v.findViewById(R.id.card_view_category_two);
        CardView cardViewCat3 = v.findViewById(R.id.card_view_category_three);
        final TextView cat1Text = v.findViewById(R.id.cat1_text);
        LoaderImageView homeBannerImage = v.findViewById(R.id.home_banner_image);
        LoaderImageView categoryOneImage = v.findViewById(R.id.product_category_one_image);
        LoaderImageView categoryTwoImage = v.findViewById(R.id.product_category_two_image);
        LoaderImageView categoryThreeImage = v.findViewById(R.id.product_category_three_image);

        //load home activity images
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Ffresh%20vegetables.jpg?alt=media&token=243ced8c-c209-4096-8c27-e11360cef8b7")).into(homeBannerImage);
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Fuganda-coffe-plantation.jpg?alt=media&token=b934b5d5-5100-4ff0-8f94-b8afbfabdf5b")).into(categoryOneImage);
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Fpng-maize.png?alt=media&token=9dfef1b1-3a3b-4c16-a7b3-a960074debba")).into(categoryTwoImage);
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Fpotatoes.webp?alt=media&token=386b7040-a0d1-47b3-bf0c-33ba1cb96e32")).into(categoryThreeImage);


        //Update user info on the home fragment
        updateUserInfo();

        //Setting Click Listeners in Home Fragment
        cardViewCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategoryActivity.class);
                intent.putExtra("product_category", "Coffee Products");
                startActivity(intent);
            }
        });

        cardViewCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategoryActivity.class);
                intent.putExtra("product_category", "Coffee Products");
                startActivity(intent);

            }
        });

        cardViewCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategoryActivity.class);
                intent.putExtra("product_category", "Maize Products");
                startActivity(intent);
            }
        });

        cardViewCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategoryActivity.class);
                intent.putExtra("product_category", "Irish Potatoes");
                startActivity(intent);
            }
        });


        return v;
    }

    private void updateUserInfo() {
        //show user profile information like username and profile picture
    }
}
