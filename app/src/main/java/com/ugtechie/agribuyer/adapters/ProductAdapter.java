package com.ugtechie.agribuyer.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.models.Product;

public class ProductAdapter extends FirestoreRecyclerAdapter<Product, ProductAdapter.ProductViewHolder> {
    private OnItemClickListener listener;

    public ProductAdapter(@NonNull FirestoreRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {
        holder.productName.setText(model.getName());
        holder.productPrice.setText("UGX " + model.getPrice());
        String imageUrl = model.getProductImage();

        //Image needs to be fetched using Picasso image loader
        Picasso.get()
                .load(imageUrl)
                // .resize(150, 150)  //Sets image to a standard dimension 150px *150px
                .into(holder.productImage);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_product_card, parent, false);
        return new ProductViewHolder(v);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        LoaderImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.single_product_name);
            productPrice = itemView.findViewById(R.id.single_product_price);
            productImage = itemView.findViewById(R.id.single_product_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
