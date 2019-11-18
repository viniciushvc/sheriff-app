package com.vs.sheriff.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vs.sheriff.R;
import com.vs.sheriff.controller.database_room.DatabaseRoom;
import com.vs.sheriff.controller.database_room.entity.UserEntity;
import com.vs.sheriff.ui.dialogs.PopupInfo;
import com.vs.sheriff.ui.utils.OnSingleClickListener;

public class Login extends AppCompatActivity {
    public Handler handler = new Handler();

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;

    private TextView tvNewAccount;

    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initComponents();
                        initEvents();
                        getUserLogin();
                    }
                });
            }
        });
    }

    private void initComponents() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        tvNewAccount = findViewById(R.id.tvNewAccount);
    }

    private void initEvents() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        tvNewAccount.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                startActivity(new Intent(Login.this, NewLogin.class));
            }
        });
    }

    private void saveUserLogin(String user){
        SharedPreferences sharedPreferences = getSharedPreferences("userLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER", user);
        editor.apply();
    }

    private void getUserLogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("userLogin", MODE_PRIVATE);
        String usuario = sharedPreferences.getString("USER", "");

        etEmail.setText(usuario);
    }

    private void login() {
        if (validation()) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    String email = etEmail.getText().toString();
                    String password = etPassword.getText().toString();

                    UserEntity user = DatabaseRoom.getInstance(getApplicationContext()).userDao().login(email, password);

                    if (user != null) {
                        saveUserLogin(user.getEmail());

                        startActivity(new Intent(Login.this, Main.class));

                        finish();
                    } else
                        PopupInfo.showMessage(Login.this, handler, "Login inválido");
                }
            });
        }
    }

    private boolean validation() {
        if (etEmail.getText().toString().trim().length() == 0) {
            etEmail.requestFocus();
            return false;
        }

        if (etPassword.getText().toString().trim().length() == 0) {
            etPassword.requestFocus();
            return false;
        }

        return true;
    }
}