package ru.mirea.golysheva.cryptoloader;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import javax.crypto.SecretKey;
import ru.mirea.golysheva.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 1001;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonLoad.setOnClickListener(v -> {
            String phrase = binding.editTextPhrase.getText().toString().trim();
            if (phrase.isEmpty()) {
                Toast.makeText(this, "Введите фразу!", Toast.LENGTH_SHORT).show();
                return;
            }

            SecretKey key = CryptoUtils.generateKey();
            byte[] cipher = CryptoUtils.encryptMsg(phrase, key);

            Bundle bundle = new Bundle();
            bundle.putByteArray(MyLoader.ARG_CIPHER, cipher);
            bundle.putByteArray(MyLoader.ARG_KEY, key.getEncoded());

            LoaderManager.getInstance(this)
                    .initLoader(LOADER_ID, bundle, this);
        });
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyLoader(this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Toast.makeText(this, "Расшифровка: " + data, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }
}
