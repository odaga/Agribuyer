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

    @GET("products/{id}")
    Call<Product> getSingleProduct(@Path("id") String productId);

    @GET("cart/{id}")
    Call<List<CartProduct>> getCartItems(@Path("id") String FirebaseUserId);

    //Add product order
    @POST("order/{id}")
    Call<CartProduct> sendOrder(@Body CartProduct order);

    @POST("cart/{id}")
    Call<CartProduct> addToCart(@Body CartProduct cartItem);





}
