package com.vs.sheriff.ui.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vs.sheriff.R;
import com.vs.sheriff.controller.database_room.DatabaseRoom;
import com.vs.sheriff.controller.database_room.dao.ProductDao;
import com.vs.sheriff.controller.database_room.entity.ProductEntity;
import com.vs.sheriff.ui.activity.dialogs.PopupInformacao;

public class NewProduct extends AppCompatActivity {
    public Handler handler = new Handler();

    public static final String EXTRA_CODIGO = "";

    private TextView tvId;
    private TextInputLayout tilNome;
    private TextInputEditText etNome;

    private ProductEntity productEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
//                Long id = getIntent().getLongExtra(tvId.toString(), -1);

                productEntity = DatabaseRoom.getInstance(getApplicationContext()).productDao().selectById(1L);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initComponents();
                    }
                });
            }
        });
    }

    private void initComponents() {
        tilNome = findViewById(R.id.tilNome);
        etNome = findViewById(R.id.etNome);

        FloatingActionButton fabConfirmar = findViewById(R.id.fabConfirmar);
        FloatingActionButton fabDeletar = findViewById(R.id.fabDeletar);

        etNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilNome.setError(null);
            }
        });

        fabConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmaTela();
            }
        });

        if (productEntity == null)
            fabDeletar.setEnabled(false);
        else {
            fabDeletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteRegistroFechaTela();
                }
            });
        }
    }

    private void confirmaTela() {
        if (!validaTela())
            return;

        salvaRegistroFechaTela();
    }

    private boolean validaTela() {
        boolean retorno = true;

        if (etNome.getText().toString().trim().length() == 0) {
            tilNome.setError("Informe o nome da universidade");
            retorno = false;
        }

        return retorno;
    }

    private void salvaRegistroFechaTela() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ProductDao productDao = DatabaseRoom.getInstance(getApplicationContext()).productDao();

                if (productEntity == null) {
                    ProductEntity productEntity = new ProductEntity();
                    preencheValores(productEntity);
                    try {
                        fechaTelaSucesso();
                    } catch (SQLiteConstraintException ex) {
                        PopupInformacao.mostraMensagem(NewProduct.this, handler, "Código já existe");
                    }
                } else {
                    preencheValores(productEntity);

                    fechaTelaSucesso();
                }
            }
        });
    }

    private void deleteRegistroFechaTela() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                fechaTelaSucesso();
            }
        });
    }

    private void preencheValores(ProductEntity productEntity) {
        productEntity.setName(etNome.getText().toString().trim());
    }

    private void fechaTelaSucesso() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        });
    }
}
