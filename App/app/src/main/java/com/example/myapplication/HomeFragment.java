
package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    OkHttpClient client;
    String baseUrl;
    TextView temperatureText;
    TextView humidityText;

    public HomeFragment(OkHttpClient client, String baseUrl) {
        this.client = client;
        this.baseUrl = baseUrl;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        temperatureText = view.findViewById(R.id.info_text_Temperature);
        humidityText = view.findViewById(R.id.info_text_Humidity);

        Request request = new Request.Builder().url(baseUrl + "/home").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    final JsonObject entity = gson.fromJson(response.body().string(), JsonObject.class);
                    getActivity().runOnUiThread(() -> {
                        temperatureText.setText(entity.get("temperature").getAsString() + "°C");
                        humidityText.setText(entity.get("humidity").getAsString() + "%");
                    });
                }
            }
        });

        return view;
    }
}
