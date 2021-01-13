package com.ugtechie.agribuyer.api;


import com.ugtechie.agribuyer.models.CartProduct;
import com.ugtechie.agribuyer.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductService {
    @GET("products")
    Call<List<Product>> getProducts();

    //Get one product category
    @GET("products/category/{id}")
    Call<List<Product>> getProductCategory(@Path("id") String categoryId);

    //Fetch single product details
    @GET("products/{id}")
    Call<Product> getSingleProduct(@Path("id") String productId);

    //Get current user cart items
    @GET("cart/{id}")
    Call<List<CartProduct>> getCartItems(@Path("id") String FirebaseUserId);

    //Add product to buyer's cart
    @POST("cart")
    Call<CartProduct> addToCart(@Body CartProduct cartItem);

    //Option 1
    //Add product order (Failed)
    @POST("orders/{id}")
    Call<Product> sendOrder(@Body Product order);

    //Option2 (Worked)
    @POST("orders")
    Call<List<CartProduct>> sendOrderList(@Body List<CartProduct> products);

    //Clear the cuurrent user cart after checkout
    @GET("/cart/clear/{id}")
    Call<Void> clearCart(@Path("id") String FirebaseUserId);


}
