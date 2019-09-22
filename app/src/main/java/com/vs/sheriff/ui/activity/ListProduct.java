package com.vs.sheriff.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vs.sheriff.R;
import com.vs.sheriff.controller.adapters.ProductAdapter;
import com.vs.sheriff.controller.database_room.DatabaseRoom;
import com.vs.sheriff.controller.database_room.entity.ProductEntity;

import java.util.List;

public class ListProduct extends AppCompatActivity {

    private ListView lvInformacoes;
    private FloatingActionButton fabAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        initComponents();
    }

    private void initComponents() {
        lvInformacoes = findViewById(R.id.lvInformacoes);
        fabAdicionar = findViewById(R.id.fabAdicionar);

        fabAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListProduct.this, NewProduct.class));
            }
        });

        lvInformacoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductEntity item = (ProductEntity) lvInformacoes.getAdapter().getItem(position);
                Intent intent = new Intent(ListProduct.this, NewProduct.class);
                intent.putExtra(NewProduct.ID, item.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getList();
    }

    private void getList() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<ProductEntity> universidadeCidadeEntities = DatabaseRoom.getInstance(getApplicationContext()).productDao().getAll();
                lvInformacoes.post(new Runnable() {
                    @Override
                    public void run() {
                        lvInformacoes.setAdapter(new ProductAdapter(ListProduct.this, universidadeCidadeEntities));
                    }
                });
            }
        });
    }
}
