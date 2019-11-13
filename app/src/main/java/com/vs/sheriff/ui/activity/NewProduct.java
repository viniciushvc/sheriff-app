package com.vs.sheriff.ui.activity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
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

    private FloatingActionButton fbConfirm;
    private FloatingActionButton fbDelete;

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
                        initEvents();
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
        etBarcode = findViewById(R.id.etBarcode);

        fbConfirm = findViewById(R.id.fbConfirm);
        fbDelete = findViewById(R.id.fbDelete);
    }

    private void initEvents() {

        fbConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        if (productEntity == null) {
            productEntity = new ProductEntity();
            fbDelete.setVisibility(View.INVISIBLE);
        } else {
            setValues();

            fbDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete();
                }
            });
        }

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

        etBarcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ilBarcode.setError(null);
            }
        });
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
        if (validation()) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    ProductDao productDao = DatabaseRoom.getInstance(getApplicationContext()).productDao();

                    fillValues();

                    if (productEntity.getId() == null)
                        add(productDao);
                    else
                        update(productDao);

                    closeActivity();
                }
            });
        }
    }

    private void add(ProductDao productDao) {
        try {
            productDao.insert(productEntity);
        } catch (SQLiteConstraintException ex) {
            PopupInfo.showMessage(NewProduct.this, handler, "Código já existe");
        }
    }

    private void update(ProductDao productDao) {
        productDao.update(productEntity);
    }

    private void delete() {
        new AlertDialog.Builder(this)
                .setMessage("Deseja remover o produto?")
                .setCancelable(true)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DatabaseRoom.getInstance(getApplicationContext()).productDao().delete(productEntity);
                                    closeActivity();
                                } catch (SQLiteConstraintException ex) {
                                    PopupInfo.showMessage(NewProduct.this, handler, "Erro ao remover");
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("Não", null)
                .show();
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

    private void setValues() {
        etName.setText(productEntity.getName());
        etBarcode.setText(productEntity.getBarcode());
    }
}
