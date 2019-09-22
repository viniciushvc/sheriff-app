package com.vs.sheriff.ui.activity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vs.sheriff.R;
import com.vs.sheriff.controller.database_room.DatabaseRoom;
import com.vs.sheriff.controller.database_room.dao.ProductDao;
import com.vs.sheriff.controller.database_room.entity.ProductEntity;
import com.vs.sheriff.ui.dialogs.PopupInfo;

public class NewProduct extends AppCompatActivity {
    public Handler handler = new Handler();

    private ProductEntity productEntity;

    public static final String ID = "";

    private TextInputLayout ilName;
    private TextInputEditText etName;
    private TextInputLayout ilBarcode;
    private TextInputEditText etBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_product);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                findProduct();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initComponents();
                    }
                });
            }
        });
    }

    private void findProduct() {
        Long codigo = getIntent().getLongExtra(ID, -1);

        productEntity = DatabaseRoom.getInstance(getApplicationContext()).productDao().selectById(codigo);
    }

    private void initComponents() {
        ilName = findViewById(R.id.ilName);
        etName = findViewById(R.id.etName);
        ilBarcode = findViewById(R.id.ilBarcode);
        etBarcode = findViewById(R.id.etPassword);

        FloatingActionButton fabConfirmar = findViewById(R.id.fbOK);
        FloatingActionButton fabDeletar = findViewById(R.id.fbDelete);

        fabConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        if (productEntity == null) {
            productEntity = new ProductEntity();
            fabDeletar.setVisibility(View.INVISIBLE);
        } else {
            carregaValores();

            fabDeletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete();
                }
            });
        }

    }

    private void confirm() {
        if (validation()) {
            save();
        }
    }

    private boolean validation() {
        if (etName.getText().toString().trim().length() == 0) {
            ilName.setError("Informe o nome");
            return false;
        }

        if (etBarcode.getText().toString().trim().length() == 0) {
            ilBarcode.setError("Informe o código de barras");
            return false;
        }

        return true;
    }

    private void save() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ProductDao productDao = DatabaseRoom.getInstance(getApplicationContext()).productDao();

                fillValues();

                if (productEntity == null)
                    add(productDao);
                else
                    update(productDao);

                closeActivity();
            }
        });
    }

    private void add(ProductDao productDao) {
        try {
            productDao.insert(productEntity);
        } catch (SQLiteConstraintException ex) {
            PopupInfo.mostraMensagem(NewProduct.this, handler, "Código já existe");
        }
    }

    private void update(ProductDao productDao) {
        productDao.update(productEntity);
    }

    private void delete() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    DatabaseRoom.getInstance(getApplicationContext()).productDao().delete(productEntity);
                    closeActivity();
                } catch (SQLiteConstraintException ex) {
                    PopupInfo.mostraMensagem(NewProduct.this, handler, "Erro ao remover");
                }
            }
        });
    }

    private void fillValues() {
        productEntity.setName(etName.getText().toString().trim());
        productEntity.setBarcode(etBarcode.getText().toString().trim());
    }

    private void closeActivity() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }

    private void carregaValores() {
        etName.setText(productEntity.getName());
        etBarcode.setText(productEntity.getBarcode());
    }
}
