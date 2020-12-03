package com.ugtechie.agribuyer.api;

import com.ugtechie.agribuyer.models.Product;
import com.ugtechie.agribuyer.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET("buyer/{id}")
    Call<User> getUser(@Path("id") String userId);

    @POST("buyer")
    Call<User> saveProfile(@Body User user);
}
