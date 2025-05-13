package ru.mirea.golysheva.lesson6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "mirea_settings";

    private EditText etGroup, etNumber, etFilm;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etGroup  = findViewById(R.id.etGroup);
        etNumber = findViewById(R.id.etNumber);
        etFilm   = findViewById(R.id.etFilm);
        Button btnSave = findViewById(R.id.btnSave);

        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        etGroup.setText (prefs.getString("GROUP",   ""));
        etNumber.setText(String.valueOf(prefs.getInt("NUMBER", 0)));
        etFilm.setText  (prefs.getString("FAVORITE_FILM", ""));

        btnSave.setOnClickListener(v -> {
            String group  = etGroup.getText().toString().trim();
            int    number = Integer.parseInt(etNumber.getText().toString().trim());
            String film   = etFilm.getText().toString().trim();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("GROUP", group);
            editor.putInt   ("NUMBER", number);
            editor.putString("FAVORITE_FILM", film);
            editor.apply();

            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
        });
    }
}
