package ru.mirea.golysheva.mireaproject.ui.background;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        for (int i = 1; i <= 5; i++) {
            try {
                Log.d("MyWorker", "Шаг " + i + " из 5");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return Result.failure();
            }
        }
        Log.d("MyWorker", "Задача завершена");
        return Result.success();
    }
}
