package ru.mirea.golysheva.data_thread;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.TimeUnit;
import ru.mirea.golysheva.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        startTime = System.currentTimeMillis();

        final Runnable runn1 = new Runnable() {
            public void run() {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                binding.tvInfo.append(elapsed + " сек: runOnUiThread → runn1\n");
            }
        };
        final Runnable runn2 = new Runnable() {
            public void run() {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                binding.tvInfo.append(elapsed + " сек: post → runn2\n");
            }
        };
        final Runnable runn3 = new Runnable() {
            public void run() {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                binding.tvInfo.append(elapsed + " сек: postDelayed → runn3\n");
            }
        };

        Thread t = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                runOnUiThread(runn1);
                TimeUnit.SECONDS.sleep(1);
                binding.tvInfo.post(runn2);
                binding.tvInfo.postDelayed(runn3, 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }
}

