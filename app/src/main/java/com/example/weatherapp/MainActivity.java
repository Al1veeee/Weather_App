
package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText user_field;
    private TextView result_logo, feels_like_logo, description_logo, wind_speed;
    private final String API_KEY = "53c93ba27f0359989e46ed6585e46719"; // Выносим ключ в константу

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Инициализация элементов UI
        user_field = findViewById(R.id.user_field);
        result_logo = findViewById(R.id.result_logo);
        feels_like_logo = findViewById(R.id.feels_like_logo);
        description_logo = findViewById(R.id.description_logo);
        wind_speed = findViewById(R.id.wind_speed);
        Button main_btn = findViewById(R.id.main_btn);

        main_btn.setOnClickListener(v -> {
            String city = user_field.getText().toString().trim(); // Получаем текст, убираем пробелы
            if (city.isEmpty()) { // Проверяем, если поле пустое
                Toast.makeText(MainActivity.this, R.string.no_input, Toast.LENGTH_LONG).show();
                return; // Выходим, если поле пустое
            }
            // Формируем URL-адрес для запроса погоды
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                    "&appid=" + API_KEY + "&units=metric&lang=ru";
            new GetURLData().execute(url); // Выполняем запрос к API
        });
    }
    // Вложенный класс для асинхронного запроса
    @SuppressLint("StaticFieldLeak")
    private class GetURLData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Задаём начальные значения для элементов, пока данные загружаются
            result_logo.setText("Ожидайте...");
            feels_like_logo.setText("");
            description_logo.setText("");
            wind_speed.setText("");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");
                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
                return null; // Возвращаем null в случае ошибки
            } finally {
                if(connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result == null) { // Если произошла ошибка при загрузке данных
                result_logo.setText(R.string.no_data); // Выводим сообщение об ошибке
                return; // Завершаем метод
            }
            try {
                JSONObject jsonResult = new JSONObject(result);
                if (jsonResult.has("cod") && jsonResult.getInt("cod") != 200) {
                    // Обрабатываем ошибки API OpenWeatherMap (например, "город не найден")
                    result_logo.setText(jsonResult.getString("message"));
                    feels_like_logo.setText("");
                    description_logo.setText("");
                    wind_speed.setText("");
                    return;
                }

                JSONObject main = jsonResult.getJSONObject("main");
                double temp = main.getDouble("temp");
                double feelsLike = main.getDouble("feels_like");
                JSONObject weather = jsonResult.getJSONArray("weather").getJSONObject(0);
                String description = weather.getString("description");
                JSONObject wind = jsonResult.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                String name = jsonResult.getString("name");
                result_logo.setText(name + ", " + String.format("%.1f", temp) + "°C");
                feels_like_logo.setText("Ощущается как: " + String.format("%.1f", feelsLike) + "°C");
                description_logo.setText(description);
                wind_speed.setText("Ветер: " + String.format("%.1f", windSpeed) + " м/с");
            } catch (JSONException e) {
                e.printStackTrace();
                // Обрабатываем ошибки парсинга JSON
                result_logo.setText(R.string.no_data);
                feels_like_logo.setText("");
                description_logo.setText("");
                wind_speed.setText("");
            }
        }
    }
}
