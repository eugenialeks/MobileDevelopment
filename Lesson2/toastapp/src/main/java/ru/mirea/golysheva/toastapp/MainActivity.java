package ru.mirea.golysheva.toastapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
    }

    public void showToast(View view) {
        String inputText = editText.getText().toString();
        int symbolCount = inputText.length();

        String message = "СТУДЕНТ №6 ГРУППА БСБО-09-22 Количество символов - " + symbolCount;

        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}