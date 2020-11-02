package com.ugtechie.agribuyer.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.models.CartProduct;
import com.ugtechie.agribuyer.models.Product;

import static com.ugtechie.agribuyer.ui.ProductCategory1Activity.SINGLE_PRODUCT_RECYCLERVIEW_ID;

public class ProductDetailsActivity extends AppCompatActivity {
    private static final String TAG = "ProductDetailsActivity";

    private Toolbar mActionBarToolbar;
    private LoaderImageView productDetailsImage;
    private LoaderTextView productName, productDescription, productPrice;
    private ProgressBar progressBar;
    private String productDetailsId;

    //Initializing firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productRef = db.collection("Submitted Products");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        //Setting the toolbar with title
        mActionBarToolbar = findViewById(R.id.toolbar);
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

        //Add click listener on cart button
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add product to buyer's cart cart
                addProductToCart();
                Toast.makeText(ProductDetailsActivity.this, "Add product to Cart", Toast.LENGTH_SHORT).show();
            }
        });
        progressBar = findViewById(R.id.progressBar);
        //Getting data from the intent sent from the product category activity
        Intent intent = getIntent();
        String productDetailsId = intent.getStringExtra(SINGLE_PRODUCT_RECYCLERVIEW_ID);
        getProductDetails(productDetailsId);
        
    }


    private void getProductDetails(final String productDetailsId) {
        DocumentReference DocRef = productRef.document(productDetailsId);
        DocRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Product productDetail = documentSnapshot.toObject(Product.class);
                        //Updating ui with data from the FireStore
                        productName.setText(productDetail.getProductName());
                        productPrice.setText("UGX " + productDetail.getProductPrice());
                        productDescription.setText(productDetail.getProductDescription());
                        Uri imageLink = Uri.parse(productDetail.getProductImageUrl());
                        Picasso.get()
                                .load(imageLink)
                                .into(productDetailsImage);

                        progressBar.setVisibility(View.INVISIBLE);
                        /*======================= ADD THE TWEAK THE UI ON THE PRODUCT DETAILS ACTIVITY TO SHOW THE DATA NICELY ===========================*/


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProductDetailsActivity.this, "Failed to fetch product details", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void addProductToCart() {
        DocumentReference userCartRef = db.collection("Users").document("Cart");
        //Creating an instance of CartProduct (Product to be added to cart)
        CartProduct cartProduct = new CartProduct(

        );

    }

}