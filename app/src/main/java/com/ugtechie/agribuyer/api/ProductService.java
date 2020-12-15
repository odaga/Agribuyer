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
    Call<List<Product>> getCartItems(@Path("id") String FirebaseUserId);

    //Add product order
    @POST("orders/{id}")
    Call<Product> sendOrder(@Body Product order);

    //Option2
    @POST("orders")
    Call<List<Product>> sendOrderList(@Body List<Product> products);

    //Add product to buyer's cart
    @POST("cart/")
    Call<Product> addToCart(@Body Product cartItem);


}
