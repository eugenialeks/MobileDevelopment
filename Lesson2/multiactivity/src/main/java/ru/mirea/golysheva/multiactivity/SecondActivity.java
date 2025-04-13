package ru.mirea.golysheva.multiactivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivityLifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_second);

        String text = (String) getIntent().getSerializableExtra("key");
        TextView textView = findViewById(R.id.textView);
        textView.setText(text);
    }
}