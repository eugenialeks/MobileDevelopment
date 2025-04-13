package ru.mirea.golysheva.activitylifecycle;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Lifecycle";
    private static final String TEXT_KEY = "text_value";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        if (savedInstanceState != null) {
            String restoredText = savedInstanceState.getString(TEXT_KEY);
            editText.setText(restoredText);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    // Вызывается перед тем, как пользователь начнет взаимодействовать с Activity
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    // Вызывается, когда пользователь покидает Activity, но она еще видна (например, открывает другую Activity)
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    // Вызывается, когда Activity больше не видна (перешла в фон)
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    // Вызывается перед окончательным уничтожением Activity (по нажатию "Назад" или `finish()`)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    // Вызывается, когда Activity возвращается после вызова `onStop()`
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    // Сохраняет состояние (например, при повороте экрана или нехватке памяти)
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState()");
        outState.putString(TEXT_KEY, editText.getText().toString());
    }

    // Восстанавливает состояние (только если Bundle не null)
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState()");
        String restoredText = savedInstanceState.getString(TEXT_KEY);
        editText.setText(restoredText);
    }
}
