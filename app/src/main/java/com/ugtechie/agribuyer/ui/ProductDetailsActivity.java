package com.ugtechie.agribuyer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.api.ProductService;
import com.ugtechie.agribuyer.fragments.CartFragment;
import com.ugtechie.agribuyer.fragments.HomeFragment;
import com.ugtechie.agribuyer.models.CartProduct;
import com.ugtechie.agribuyer.models.Product;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ugtechie.agribuyer.ui.ProductCategoryActivity.SINGLE_PRODUCT_RECYCLERVIEW_ID;

public class ProductDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ProductDetailsActivity";
    private int item_quantity = 1;

    private LoaderImageView productDetailsImage;
    private LoaderTextView productName, productDescription, productPrice;
    private ProgressBar progressBar;
    private String productDetailsId;
    private Button increaseQuantity, decreaseQuantity;
    private TextView quantity;
    private int productQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        //Setting the toolbar with title
        Toolbar mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Product Details");

        //Setting up widgets
        productDetailsImage = findViewById(R.id.product_details_image);
        productName = findViewById(R.id.product_details_name);
        productDescription = findViewById(R.id.product_detail_description);
        productPrice = findViewById(R.id.product_details_price);
        Button addToCartButton = findViewById(R.id.add_to_cart_button);
        increaseQuantity = findViewById(R.id.increase_quantity);
        decreaseQuantity = findViewById(R.id.decrease_quantity);
        quantity = findViewById(R.id.cart_item_quantity);


        //getProduct(productDetailsId);

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_quantity = item_quantity + 1;
                quantity.setText(String.valueOf(item_quantity));
            }
        });

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item_quantity == 1) {
                    //Do nothing
                    Toast.makeText(ProductDetailsActivity.this, "Minimum quantity is one item", Toast.LENGTH_SHORT).show();
                } else {
                    item_quantity = item_quantity - 1;
                    quantity.setText(String.valueOf(item_quantity));
                }
            }
        });


        //Add click listener on cart button
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add product to buyer's cart cart
                addProductToCart();
            }
        });
        progressBar = findViewById(R.id.progressBar);
        //Getting data from the intent sent from the product category activity
        Intent intent = getIntent();
        String productDetailsId = intent.getStringExtra("SINGLE_PRODUCT_RECYCLERVIEW_ID");
        String pId = intent.getStringExtra("pId");
        //Toast.makeText(this, productDetailsId, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, pId, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, pId, Toast.LENGTH_SHORT).show();
        //getProduct(productDetailsId);


        try {
            /*
            if (!pId.isEmpty()) {
                getProduct(pId);
            } else {
                getProduct(productDetailsId);
            }
            */
            getProduct(productDetailsId);
            getProduct(pId);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void getProduct(String productDetailsId) {
        //Call the product from the api
        //SETTING UP RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://amis-1.herokuapp.com/") //Add the base url for the api
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        //Call<Product> call = productService.getSingleProduct(productDetailsId);
        Call<Product> call = productService.getSingleProduct(productDetailsId);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (!response.isSuccessful()) {
                    Toast.makeText(ProductDetailsActivity.this, "Error fetching product code: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                //setting up the thousand number format for prices
                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

                //update the ui with data from the api
                productName.setText(response.body().getName());
                productPrice.setText("UGX" + response.body().getPrice());
                productDescription.setText(response.body().getDescription());
                if (response.body().getProductImage() == null) {
                    productDetailsImage.setImageResource(R.drawable.app_background);
                } else {
                    Picasso.get().load(response.body().getProductImage())
                            .into(productDetailsImage);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(ProductDetailsActivity.this, "Failed to fetch product details", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addProductToCart() {

        Product cartItem = new Product(
                "",
                getIntent().getStringExtra("product_name"),
                getIntent().getStringExtra("product_description"),
                getIntent().getStringExtra("product_price"),
                getIntent().getStringExtra("product_category"),
                getIntent().getStringExtra("product_image"),
                getIntent().getStringExtra("ownerId"),   //will be updated when the plus is implemented on each cart item
                false,
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );

        CartProduct item = new CartProduct(
                "",
                //getIntent().getStringExtra("product_id"),
                //productDetailsId,
                getIntent().getStringExtra(SINGLE_PRODUCT_RECYCLERVIEW_ID),
                getIntent().getStringExtra("product_name"),
                getIntent().getStringExtra("product_description"),
                getIntent().getStringExtra("product_price"),
                getIntent().getStringExtra("product_category"),
                getIntent().getStringExtra("product_image"),
        item_quantity,
                //Integer.parseInt(quantity.getText().toString()),
                getIntent().getStringExtra("ownerId"),
                FirebaseAuth.getInstance().getCurrentUser().getUid()
        );


        //Toast.makeText(ProductDetailsActivity.this, String.valueOf(item_quantity), Toast.LENGTH_SHORT).show();
        //Toast.makeText(ProductDetailsActivity.this, getIntent().getStringExtra("ownerId"), Toast.LENGTH_SHORT).show();

        //SETTING UP RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://amis-1.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        Call<CartProduct> call = productService.addToCart(item);

        call.enqueue(new Callback<CartProduct>() {
            @Override
            public void onResponse(Call<CartProduct> call, Response<CartProduct> response) {
                if (!response.isSuccessful())
                    Toast.makeText(ProductDetailsActivity.this, "Request failed with code:" + response.code(), Toast.LENGTH_SHORT).show();
                else {
                    //show success message and open cart fragment
                    Toast.makeText(ProductDetailsActivity.this, "Product Added to Cart", Toast.LENGTH_SHORT).show();
                    //StartCartFragment
                }
            }

            @Override
            public void onFailure(Call<CartProduct> call, Throwable t) {
                //Toast.makeText(ProductDetailsActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
                Toast.makeText(ProductDetailsActivity.this, "Product Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

    }


} 