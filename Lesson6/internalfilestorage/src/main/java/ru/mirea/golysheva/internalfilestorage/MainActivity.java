package ru.mirea.golysheva.internalfilestorage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final String TAG       = MainActivity.class.getSimpleName();
    private static final String FILE_NAME = "russian_history.txt";
    private static final String CHARSET   = StandardCharsets.UTF_8.name();   // ← гарантируем UTF-8

    private EditText etInput;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput  = findViewById(R.id.etInput);
        tvResult = findViewById(R.id.tvResult);
        Button btnSave = findViewById(R.id.btnSave);

        String stored = readFromFile();
        if (stored != null) tvResult.setText(stored);

        btnSave.setOnClickListener(v -> {
            String text = etInput.getText().toString().trim();
            if (text.isEmpty()) {
                Toast.makeText(this, "Введите дату и описание!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (writeToFile(text)) {
                tvResult.setText(text);
                Toast.makeText(this, "Файл сохранён", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean writeToFile(String text) {
        try (FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
            fos.write(text.getBytes(CHARSET));                    // ← сохраняем в UTF-8
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Ошибка записи", e);
            Toast.makeText(this, "Ошибка записи: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Nullable
    private String readFromFile() {
        try (FileInputStream fin = openFileInput(FILE_NAME)) {
            byte[] bytes = new byte[fin.available()];
            //noinspection ResultOfMethodCallIgnored
            fin.read(bytes);
            return new String(bytes, CHARSET);                    // ← читаем в UTF-8
        } catch (IOException e) {
            Log.i(TAG, "Файл ещё не создан");
            return null;
        }
    }
}