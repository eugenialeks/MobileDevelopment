package ru.mirea.golysheva.serviceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import ru.mirea.golysheva.serviceapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private final ActivityResultLauncher<String[]> requestPermsLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                boolean granted = result.getOrDefault(Manifest.permission.POST_NOTIFICATIONS, false);
                Log.d("MainActivity", "POST_NOTIFICATIONS granted? " + granted);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermsLauncher.launch(new String[]{
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.FOREGROUND_SERVICE
            });
        }

        binding.buttonStart.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(this, PlayerService.class);
            ContextCompat.startForegroundService(this, serviceIntent);
        });

        binding.buttonStop.setOnClickListener(v -> {
            Intent stopIntent = new Intent(this, PlayerService.class);
            stopService(stopIntent);
        });
    }
}