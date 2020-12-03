package com.ugtechie.agribuyer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.ugtechie.agribuyer.R;
import com.ugtechie.agribuyer.models.CartProduct;
import com.ugtechie.agribuyer.models.Product;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    //Adapter variables
    private List<CartProduct> cartProductList;
    private OnItemClickListener mListener;

    public CartAdapter(List<CartProduct> cartProductList, OnItemClickListener mListener) {
        this.cartProductList = cartProductList;
        this.mListener = mListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnClickListener (OnItemClickListener listener) {
        mListener = listener;
    }

    public CartAdapter(List<CartProduct> cartProductList) {
        this.cartProductList = cartProductList;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        LoaderImageView productImage;

        public CartViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            productName = itemView.findViewById(R.id.cart_product_name);
            productPrice = itemView.findViewById(R.id.cart_product_price);
            productImage = itemView.findViewById(R.id.cart_product_image);

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
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item, parent, false);
        CartViewHolder vh = new CartViewHolder(v, (OnItemClickListener) mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartProduct cartItem = cartProductList.get(position);
        //setting up the thousand number format for prices
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

        holder.productName.setText(cartItem.getName());
        holder.productPrice.setText("UGX "+decimalFormat.format(Integer.parseInt(cartItem.getPrice())));

    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

}
