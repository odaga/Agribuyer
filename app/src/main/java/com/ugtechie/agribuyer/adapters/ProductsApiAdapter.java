package com.ugtechie.agribuyer.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.squareup.picasso.Picasso;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.models.Product;

import java.text.DecimalFormat;
import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class ProductsApiAdapter extends RecyclerView.Adapter<ProductsApiAdapter.productViewHolder> {
    //Adapter variables
    private List<Product> productsList;
    private OnItemClickListener mListener;

    //OnClick Interface
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //Adapter constructor
    public ProductsApiAdapter(List<Product> productsList) {
        this.productsList = productsList;
    }

    public static class productViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        LoaderImageView productImage;

        public productViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            productName = itemView.findViewById(R.id.single_product_name);
            productPrice = itemView.findViewById(R.id.single_product_price);
            productImage = itemView.findViewById(R.id.single_product_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if (listener != null) {
                       int position = getAdapterPosition();
                       if (position != RecyclerView.NO_POSITION) {
                           listener.onItemClick(position);
                       }
                   }
                }
            });
        }
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_product_card, parent, false);
        productViewHolder vh = new productViewHolder(v, mListener); //add listener as parameter
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {
        Product product =  productsList.get(position);
        //setting up the thousand number format for prices
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        
        try {
            holder.productName.setText(product.getName());
            holder.productPrice.setText("UGX "+decimalFormat.format(Integer.parseInt(product.getPrice())));

        }
        catch (Exception e) {
            Log.d(TAG, "onBindViewHolder: " +e.getMessage());
        }

        try {
            if (product.getProductImage().isEmpty())
                holder.productImage.setImageResource(R.drawable.app_background);
            else
                Picasso.get().load(product.getProductImage()).into(holder.productImage);
        }
        catch (Exception e) {
            Log.d(TAG, "onBindViewHolder: " +e.getMessage());
            holder.productImage.setImageResource(R.drawable.app_background);
        }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }




}
