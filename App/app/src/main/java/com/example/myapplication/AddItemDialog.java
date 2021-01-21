package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddItemDialog extends Dialog {

    private MainActivity context;
    private Dialog dialog;
    private EditText productName;
    private Button submit;
    private Button close;
    private OkHttpClient client;

    public AddItemDialog(MainActivity a, OkHttpClient client) {
        super(a);
        this.context = a;
        this.client = client;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_dialog);
        submit = findViewById(R.id.button_submit);
        close = findViewById((R.id.button_close));
        productName = findViewById(R.id.edit_product_name);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requestUrl = context.url + "/pantry/" + productName.getText();
                productName.setText("");
                Request request = new Request.Builder().url(requestUrl).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        context.runOnUiThread(() -> {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Successfully added product", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }else{
                            Toast.makeText(context, "Failed to add. This product might not exist.", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }});
                    }
                });
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}