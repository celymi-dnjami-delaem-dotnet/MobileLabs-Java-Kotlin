package com.dmyaniuk.lb_3;

import android.widget.Toast;

public class ToastHandler {
    public static void showToast(MainActivity activity, String message) {
        Toast toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
