package ru.mirea.golysheva.mireaproject.ui.network;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.golysheva.mireaproject.MyApiService;
import ru.mirea.golysheva.mireaproject.Post;
import ru.mirea.golysheva.mireaproject.R;

public class NetworkFragment extends Fragment {
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_network, container, false);
        textView = view.findViewById(R.id.textView);
        fetchData();
        return view;
    }

    private void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyApiService api = retrofit.create(MyApiService.class);
        api.getPost().enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Post post = response.body();
                    textView.setText(post.title + "\n\n" + post.body);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textView.setText("Ошибка загрузки: " + t.getMessage());
            }
        });
    }
}