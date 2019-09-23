package com.vs.sheriff.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MessageInfo extends DialogFragment {
    public static final String TITULO = "";
    public static final String MENSAGEM = "";

    public static void mostraMensagem(AppCompatActivity activity, String titulo, String mensagem) {
        MessageInfo messageInfo = new MessageInfo();
        Bundle arguments = new Bundle();
        arguments.putString(TITULO, titulo);
        arguments.putString(MENSAGEM, mensagem);
        messageInfo.setArguments(arguments);
        messageInfo.show(activity.getSupportFragmentManager(), MessageInfo.class.toString());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        getArguments().getString(TITULO);
        getArguments().getString(MENSAGEM);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getArguments().getString(TITULO));
        builder.setMessage(getArguments().getString(MENSAGEM));
        builder.setPositiveButton("OK", null);

        return builder.create();
    }
}
