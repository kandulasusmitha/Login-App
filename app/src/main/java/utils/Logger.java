package utils;

import static constants.StaticConstants.SHOW_LOG;

import android.util.Log;

public class Logger {



    public static void debug(String Tag, String message) {
        if (SHOW_LOG && message != null)
            Log.d(Tag, message);
    }

    public static void info(String Tag, String message) {
        if (SHOW_LOG && message != null)
            Log.i(Tag, message);
    }

    public static void error(String Tag, String message) {
        if (SHOW_LOG && message != null)
            Log.e(Tag, message);
    }

    public static void warn(String Tag, String message) {
        if (SHOW_LOG && message != null)
            Log.w(Tag, message);
    }
    public static void logException(Exception message) {
        if (SHOW_LOG && message != null)
            message.printStackTrace();
    }



}
