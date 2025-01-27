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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        user_field = findViewById(R.id.user_field);
        result_logo = findViewById(R.id.result_logo);
        feels_like_logo = findViewById(R.id.feels_like_logo);
        description_logo = findViewById(R.id.description_logo);
        wind_speed = findViewById(R.id.wind_speed);
        Button main_btn = findViewById(R.id.main_btn);

        main_btn.setOnClickListener(v -> {
            if(user_field.getText().toString().trim().isEmpty())
                Toast.makeText(MainActivity.this, R.string.no_input, Toast.LENGTH_LONG).show();
            else {
                String city = user_field.getText().toString();
                String key = "53c93ba27f0359989e46ed6585e46719";
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key + "&units=metric&lang=ru";

                new GetURLData().execute(url);
            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    private class GetURLData extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
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

            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                int temp = jsonObject.getJSONObject("main").getInt("temp");
                int feelsLike = jsonObject.getJSONObject("main").getInt("feels_like");
                double speed = jsonObject.getJSONObject("wind").getDouble("speed");
                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject weather = (JSONObject) weatherArray.get(0);
                String description = weather.getString("description");
                result_logo.setText("Температура: " + temp + " °C");
                feels_like_logo.setText("Ощущается как: " + feelsLike + " °C");
                description_logo.setText("Описание: " + description);
                wind_speed.setText("Скорость ветра: " + speed + " м/с");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}