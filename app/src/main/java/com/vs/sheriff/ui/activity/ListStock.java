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
import com.vs.sheriff.controller.adapters.StockAdapter;
import com.vs.sheriff.controller.database_room.DatabaseRoom;
import com.vs.sheriff.controller.database_room.entity.StockEntity;

import java.util.List;

public class ListStock extends AppCompatActivity {

    private ListView listStock;
    private FloatingActionButton fbNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stock);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        listStock = findViewById(R.id.listStock);
        fbNew = findViewById(R.id.fbNew);
    }

    private void initEvents() {
        fbNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListStock.this, NewStock.class));
            }
        });

        listStock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StockEntity item = (StockEntity) listStock.getAdapter().getItem(position);
                Intent intent = new Intent(ListStock.this, NewStock.class);
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
                final List<StockEntity> list = DatabaseRoom.getInstance(getApplicationContext()).stockDao().getAll();
                listStock.post(new Runnable() {
                    @Override
                    public void run() {
                        listStock.setAdapter(new StockAdapter(ListStock.this, list));
                    }
                });
            }
        });
    }
}
