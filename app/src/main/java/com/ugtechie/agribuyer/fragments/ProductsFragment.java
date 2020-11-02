package com.ugtechie.agribuyer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import com.ugtechie.agribuyer.models.Product;
import com.ugtechie.agribuyer.ui.ProductDetailsActivity;

public class ProductsFragment extends Fragment {
    private static final String TAG = "ProductsFragment";
    //public static final String RECYCLERVIEW_SINGLE_ID = "com.ugtechie.eshamber.ui.farm.id.extra";
    public static final String SINGLE_PRODUCT_RECYCLERVIEW_ID = "com.ugtechie.fragments.extra";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ProductAdapter productAdapter;
    private Toolbar mActionBarToolbar;
    //Initializing firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference productRef = db.collection("Submitted Products");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_products, container, false);

        //Initializing the toolbar
        mActionBarToolbar = v.findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle("Products");

        Query query = productRef;
        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query,Product.class)
                .build();
        productAdapter = new ProductAdapter(options);

        recyclerView = v.findViewById(R.id.product_recyclerView);
        recyclerView.setHasFixedSize(true);
       // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(productAdapter);

        //Handling click listener on the single product card
        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Product product = documentSnapshot.toObject(Product.class);
                String id = documentSnapshot.getId();

                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra(SINGLE_PRODUCT_RECYCLERVIEW_ID, id);
                startActivity(intent);
            }
        });
        return v;
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
