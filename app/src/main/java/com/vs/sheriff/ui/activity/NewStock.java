package com.vs.sheriff.ui.activity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vs.sheriff.R;
import com.vs.sheriff.controller.database_room.DatabaseRoom;
import com.vs.sheriff.controller.database_room.dao.StockDao;
import com.vs.sheriff.controller.database_room.entity.ProductEntity;
import com.vs.sheriff.controller.database_room.entity.StockEntity;
import com.vs.sheriff.ui.dialogs.PopupInfo;

import java.util.List;

public class NewStock extends AppCompatActivity {
    public static final String ID = "";

    public Handler handler = new Handler();

    private StockEntity stockEntity;

    private Spinner spProduct;
    private TextInputEditText etStreet;
    private TextInputEditText etFloor;
    private TextInputEditText etColumn;
    private TextInputEditText etNote;

    private FloatingActionButton fbConfirm;
    private FloatingActionButton fbDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_stock);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                findProduct();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initComponents();
                        initEvents();
                        initProducts();
                    }
                });
            }
        });
    }

    private void findProduct() {
        Long codigo = getIntent().getLongExtra(ID, -1);

        stockEntity = DatabaseRoom.getInstance(getApplicationContext()).stockDao().selectById(codigo);
    }

    private void initComponents() {
        spProduct = findViewById(R.id.spProduct);
        etStreet = findViewById(R.id.etStreet);
        etFloor = findViewById(R.id.etFloor);
        etColumn = findViewById(R.id.etColumn);
        etNote = findViewById(R.id.etNote);

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

        if (stockEntity == null) {
            stockEntity = new StockEntity();
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
    }

    private void initProducts() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<ProductEntity> productEntity = DatabaseRoom.getInstance(getApplicationContext()).productDao().getAll();
                productEntity.add(0, new ProductEntity());
                spProduct.post(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter adapter = new ArrayAdapter(NewStock.this, android.R.layout.simple_spinner_item, productEntity);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spProduct.setAdapter(adapter);
                    }
                });
            }
        });


    }


    private boolean validation() {
        if (spProduct.getSelectedItemPosition() <= 0) {
            PopupInfo.showMessage(this, "Selecione um produto");
            return false;
        }

        return true;
    }

    private void save() {
        if (validation()) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    StockDao stockDao = DatabaseRoom.getInstance(getApplicationContext()).stockDao();

                    fillValues();

                    if (stockEntity.getId() == null)
                        add(stockDao);
                    else
                        update(stockDao);

                    closeActivity();
                }
            });
        }
    }

    private void add(StockDao stockDao) {
        try {
            stockDao.insert(stockEntity);
        } catch (SQLiteConstraintException ex) {
            PopupInfo.showMessage(NewStock.this, handler, "Código já existe");
        }
    }

    private void update(StockDao stockDao) {
        stockDao.update(stockEntity);
    }

    private void delete() {
        new AlertDialog.Builder(this)
                .setMessage("Deseja remover do estoque?")
                .setCancelable(true)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DatabaseRoom.getInstance(getApplicationContext()).stockDao().delete(stockEntity);
                                    closeActivity();
                                } catch (SQLiteConstraintException ex) {
                                    PopupInfo.showMessage(NewStock.this, handler, "Erro ao remover");
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    private void fillValues() {
        stockEntity.setStreet(etStreet.getText().toString().trim());
        stockEntity.setFloor(etFloor.getText().toString().trim());
        stockEntity.setColumn(etColumn.getText().toString().trim());
        stockEntity.setColumn(etNote.getText().toString().trim());

        for (int i = 1; i < spProduct.getCount(); i++) {
            if (((ProductEntity) spProduct.getItemAtPosition(i)).getId().equals(new Long(stockEntity.getIdProduct()))) {
                spProduct.setSelection(i, true);
            }
        }
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
        spProduct.setSelection(stockEntity.getIdProduct(), true);
        etStreet.setText(stockEntity.getStreet());
        etFloor.setText(stockEntity.getFloor());
        etColumn.setText(stockEntity.getColumn());
        etNote.setText(stockEntity.getNote());
    }
}

