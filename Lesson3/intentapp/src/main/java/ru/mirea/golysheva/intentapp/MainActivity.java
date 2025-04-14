package ru.mirea.golysheva.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button button;
    int myNumber = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.buttonSendIntent);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long currentTimeMillis = System.currentTimeMillis();
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                        .format(new Date(currentTimeMillis));

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("number", myNumber);
                intent.putExtra("time", currentTime);
                startActivity(intent);
            }
        });
    }
}