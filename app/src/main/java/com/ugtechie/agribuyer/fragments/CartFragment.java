package com.ugtechie.agribuyer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.adapters.CartAdapter;
import com.ugtechie.agribuyer.api.ProductService;
import com.ugtechie.agribuyer.models.CartProduct;
import com.ugtechie.agribuyer.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment {
    private static final String TAG = "CartFragment";

    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        //Initializing the toolbar
        Toolbar mActionBarToolbar = v.findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("My Cart");

        RecyclerView productRecyclerview = v.findViewById(R.id.product_recyclerView);


        //SHOW PROGRESSBAR BEFORE PRODUCTS ARE LOADED
        //SETTING UP RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://lit-earth-63598.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<CartProduct>> call = productService.getCartItems(FirebaseAuth.getInstance().getCurrentUser().getUid());


        call.enqueue(new Callback<List<CartProduct>>() {
            @Override
            public void onResponse(Call<List<CartProduct>> call, Response<List<CartProduct>> response) {
                if (!response.isSuccessful()) {
                    //progressBar.setVisibility(View.INVISIBLE);
                   // Toast.makeText(getContext(), "Could not get items code: " + response.code(), Toast.LENGTH_LONG).show();
                } else {

                    List<CartProduct> CartItems = response.body();

                    //Setting up recyclerview
                    RecyclerView cartRecyclerView = v.findViewById(R.id.cart_recyclerview);
                    cartRecyclerView.setHasFixedSize(true);
                    adapter = new CartAdapter(CartItems);
                    cartRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    cartRecyclerView.setAdapter(adapter);
                    cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    cartRecyclerView.setAdapter(adapter);

                  //  progressBar.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(Call<List<CartProduct>> call, Throwable t) {
               // progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Could not get cart items", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }
}
