package ru.mirea.golysheva.multiactivity;

import android.os.Bundle;
import android.util.Log;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityLifecycle";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
    }

    public void onClickNewActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("key", editText.getText().toString());
        startActivity(intent);
    }
}