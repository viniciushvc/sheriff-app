package com.vs.sheriff.ui.activity;

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

    private TextView tvId;

    public static final String EXTRA_CODIGO = "";

    private TextInputLayout tilName;
    private TextInputEditText etName;

    private TextInputLayout tilBarcode;
    private TextInputEditText etBarcode;

    private ProductEntity productEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Long codigo = getIntent().getLongExtra(EXTRA_CODIGO, -1);

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
        tilName = findViewById(R.id.tilName);
        etName = findViewById(R.id.etName);
        tilBarcode = findViewById(R.id.tilBarcode);
        etBarcode = findViewById(R.id.etBarcode);

        FloatingActionButton fabConfirmar = findViewById(R.id.fabConfirmar);
        FloatingActionButton fabDeletar = findViewById(R.id.fabDeletar);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tilName.setError(null);
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

        if (etName.getText().toString().trim().length() == 0) {
            tilName.setError("Informe o nome");
            retorno = false;
        }

        if (etBarcode.getText().toString().trim().length() == 0) {
            tilBarcode.setError("Informe o código de barras");
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

                        productDao.insert(productEntity);

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
        productEntity.setId(1L);
        productEntity.setName(etName.getText().toString().trim());
        productEntity.setBarcode(etBarcode.getText().toString().trim());
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
