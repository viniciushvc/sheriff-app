package com.vs.sheriff.ui.utils;

import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

public abstract class OnSingleclickListener implements View.OnClickListener {
    private long MIN_CLICK_INTERVAL;
    private long mLastClickTime;
    public boolean processando = false;

    public abstract void onSingleClick(View v);

    public OnSingleclickListener() {
        MIN_CLICK_INTERVAL = 1000;
    }

    public OnSingleclickListener(long click_interval) {
        MIN_CLICK_INTERVAL = click_interval;
    }

    @Override
    public final void onClick(final View v) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        //
        if (processando) {
            return;
        } else {
            if (elapsedTime <= MIN_CLICK_INTERVAL)
                return;
            //
            mLastClickTime = currentClickTime;
            processando = true;
            onSingleClick(v);
            //
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    processando = false;
                }
            }, MIN_CLICK_INTERVAL);

        }
    }
}