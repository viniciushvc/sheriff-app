package com.vs.sheriff.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.vs.sheriff.R;
import com.vs.sheriff.ui.dialogs.PopupInfo;
import com.vs.sheriff.ui.utils.OnSingleclickListener;

public class Main extends AppCompatActivity {

    Handler handler = new Handler();

    private Button btProduct;
    private Button btStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        btProduct = findViewById(R.id.btProduct);
        btStock = findViewById(R.id.btStock);
    }

    private void initEvents() {
        btProduct.setOnClickListener(new OnSingleclickListener() {
            @Override
            public void onSingleClick(View view) {
                startActivity(new Intent(Main.this, ListProduct.class));
            }
        });

        btStock.setOnClickListener(new OnSingleclickListener() {
            @Override
            public void onSingleClick(View view) {
                PopupInfo.mostraMensagem(Main.this, handler, "Em breve");
            }
        });
    }
}
