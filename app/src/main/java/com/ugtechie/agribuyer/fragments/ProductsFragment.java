package com.ugtechie.agribuyer.fragments;

import android.app.Activity;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.adapters.ProductAdapter;
import com.ugtechie.agribuyer.adapters.ProductsApiAdapter;
import com.ugtechie.agribuyer.api.ProductService;
import com.ugtechie.agribuyer.models.Product;
import com.ugtechie.agribuyer.ui.ProductDetailsActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductsFragment extends Fragment {
    private static final String TAG = "ProductsFragment";
    public static final String SINGLE_PRODUCT_RECYCLERVIEW_ID = "com.ugtechie.fragments.extra";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ProductAdapter productAdapter;
    private ProductsApiAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_products, container, false);

        //Initializing the toolbar
        Toolbar mActionBarToolbar = v.findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("Products");


        RecyclerView productRecyclerview = v.findViewById(R.id.product_recyclerView);
        progressBar = v.findViewById(R.id.products_progressbar);

        //SHOW PROGRESSBAR BEFORE PRODUCTS ARE LOADED
        progressBar.setVisibility(View.VISIBLE);
        //SETTING UP RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://amis-1.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<List<Product>> call = productService.getProducts();


        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Could not get products", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    List<Product> products = response.body();

                    //Setting up recyclerview
                    productRecyclerview.setHasFixedSize(true);
                    adapter = new ProductsApiAdapter(products);
                    productRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    productRecyclerview.setAdapter(adapter);

                    adapter.setOnClickListener(new ProductsApiAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            products.get(position);
                            Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
                            intent.putExtra(SINGLE_PRODUCT_RECYCLERVIEW_ID, products.get(position).get_id());
                            intent.putExtra("product_id", products.get(position).get_id());
                            intent.putExtra("product_name", products.get(position).getName());
                            intent.putExtra("product_price", products.get(position).getPrice());
                            intent.putExtra("product_description", products.get(position).getDescription());
                            intent.putExtra("product_category", products.get(position).getProductCategory());
                            intent.putExtra("product_image", products.get(position).getProductImage());
                            intent.putExtra("ownerId", products.get(position).getOwnerId());
                            intent.putExtra("pId", products.get(position).get_id());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Could not get products", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}
