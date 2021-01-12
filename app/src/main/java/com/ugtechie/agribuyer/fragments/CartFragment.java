package com.ugtechie.agribuyer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.ugtechie.agribuyer.ui.HomeActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment {
    private static final String TAG = "CartFragment";

    private CartAdapter adapter;
    private RecyclerView cartRecyclerView;
    private ProgressBar cartProgressBar;
    private TextView cartEmpty;
    private Button checkoutButton;
    private List<Product> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        //Initializing the toolbar
        Toolbar mActionBarToolbar = v.findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("My Cart");

        // RecyclerView productRecyclerview = v.findViewById(R.id.product_recyclerView);
        cartRecyclerView = v.findViewById(R.id.cart_recyclerview);
        cartProgressBar = v.findViewById(R.id.cart_progressbar);
        cartEmpty = v.findViewById(R.id.cart_empty_text);
        checkoutButton = v.findViewById(R.id.checkout_button);
        cartEmpty.setVisibility(View.INVISIBLE);


        //SHOW PROGRESSBAR BEFORE PRODUCTS ARE LOADED
        //SETTING UP RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://amis-1.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getCartItems(FirebaseAuth.getInstance().getCurrentUser().getUid());

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    cartProgressBar.setVisibility(View.INVISIBLE);
                    cartEmpty.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Could not get items code: " + response.code(), Toast.LENGTH_LONG).show();

                } else {

                    List<Product> CartItems = response.body();
                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();

                    //Setting up recyclerview
                    cartRecyclerView = v.findViewById(R.id.cart_recyclerview);
                    cartRecyclerView.setHasFixedSize(true);
                    adapter = new CartAdapter(CartItems);
                    cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    cartRecyclerView.setAdapter(adapter);
                    cartProgressBar.setVisibility(View.INVISIBLE);

                    checkoutButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sendProductOrders(CartItems);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Could not get cart items", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void sendProductOrders(List<Product> cartItems) {
        cartProgressBar.setVisibility(View.VISIBLE);
        //Setting up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://amis-1.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.sendOrderList(cartItems);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    cartProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Checkout Failed with code: " + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Checkout complete", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                cartProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
