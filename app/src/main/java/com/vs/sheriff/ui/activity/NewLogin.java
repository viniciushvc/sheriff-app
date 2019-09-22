package com.vs.sheriff.ui.activity;

import android.database.sqlite.SQLiteConstraintException;
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
import com.vs.sheriff.ui.dialogs.PopupInfo;

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
    }

    private void initEvents() {
        btCreateAccount = findViewById(R.id.btLogin);

        btCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (validation()) {
                    UserDao userDao = DatabaseRoom.getInstance(getApplicationContext()).userDao();

                    add(userDao);
                }
            }
        });
    }

    private void add(UserDao userDao) {
        fillValues();

        try {
            userDao.insert(userEntity);

            closeActivity();
        } catch (SQLiteConstraintException ex) {
            PopupInfo.mostraMensagem(NewLogin.this, handler, "Email já cadastrado");
        }
    }

    private boolean validation() {

//        if (etName.getText().toString().trim().length() == 0) {
//            etEmail.requestFocus();
//            return false;
//        }
//
//        if (etEmail.getText().toString().trim().length() == 0) {
//            etEmail.requestFocus();
//            return false;
//        }
//
//        if (etPassword.getText().toString().length() == 0) {
//            etPassword.requestFocus();
//            return false;
//        }

        return true;
    }

    private void fillValues() {

        userEntity.setName("Vinicius");
        userEntity.setEmail("viniciusvicentini@live.com");
        userEntity.setPassword("123");

//        userEntity.setName(etName.getText().toString().trim());
//        userEntity.setEmail(etEmail.getText().toString().trim());
//        userEntity.setPassword(etPassword.getText().toString().trim());
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