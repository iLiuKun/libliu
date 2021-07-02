package com.example.libliu;

import android.content.Context;
import android.widget.Toast;


/**
 * toast 辅助类
 */
public class ToastUtils {
    public static void s(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void l(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    private static Toast sToast;

    static void init(Context context) {
        sToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    public static void showShortToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
        }
    }

    private static void showToast(String message, int duration) {
        ThreadUtils.runOnUiThread(() -> {
            if (sToast != null) {
                sToast.setText(message);
                sToast.setDuration(duration);
                sToast.show();
            }
        });
    }
}
