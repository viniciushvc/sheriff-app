package com.vs.sheriff.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vs.sheriff.R;
import com.vs.sheriff.ui.utils.OnSingleClickListener;

public class Main extends AppCompatActivity {

    Handler handler = new Handler();

    private Button btProduct;
    private Button btStock;
    private Button btWithdraw;
    private Button btExit;

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
        btWithdraw = findViewById(R.id.btWithdraw);
        btExit = findViewById(R.id.btExit);
    }

    private void initEvents() {
        btProduct.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                startActivity(new Intent(Main.this, ListProduct.class));
            }
        });

        btStock.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                startActivity(new Intent(Main.this, ListStock.class));
            }
        });

        btWithdraw.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                startActivity(new Intent(Main.this, UserProfile.class));
            }
        });

        btExit.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                exitAccountDialog();
            }
        });
    }

    private void exitAccountDialog() {
        new AlertDialog.Builder(this)
                .setMessage("Deseja sair da sua conta?")
                .setCancelable(true)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Main.this, Login.class));

                        finish();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
}
