package com.ppcrong.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.socks.library.KLog;

/**
 * Utils Library
 */
public class Utils {

    /**
     * Start an intent with try-catch to avoid activity not found
     *
     * @param context
     * @param intent
     */
    public static void startSafeIntent(Context context, Intent intent) {

        try {

            context.startActivity(intent);
        } catch (Exception e) {

            KLog.i("[startSafeIntent1] start intent error...");
            KLog.i(Log.getStackTraceString(e));
        }
    }

    /**
     * Start an intent with try-catch to avoid activity not found
     *
     * @param context
     * @param action
     */
    public static void startSafeIntent(Context context, String action) {

        Intent intent = new Intent(action);
        startSafeIntent(context, intent);
    }

}
