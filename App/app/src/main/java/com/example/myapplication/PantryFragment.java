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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PantryFragment extends Fragment {

    OkHttpClient client;
    String baseUrl;

    PantryAdapter adapter;

    public PantryFragment(OkHttpClient client, String baseUrl) {
        this.client = client;
        this.baseUrl = baseUrl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry, container, false);

        Request request = new Request.Builder().url(baseUrl + "/pantry").build();

        RecyclerView recyclerView = view.findViewById(R.id.pantryList);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<JsonObject>>() {}.getType();
                    ArrayList<JsonObject> productList = gson.fromJson(response.body().string(), listType);

                    getActivity().runOnUiThread(() -> {
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        adapter = new PantryAdapter(productList);
                        recyclerView.setAdapter(adapter);
                    });

                }
            }
        });

        return view;
    }
}
