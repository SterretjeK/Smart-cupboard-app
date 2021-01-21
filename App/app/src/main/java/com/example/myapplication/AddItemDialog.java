package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class AddItemDialog extends Dialog {

    public Activity context;
    public Dialog dialog;
    public EditText productName;
    public Button submit;

    public AddItemDialog(Activity a) {
        super(a);
        this.context = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_dialog);
        submit = findViewById(R.id.edit_product);
        productName = (Button) findViewById(R.id.add_product);
    }
}