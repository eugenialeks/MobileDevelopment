package ru.mirea.golysheva.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> activityResultLauncher;

    public static final String BOOK_NAME_KEY = "book_name";
    public static final String QUOTES_KEY    = "quotes_name";
    public static final String USER_MESSAGE  = "MESSAGE";

    private TextView textViewUserBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewUserBook = findViewById(R.id.textViewBook);

        ActivityResultCallback<ActivityResult> callback = result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                String userBook = result.getData().getStringExtra(USER_MESSAGE);
                textViewUserBook.setText(userBook);
            }
        };

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                callback
        );
    }

    public void getInfoAboutBook(View view) {
        Intent intent = new Intent(this, ShareActivity.class);
        intent.putExtra(BOOK_NAME_KEY,  "Мастер и Маргарита");
        intent.putExtra(QUOTES_KEY,     "«Разве можно говорить о том, чего не бывает?»");
        activityResultLauncher.launch(intent);
    }
}