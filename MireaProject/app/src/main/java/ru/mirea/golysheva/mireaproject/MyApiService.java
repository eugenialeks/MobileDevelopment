package ru.mirea.golysheva.mireaproject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApiService {
    @GET("posts/1") // Пример: https://jsonplaceholder.typicode.com/posts/1
    Call<Post> getPost();
}