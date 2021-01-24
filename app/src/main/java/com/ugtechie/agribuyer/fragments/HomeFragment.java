package com.ugtechie.agribuyer.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.api.ProductService;
import com.ugtechie.agribuyer.api.UserService;
import com.ugtechie.agribuyer.models.User;
import com.ugtechie.agribuyer.ui.ProductCategoryActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

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
        CardView cardViewCat4 = v.findViewById(R.id.card_view_category_four);
        final TextView cat1Text = v.findViewById(R.id.cat1_text);
        LoaderImageView homeBannerImage = v.findViewById(R.id.home_banner_image);
        LoaderImageView categoryOneImage = v.findViewById(R.id.product_category_one_image);
        LoaderImageView categoryTwoImage = v.findViewById(R.id.product_category_two_image);
        LoaderImageView categoryThreeImage = v.findViewById(R.id.product_category_three_image);
        LoaderImageView categoryFourImage = v.findViewById(R.id.product_category_four_image);

        //load home activity images
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Fbuyer_header_image.jpg?alt=media&token=3c197e7d-8e6e-4935-a7cf-3fc191cc5ba4")).into(homeBannerImage);
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Ffruit.jpg?alt=media&token=a8be48f2-0733-4525-845b-3b78677c1e13")).into(categoryOneImage);
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Fvegetables.jpg?alt=media&token=874ea41c-a743-4038-83df-fe349bafb90e")).into(categoryTwoImage);
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Fcereals.webp?alt=media&token=81ddc7d4-05b1-4447-9e45-274ca0a52ca5")).into(categoryThreeImage);
        Picasso.get().load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/agri-products-eec4b.appspot.com/o/home_Activity_Banner_Imagines%2Fmatooke.jpg?alt=media&token=0ae58458-3e33-41f0-9ef4-2576e4bfd5e1")).into(categoryFourImage);


        //Update user info on the home fragment
        updateUserInfo();

        //Setting Click Listeners in Home Fragment
        cardViewCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategoryActivity.class);
                intent.putExtra("product_category", "Fruits");
                startActivity(intent);
            }
        });


        cardViewCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategoryActivity.class);
                intent.putExtra("product_category", "Vegetables");
                startActivity(intent);
            }
        });

        cardViewCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategoryActivity.class);
                intent.putExtra("product_category", "Cereals");
                startActivity(intent);
            }
        });

        cardViewCat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProductCategoryActivity.class);
                intent.putExtra("product_category", "Food");
                startActivity(intent);
            }
        });


        return v;
    }

    private void updateUserInfo() {
        //Call the product from the api
        //SETTING UP RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://amis-1.herokuapp.com/") //Add the base url for the api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // ProductService productService = retrofit.create(ProductService.class);
        UserService userService = retrofit.create(UserService.class);
        Call<User> call = userService.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Failed with code:" + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    textViewHomeUserName.setText("Hi, " + response.body().getFirstName());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //Toast.makeText(getContext(), "Connection error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: Connection error");
            }
        });
    }


}
