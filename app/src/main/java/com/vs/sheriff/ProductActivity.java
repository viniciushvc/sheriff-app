package com.vs.sheriff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout edtNome;
    private TextInputLayout edtCodigo;
    private AppCompatButton btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initComponents();
        initEvents();
    }

    private void initComponents() {
        edtNome = findViewById(R.id.edtNome);
        edtCodigo = findViewById(R.id.edtCodigo);
        btnSalvar = findViewById(R.id.btnSalvar);
    }

    private void initEvents() {
        btnSalvar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSalvar:
                confirmaTela();
                break;
            default:
                break;
        }
    }

    private void confirmaTela() {
        if (!validaTela())
            return;

        if (salvaInformacoes()) {
            this.finish();
        }
    }

    private boolean validaTela() {
        if (edtCodigo.getEditText().toString().trim().isEmpty()) {
            edtCodigo.setError("Campo não pode ser vazio");
            edtCodigo.requestFocus();
            return false;
        }

        if (edtNome.getEditText().toString().trim().isEmpty()) {
            edtCodigo.setError("Campo não pode ser vazio");
            edtNome.requestFocus();
            return false;
        }

        return true;
    }

    private boolean salvaInformacoes() {
        return true;
    }
}
