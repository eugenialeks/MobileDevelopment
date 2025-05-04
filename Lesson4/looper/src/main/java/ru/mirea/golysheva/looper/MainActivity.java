package ru.mirea.golysheva.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.golysheva.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString("RESULT");
                Log.d("MainActivity", result);
            }
        };

        myLooper = new MyLooper(mainHandler);
        myLooper.start();

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ageStr = binding.editTextAge.getText().toString().trim();
                String job = binding.editTextJob.getText().toString().trim();
                if (ageStr.isEmpty() || job.isEmpty()) {
                    Log.d("MainActivity", "Пожалуйста, заполните оба поля.");
                    return;
                }
                int age = Integer.parseInt(ageStr);

                if (myLooper.mHandler == null) {
                    Log.d("MainActivity", "Подождите, поток ещё не готов.");
                    return;
                }

                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("KEY_AGE", age);
                bundle.putString("KEY_JOB", job);
                msg.setData(bundle);
                myLooper.mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}