package ru.mirea.golysheva.securesharedpreferences;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_FILE_NAME = "secret_shared_prefs";
    private static final String KEY_POET_NAME   = "POET_NAME";
    private static final String DEFAULT_POET    = "Уильям Шекспир";

    private SharedPreferences securePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvPoetName = findViewById(R.id.tvPoetName);
        ImageView ivPoet    = findViewById(R.id.ivPoet);

        initSecurePrefs();

        String poetName = securePrefs.getString(KEY_POET_NAME, DEFAULT_POET);
        tvPoetName.setText(poetName);

        ivPoet.setImageResource(R.drawable.shekspir);
    }

    private void initSecurePrefs() {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            securePrefs = EncryptedSharedPreferences.create(
                    PREF_FILE_NAME,
                    masterKeyAlias,
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            if (!securePrefs.contains(KEY_POET_NAME)) {
                securePrefs.edit()
                        .putString(KEY_POET_NAME, DEFAULT_POET)
                        .apply();
            }

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Не удалось инициализировать шифрованные настройки", e);
        }
    }
}