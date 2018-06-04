package com.example.harshendra.newsreader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listview);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        listView.setAdapter(arrayAdapter);

        DownloadTask task = new DownloadTask();

    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = reader.read();
                }

                Log.i("URLContent", result);

                JSONArray jsonArray = new JSONArray(result);

                for (int i =0; i < jsonArray.length(); i++) {
                    Log.i("JSONItem", jsonArray.getString(i));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}


