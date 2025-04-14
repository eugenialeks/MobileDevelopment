package ru.mirea.golysheva.favoritebook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    private EditText editBook, editQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView devBook  = findViewById(R.id.textDevBook);
        TextView devQuote = findViewById(R.id.textDevQuote);
        editBook  = findViewById(R.id.editUserBook);
        editQuote = findViewById(R.id.editUserQuote);
        Button btnSend = findViewById(R.id.btnSend);

        Intent intent = getIntent();
        devBook.setText(intent.getStringExtra(MainActivity.BOOK_NAME_KEY));
        devQuote.setText(intent.getStringExtra(MainActivity.QUOTES_KEY));

        btnSend.setOnClickListener(v -> sendResult());
    }

    private void sendResult() {
        String userBook  = editBook.getText().toString().trim();
        String userQuote = editQuote.getText().toString().trim();

        if (TextUtils.isEmpty(userBook) || TextUtils.isEmpty(userQuote)) {
            Toast.makeText(this, "Пожалуйста, заполните оба поля", Toast.LENGTH_SHORT).show();
            return;
        }

        String message = "Название Вашей любимой книги: " + userBook +
                "\nЦитата: " + userQuote;

        Intent data = new Intent();
        data.putExtra(MainActivity.USER_MESSAGE, message);
        setResult(RESULT_OK, data);
        finish();
    }
}