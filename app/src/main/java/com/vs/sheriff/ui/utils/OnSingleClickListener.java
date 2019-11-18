package com.vs.sheriff.ui.utils;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

public abstract class OnSingleClickListener implements View.OnClickListener {
    private boolean loading = false;

    public abstract void onSingleClick(View v);

    @Override
    public final void onClick(final View v) {
        long elapsedTime = SystemClock.uptimeMillis();

        if (!loading) {
            long CLICK_INTERVAL = 1000;

            if (elapsedTime > CLICK_INTERVAL) {
                loading = true;
                onSingleClick(v);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading = false;
                    }
                }, CLICK_INTERVAL);
            }
        }
    }
}