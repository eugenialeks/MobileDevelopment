package ru.mirea.golysheva.workmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import ru.mirea.golysheva.workmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonStartWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUploadWork();
            }
        });
    }

    private void startUploadWork() {

        Constraints constraints = new Constraints.Builder()
                // .setRequiredNetworkType(NetworkType.UNMETERED)
                // .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        WorkRequest uploadWorkRequest =
                new OneTimeWorkRequest.Builder(UploadWorker.class)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(this)
                .enqueue(uploadWorkRequest);

        Toast.makeText(this,
                "UploadWorker enqueued with constraints",
                Toast.LENGTH_SHORT).show();

        Log.d(TAG, "UploadWorker enqueued");
    }
}