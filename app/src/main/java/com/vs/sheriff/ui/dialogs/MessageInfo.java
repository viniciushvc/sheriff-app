package com.vs.sheriff.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MessageInfo extends DialogFragment {
    private static final String TITLE = "";
    private static final String MESSAGE = "";

    public static void showMessage(AppCompatActivity activity, String titulo, String mensagem) {
        MessageInfo messageInfo = new MessageInfo();
        Bundle arguments = new Bundle();
        arguments.putString(TITLE, titulo);
        arguments.putString(MESSAGE, mensagem);
        messageInfo.setArguments(arguments);
        messageInfo.show(activity.getSupportFragmentManager(), MessageInfo.class.toString());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        getArguments().getString(TITLE);
        getArguments().getString(MESSAGE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(getArguments().getString(TITLE));
        builder.setMessage(getArguments().getString(MESSAGE));
        builder.setPositiveButton("OK", null);

        return builder.create();
    }
}
