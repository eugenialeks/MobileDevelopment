package ru.mirea.golysheva.timeservice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "GetTimeActivity";
    private final String host = "time-a.nist.gov";
    private final int port = 13;
    private final int CONNECT_TIMEOUT_MS = 5000;
    private final int READ_TIMEOUT_MS = 5000;

    private TextView textDate;
    private TextView textTime;
    private Button buttonGetTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDate = findViewById(R.id.text_date);
        textTime = findViewById(R.id.text_time);
        buttonGetTime = findViewById(R.id.button_get_time);

        buttonGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetTimeTask().execute();
            }
        });
    }


    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        private String errorMessage = null;

        @Override
        protected String doInBackground(Void... voids) {
            Socket socket = null;
            try {

                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), CONNECT_TIMEOUT_MS);

                socket.setSoTimeout(READ_TIMEOUT_MS);

                BufferedReader reader = SocketUtils.getReader(socket);

                String firstLine = reader.readLine();
                if (firstLine == null) {
                    throw new IOException("Первая строка от сервера оказалась null");
                }

                String secondLine = reader.readLine();
                if (secondLine == null) {
                    throw new IOException("Вторая строка от сервера оказалась null");
                }

                Log.d(TAG, "Сервер вернул: " + secondLine);
                return secondLine;

            } catch (SocketTimeoutException ste) {
                ste.printStackTrace();
                errorMessage = "Таймаут подключения/чтения: " + ste.getMessage();
                Log.e(TAG, "SocketTimeoutException: " + ste.getMessage());
            } catch (IOException ioe) {
                ioe.printStackTrace();
                errorMessage = "Ошибка при подключении/чтении: " + ioe.getMessage();
                Log.e(TAG, "IOException: " + ioe.getMessage());
            } finally {
                if (socket != null && !socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Не удалось закрыть сокет: " + e.getMessage());
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {
                if (errorMessage != null) {
                    textDate.setText(errorMessage);
                } else {
                    textDate.setText("Не удалось получить ответ от сервера");
                }
                textTime.setText("");
                return;
            }

            String[] parts = result.split(" ");
            if (parts.length < 3) {
                textDate.setText("Неверный формат ответа: " + result);
                textTime.setText("");
                return;
            }

            String datePart = parts[1];
            String timePart = parts[2];
            String combinedUtc = datePart + " " + timePart;

            SimpleDateFormat parser = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault());
            parser.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date utcDate = null;
            try {
                utcDate = parser.parse(combinedUtc);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (utcDate == null) {
                textDate.setText("Дата (UTC): " + datePart);
                textTime.setText("Время (UTC): " + timePart);
                return;
            }

            TimeZone moscowTZ = TimeZone.getTimeZone("Europe/Moscow");

            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            dateFormatter.setTimeZone(moscowTZ);
            timeFormatter.setTimeZone(moscowTZ);

            String formattedDate = dateFormatter.format(utcDate);
            String formattedTime = timeFormatter.format(utcDate);

            textDate.setText("Дата: " + formattedDate);
            textTime.setText("Время: " + formattedTime);
        }
    }
}
