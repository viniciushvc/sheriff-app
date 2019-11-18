package com.vs.sheriff.ui.activity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vs.sheriff.R;
import com.vs.sheriff.controller.database_room.DatabaseRoom;
import com.vs.sheriff.controller.database_room.dao.UserDao;
import com.vs.sheriff.controller.database_room.entity.UserEntity;
import com.vs.sheriff.ui.dialogs.PopupInfo;

import java.util.regex.Pattern;

public class NewLogin extends AppCompatActivity {
    public Handler handler = new Handler();

    private UserEntity userEntity = new UserEntity();

    private TextInputLayout ilName;
    private TextInputLayout ilEmail;
    private TextInputLayout ilPassword;

    private TextInputEditText etName;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;

    private Button btCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initComponents();
                        initEvents();
                    }
                });
            }
        });
    }

    private void initComponents() {
        ilName = findViewById(R.id.ilName);
        etName = findViewById(R.id.etName);
        ilEmail = findViewById(R.id.ilEmail);
        etEmail = findViewById(R.id.etEmail);
        ilPassword = findViewById(R.id.ilPassword);
        etPassword = findViewById(R.id.etPassword);
        btCreateAccount = findViewById(R.id.btLogin);
    }

    private void initEvents() {
        btCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation())
                    add();
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ilName.setError(null);
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ilEmail.setError(null);
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ilPassword.setError(null);
            }
        });
    }

    private void add() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                UserDao userDao = DatabaseRoom.getInstance(getApplicationContext()).userDao();

                fillValues();

                try {
                    userDao.insert(userEntity);

                    closeActivity();
                } catch (SQLiteConstraintException ex) {
                    PopupInfo.showMessage(NewLogin.this, handler, "Email já cadastrado");
                }
            }
        });
    }

    private boolean validation() {
        if (etName.getText().toString().trim().length() == 0) {
            etName.requestFocus();
            ilName.setError("Preencha seu nome");
            return false;
        }

        if (etEmail.getText().toString().trim().length() == 0) {
            etEmail.requestFocus();
            ilEmail.setError("Preencha seu email");
            return false;
        }

        if (!validEmail(etEmail.getText().toString())) {
            etEmail.requestFocus();
            ilEmail.setError("Email inválido");
            return false;
        }

        if (etPassword.getText().toString().trim().length() == 0) {
            etPassword.requestFocus();
            ilPassword.setError("Preencha sua senha");
            return false;
        }

        return true;
    }

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void fillValues() {
        userEntity.setName(etName.getText().toString().trim());
        userEntity.setEmail(etEmail.getText().toString().trim());
        userEntity.setPassword(etPassword.getText().toString().trim());
    }

    private void closeActivity() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

}