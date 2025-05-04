package ru.mirea.golysheva.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import ru.mirea.golysheva.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread mainThread = Thread.currentThread();
        binding.textViewResult.setText("Имя текущего потока: " + mainThread.getName());

        mainThread.setName("БСБО-09-22, № по списку: 6, Фильм: Голодные игры");
        binding.textViewResult.append("\nНовое имя потока: " + mainThread.getName());

        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));
        Log.d(MainActivity.class.getSimpleName(), "Group: " + mainThread.getThreadGroup());

        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int threadNumber = counter++;
                        Log.d("ThreadProject", String.format(
                                "Запущен поток № %d студентом группы БСБО-09-22, номер по списку 6", threadNumber));

                        try {
                            int totalClasses = Integer.parseInt(binding.editTotalClasses.getText().toString());
                            int days = Integer.parseInt(binding.editSchoolDays.getText().toString());

                            long endTime = System.currentTimeMillis() + 20000;
                            while (System.currentTimeMillis() < endTime) {
                                synchronized (this) {
                                    wait(endTime - System.currentTimeMillis());
                                }
                            }

                            double avgPerDay = (double) totalClasses / days;
                            runOnUiThread(() -> binding.textViewResult.setText(
                                    "Среднее число пар в день: " + String.format("%.2f", avgPerDay)));

                            Log.d("ThreadProject", "Выполнен поток № " + threadNumber);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}