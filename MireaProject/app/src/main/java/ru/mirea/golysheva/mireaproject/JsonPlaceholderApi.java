package ru.mirea.golysheva.mireaproject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {
    @GET("posts/1")
    Call<Post> getPost();
}