package ru.mirea.golysheva.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {
    public Handler mHandler;          // для приёма сообщений из UI
    private Handler mainHandler;      // хендлер главного потока

    public MyLooper(Handler mainThreadHandler) {
        this.mainHandler = mainThreadHandler;
    }

    @Override
    public void run() {
        Log.d("MyLooper", "Thread started");
        Looper.prepare();

        // 2. Создаём Handler, связанный с этим Looper’ом
        mHandler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // 3. Получаем данные
                Bundle data = msg.getData();
                int age = data.getInt("KEY_AGE");
                String job = data.getString("KEY_JOB");

                Log.d("MyLooper", "Received age=" + age + ", job=" + job);

                // 4. Задержка = возраст(секунды)
                try {
                    Thread.sleep(age * 1000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // 5. Формируем ответ в главный поток
                Message outMsg = Message.obtain();
                Bundle bundle = new Bundle();
                String result = String.format(
                        "Через %d секунд задержки: вам %d лет и вы работаете %s",
                        age, age, job
                );
                bundle.putString("RESULT", result);
                outMsg.setData(bundle);

                mainHandler.sendMessage(outMsg);
            }
        };

        // 6. Запускаем цикл обработки сообщений
        Looper.loop();
    }
}