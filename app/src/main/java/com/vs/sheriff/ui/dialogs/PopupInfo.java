package com.vs.sheriff.ui.dialogs;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class PopupInfo {
    public static void showMessage(final Context context, Handler handler, final String mensagem) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                showMessage(context, mensagem);
            }
        });
    }

    public static void showMessage(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
