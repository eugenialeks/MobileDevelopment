package ru.mirea.golysheva.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textViewResult);

        Intent intent = getIntent();
        int number = intent.getIntExtra("number", 0);
        String time = intent.getStringExtra("time");

        int square = number * number;
        String result = "Квадрат значения моего номера по списку составляет " + square +
                ", а текущее время " + time;
        textView.setText(result);
    }
}