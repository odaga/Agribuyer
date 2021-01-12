package com.ugtechie.agribuyer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.adapters.ProductAdapter;
import com.ugtechie.agribuyer.adapters.ProductsApiAdapter;
import com.ugtechie.agribuyer.api.ProductService;
import com.ugtechie.agribuyer.models.Product;
import com.ugtechie.agribuyer.utils.SpacingItemDecoration;
import com.ugtechie.agribuyer.utils.Tools;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductCategoryActivity extends AppCompatActivity {
    private static final String TAG = "ProductCategoryActivity";
    public static final String SINGLE_PRODUCT_RECYCLERVIEW_ID = "com.ugtechie.fragments.extra";
    private RelativeLayout categoryOneActivityRelativeLayout;
    private ProgressBar productCategoryProgressBar;

    ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        categoryOneActivityRelativeLayout = findViewById(R.id.product_category_relativelayout);


        productCategoryProgressBar = findViewById(R.id.category_progressbar);

        //Setting the toolbar with title and widgets
        Toolbar mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("product_category"));

        //getProductsFromFirebase();
        getProductsFromServer();

    }

    private void getProductsFromServer() {
        productCategoryProgressBar.setVisibility(View.VISIBLE);
        //SETTING UP RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("com.squareup.retrofit2:retrofit:2.9.0/") //Add the base url for the api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);

        Call<List<Product>> call = productService.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productCategoryProgressBar.setVisibility(View.INVISIBLE);
                if (!response.isSuccessful()) {
                    Toast.makeText(ProductCategoryActivity.this, "Failed with code: " + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    List<Product> productList = response.body();
                    buildRecyclerview(productList);
                    productCategoryProgressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Dismiss progress widget when the fetching products fails
                productCategoryProgressBar.setVisibility(View.INVISIBLE);

                Snackbar snackbar = Snackbar
                        .make(categoryOneActivityRelativeLayout, "Could not fetch products", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getProductsFromServer();
                            }
                        });
                snackbar.show();
            }
        });
    }

    private void buildRecyclerview(final List<Product> productList) {
        RecyclerView recyclerView = findViewById(R.id.product_category_recyclerview);
        recyclerView.setHasFixedSize(true);
        ProductsApiAdapter adapter = new ProductsApiAdapter(productList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new ProductsApiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra("SINGLE_PRODUCT_RECYCLERVIEW_ID", productList.get(position).get_id());
                startActivity(intent);
                Toast.makeText(ProductCategoryActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //Adding an on click listener to the product card
        //passing single product data to the detailsActivity
        adapter.setOnClickListener(new ProductsApiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                productList.get(position);
                Intent intent = new Intent(ProductCategoryActivity.this, ProductDetailsActivity.class);
                intent.putExtra("SINGLE_PRODUCT_RECYCLERVIEW_ID", productList.get(position).get_id());
                startActivity(intent);
            }
        });
    }



    /*
    private void getProductsFromFirebase() {

        //SETTING UP THE QUERY TO FETCH THE PRODUCTS FROM FIREBASE
        // Query query = productRef; //Query picks all submitted products

        Query query = productRef.whereEqualTo("productCategory", "Legumes");
        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .build();
        productAdapter = new ProductAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.product_category_recyclerview);
        recyclerView.setHasFixedSize(true);
        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        recyclerView.setAdapter(productAdapter);

        //Handling click listener on the single product card
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Product product = documentSnapshot.toObject(Product.class);
                String SingleProductDocumentId = documentSnapshot.getId();

                Intent intent = new Intent(getApplicationContext(), ProductDetailsActivity.class);
                intent.putExtra(SINGLE_PRODUCT_RECYCLERVIEW_ID, SingleProductDocumentId);
                startActivity(intent);
            }
        });
    }
    */


    /*
    @Override
    public void onStart() {
        super.onStart();
        productAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        productAdapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        productAdapter.startListening();
    }
    */
}