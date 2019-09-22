package com.vs.sheriff.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vs.sheriff.R;
import com.vs.sheriff.controller.database_room.DatabaseRoom;
import com.vs.sheriff.controller.database_room.entity.UserEntity;
import com.vs.sheriff.ui.dialogs.PopupInfo;
import com.vs.sheriff.ui.utils.OnSingleclickListener;

public class Login extends AppCompatActivity {

    private TextInputLayout ilEmail;
    private TextInputLayout ilPassword;

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;

    private TextView tvNewAccount;

    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        ilEmail = findViewById(R.id.ilEmail);
        etEmail = findViewById(R.id.etEmail);
        ilPassword = findViewById(R.id.ilPassword);
        etPassword = findViewById(R.id.etPassword);
    }

    private void initEvents() {
        btLogin = findViewById(R.id.btLogin);
        tvNewAccount = findViewById(R.id.tvNewAccount);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        tvNewAccount.setOnClickListener(new OnSingleclickListener(){
            @Override
            public void onSingleClick(View view) {
                startActivity(new Intent(Login.this, NewLogin.class));
            }
        });
    }

    private void login() {

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (validation()) {
            UserEntity user = DatabaseRoom.getInstance(getApplicationContext()).userDao().login(email, password);

            if (user != null)
                startActivity(new Intent(Login.this, Main.class));
            else
                PopupInfo.mostraMensagem(Login.this, "Login inválido");
        }
    }

    private boolean validation() {
        if (etEmail.getText().toString().trim().length() == 0) {
            etEmail.requestFocus();
            return false;
        }

        if (etEmail.getText().toString().length() == 0) {
            etEmail.requestFocus();
            return false;
        }

        return true;
    }

}