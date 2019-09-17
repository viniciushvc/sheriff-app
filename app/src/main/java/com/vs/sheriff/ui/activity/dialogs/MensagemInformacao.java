package com.vs.sheriff.ui.activity.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.vs.sheriff.R;

public class MensagemInformacao extends DialogFragment
{
    public static final String TITULO = "";
    public static final String MENSAGEM = "";

    public MensagemInformacao()
    {}

    public static void mostraMensagem(AppCompatActivity activity, String titulo, String mensagem)
    {
        MensagemInformacao mensagemInformacao = new MensagemInformacao();
        Bundle arguments = new Bundle();
        arguments.putString(TITULO, titulo);
        arguments.putString(MENSAGEM, mensagem);
        mensagemInformacao.setArguments(arguments);
        mensagemInformacao.show(activity.getSupportFragmentManager(), MensagemInformacao.class.toString());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        getArguments().getString(TITULO);
        getArguments().getString(MENSAGEM);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getArguments().getString(TITULO));
        builder.setMessage(getArguments().getString(MENSAGEM));
        builder.setPositiveButton("OK", null);
        return builder.create();
    }
}
