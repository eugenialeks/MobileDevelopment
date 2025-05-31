package ru.mirea.golysheva.httpurlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HttpURLConnection";

    private Button btnGetInfo;
    private TextView tvIpAddress, tvCity, tvRegion, tvCountry, tvLatitude, tvLongitude;
    private TextView tvTemperature, tvWindspeed, tvWinddirection, tvWeathercode;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetInfo      = findViewById(R.id.btn_get_info);
        tvIpAddress     = findViewById(R.id.tv_ip_address);
        tvCity          = findViewById(R.id.tv_city);
        tvRegion        = findViewById(R.id.tv_region);
        tvCountry       = findViewById(R.id.tv_country);
        tvLatitude      = findViewById(R.id.tv_latitude);
        tvLongitude     = findViewById(R.id.tv_longitude);

        tvTemperature   = findViewById(R.id.tv_temperature);
        tvWindspeed     = findViewById(R.id.tv_windspeed);
        tvWinddirection = findViewById(R.id.tv_winddirection);
        tvWeathercode   = findViewById(R.id.tv_weathercode);

        tvStatus        = findViewById(R.id.tv_status);

        btnGetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInternetAndFetch();
            }
        });
    }

    private void checkInternetAndFetch() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;

        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()) {
            tvStatus.setText("");
            tvIpAddress.setText("IP: —");
            tvCity.setText("Город: —");
            tvRegion.setText("Регион: —");
            tvCountry.setText("Страна: —");
            tvLatitude.setText("Широта: —");
            tvLongitude.setText("Долгота: —");

            tvTemperature.setText("Температура: —");
            tvWindspeed.setText("Скорость ветра: —");
            tvWinddirection.setText("Направление ветра: —");
            tvWeathercode.setText("Код погоды: —");

            new DownloadIpInfoTask().execute("https://ipinfo.io/json");
        } else {
            Toast.makeText(MainActivity.this, "Нет интернета", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadIpInfoTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvStatus.setText("Загружаем информацию об IP...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String address = strings[0]; // "https://ipinfo.io/json"
            try {
                return downloadUrl(address);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {
                tvStatus.setText("Ошибка при загрузке IP-информации.");
                return;
            }

            try {
                JSONObject json = new JSONObject(result);

                String ip      = json.optString("ip", "—");
                String city    = json.optString("city", "—");
                String region  = json.optString("region", "—");
                String country = json.optString("country", "—");
                String loc     = json.optString("loc", "—"); // например "55.7558,37.6173"

                tvIpAddress.setText("IP: " + ip);
                tvCity.setText("Город: " + city);
                tvRegion.setText("Регион: " + region);
                tvCountry.setText("Страна: " + country);

                String latitude  = "—";
                String longitude = "—";
                if (loc.contains(",")) {
                    String[] parts = loc.split(",");
                    latitude  = parts[0];
                    longitude = parts[1];
                }
                tvLatitude.setText("Широта: " + latitude);
                tvLongitude.setText("Долгота: " + longitude);

                tvStatus.setText("IP-данные загружены. Получаем погоду...");
                new DownloadWeatherTask().execute(latitude, longitude);

            } catch (JSONException e) {
                e.printStackTrace();
                tvStatus.setText("Ошибка разбора JSON от ipinfo.io");
            }
        }
    }

    private class DownloadWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvStatus.setText("Загружаем текущую погоду...");
            tvTemperature.setText("Температура: —");
            tvWindspeed.setText("Скорость ветра: —");
            tvWinddirection.setText("Направление ветра: —");
            tvWeathercode.setText("Код погоды: —");
        }

        @Override
        protected String doInBackground(String... strings) {
            String latitude  = strings[0];
            String longitude = strings[1];
            if (latitude == null || longitude == null || latitude.isEmpty() || longitude.isEmpty()) {
                return null;
            }

            try {
                String latEnc = URLEncoder.encode(latitude,  "UTF-8");
                String lonEnc = URLEncoder.encode(longitude, "UTF-8");
                String tzEnc  = URLEncoder.encode("Europe/Moscow", "UTF-8"); // "Europe%2FMoscow"

                String weatherUrl = "https://api.open-meteo.com/v1/forecast"
                        + "?latitude="       + latEnc
                        + "&longitude="      + lonEnc
                        + "&current_weather=true"
                        + "&timezone="       + tzEnc;

                Log.d(TAG, "Weather URL → " + weatherUrl);

                return downloadUrl(weatherUrl);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) {
                tvStatus.setText("Ошибка при загрузке данных о погоде.");
                return;
            }

            String trimmed = result.trim();
            if (!trimmed.startsWith("{")) {
                tvStatus.setText(trimmed);
                return;
            }

            try {
                JSONObject json = new JSONObject(trimmed);

                if (json.has("current_weather")) {
                    JSONObject current = json.getJSONObject("current_weather");

                    double temperature   = current.optDouble("temperature", Double.NaN);
                    double windspeed     = current.optDouble("windspeed", Double.NaN);
                    double winddirection = current.optDouble("winddirection", Double.NaN);
                    int weathercode      = current.optInt("weathercode", -1);

                    tvTemperature.setText(String.format("Температура: %.1f°C", temperature));
                    tvWindspeed.setText(String.format("Скорость ветра: %.1f м/с", windspeed));
                    tvWinddirection.setText(String.format("Направление ветра: %.0f°", winddirection));
                    tvWeathercode.setText("Код погоды: " + weathercode);

                    tvStatus.setText("Погода успешно получена.");
                } else {
                    tvStatus.setText("В ответе нет поля current_weather.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                tvStatus.setText("Ошибка разбора JSON от Open-Meteo");
            }
        }
    }

    private String downloadUrl(String address) throws IOException {
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();

            // Таймауты
            connection.setConnectTimeout(10_000);
            connection.setReadTimeout(10_000);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read;
                while ((read = inputStream.read()) != -1) {
                    bos.write(read);
                }
                bos.close();
                return bos.toString("UTF-8");
            } else {
                String msg = connection.getResponseMessage();
                return msg + ". Error Code: " + responseCode;
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
