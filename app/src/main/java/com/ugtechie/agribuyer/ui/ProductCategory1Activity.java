package com.ugtechie.agribuyer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.adapters.ProductAdapter;
import com.ugtechie.agribuyer.models.Product;
import com.ugtechie.agribuyer.utils.SpacingItemDecoration;
import com.ugtechie.agribuyer.utils.Tools;

public class ProductCategory1Activity extends AppCompatActivity {
    private static final String TAG = "ProductCategory1Activity";
    public static final String SINGLE_PRODUCT_RECYCLERVIEW_ID = "com.ugtechie.fragments.extra";

    ProductAdapter productAdapter;
    //Initializing firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productRef = db.collection("Submitted Products");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category1);

        //Setting the toolbar with title and widgets
        Toolbar mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Product Category");

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
}