package com.vs.sheriff.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vs.sheriff.R;
import com.vs.sheriff.controller.database_room.DatabaseRoom;
import com.vs.sheriff.controller.database_room.dao.UserDao;
import com.vs.sheriff.controller.database_room.entity.UserEntity;

public class UserProfile extends AppCompatActivity {

    public Handler handler = new Handler();

    private UserEntity userEntity = new UserEntity();

    private TextInputLayout ilName;
    private TextInputLayout ilPassword;
    private TextInputLayout ilRePassword;

    private TextInputEditText etName;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etRePassword;

    private Button btSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        ilName = findViewById(R.id.ilName);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        ilPassword = findViewById(R.id.ilPassword);
        etPassword = findViewById(R.id.etPassword);
        ilRePassword = findViewById(R.id.ilRePassword);
        etRePassword = findViewById(R.id.etRePassword);
        btSave = findViewById(R.id.btSave);
    }

    private void initEvents() {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation())
                    update();
            }
        });
    }

    private boolean validation() {
        if (etName.getText().toString().trim().length() == 0) {
            etName.requestFocus();
            ilName.setError("Preencha seu nome");
            return false;
        }

        if (etPassword.getText().toString().trim().length() == 0) {
            etPassword.requestFocus();
            ilPassword.setError("Preencha sua senha");
            return false;
        }

        if (etRePassword.getText().toString().trim().length() == 0) {
            etRePassword.requestFocus();
            ilRePassword.setError("Confirme a senha");
            return false;
        }

        if (!etRePassword.getText().equals(etPassword.getText())) {
            etRePassword.requestFocus();
            ilRePassword.setError("Senha não estão iguais");
            return false;
        }

        return true;
    }

    private void update() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                UserDao userDao = DatabaseRoom.getInstance(getApplicationContext()).userDao();

                fillValues();

                userDao.update(userEntity);
            }
        });
    }

    private void fillValues() {
        userEntity.setName(etName.getText().toString().trim());
        userEntity.setEmail(etEmail.getText().toString().trim());
        userEntity.setPassword(etPassword.getText().toString().trim());
    }
}
