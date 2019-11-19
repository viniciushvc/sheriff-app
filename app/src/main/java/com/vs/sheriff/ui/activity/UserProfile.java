package com.vs.sheriff.ui.activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
        getUserData();
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

        etRePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ilRePassword.setError(null);
            }
        });
    }

    private void getUserData() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("userLogin", MODE_PRIVATE);
                String userId = sharedPreferences.getString("ID", "");

                userEntity = DatabaseRoom.getInstance(getApplicationContext()).userDao().selectById(Long.parseLong(userId));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setValues();
                    }
                });
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

        if (!etRePassword.getText().toString().equals(etPassword.getText().toString())) {
            etRePassword.requestFocus();
            ilRePassword.setError("Senha não são iguais");
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

                PopupInfo.showMessage(UserProfile.this, handler, "Usuário atualizado");
            }
        });
    }

    private void fillValues() {
        userEntity.setName(etName.getText().toString().trim());
        userEntity.setEmail(etEmail.getText().toString().trim());
        userEntity.setPassword(etPassword.getText().toString().trim());
    }

    private void setValues() {
        etEmail.setText(userEntity.getEmail());
        etName.setText(userEntity.getName());
        etPassword.setText(userEntity.getPassword());
    }

}
