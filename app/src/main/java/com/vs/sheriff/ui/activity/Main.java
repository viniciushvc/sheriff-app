package com.vs.sheriff.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vs.sheriff.R;

public class Main extends AppCompatActivity {

    private Button product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        product = findViewById(R.id.product);
    }

    private void initEvents() {
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main.this, ListProduct.class));
            }
        });
    }
}
