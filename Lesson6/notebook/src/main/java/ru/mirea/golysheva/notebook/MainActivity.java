package ru.mirea.golysheva.notebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE = 101;
    private EditText etFileName, etQuote;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFileName = findViewById(R.id.etFileName);
        etQuote    = findViewById(R.id.etQuote);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnLoad = findViewById(R.id.btnLoad);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQ_CODE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            btnSave.setOnClickListener(v -> saveQuote());
        }
        btnLoad.setOnClickListener(v -> loadQuote());
    }

    /* ===== Проверки хранилища ===== */
    private boolean isStorageWritable() {
        return Environment.MEDIA_MOUNTED
                .equals(Environment.getExternalStorageState());
    }

    private File getPublicDocumentsDir() {
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
    }

    /* ===== Сохранение ===== */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void saveQuote() {
        if (!isStorageWritable()) {
            toast("Внешнее хранилище недоступно для записи");
            return;
        }

        String fileName = etFileName.getText().toString().trim();
        String quote    = etQuote.getText().toString();

        if (fileName.isEmpty() || quote.isEmpty()) {
            toast("Заполните имя файла и цитату!");
            return;
        }

        File dir = getPublicDocumentsDir();
        if (!dir.exists() && !dir.mkdirs()) {
            toast("Не удалось создать каталог Documents");
            return;
        }

        File file = new File(dir, fileName);
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(file, StandardCharsets.UTF_8, false))) {   // false → перезапись
            bw.write(quote);
            toast("Сохранено: " + file.getAbsolutePath());
        } catch (Exception e) {
            toast("Ошибка записи: " + e.getMessage());
        }
    }

    /* ===== Загрузка ===== */
    private void loadQuote() {
        String fileName = etFileName.getText().toString().trim();
        if (fileName.isEmpty()) {
            toast("Введите имя файла для чтения");
            return;
        }
        File file = new File(getPublicDocumentsDir(), fileName);
        if (!file.exists()) {
            toast("Файл не найден");
            return;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(file), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append('\n');
            etQuote.setText(sb.toString().trim());
            toast("Загружено");
        } catch (Exception e) {
            toast("Ошибка чтения: " + e.getMessage());
        }
    }

    /* ===== Обратный вызов разрешения ===== */
    @Override public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                toast("Без разрешения приложение работать не сможет");
            }
        }
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}