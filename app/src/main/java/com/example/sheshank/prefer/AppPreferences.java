package com.example.sheshank.prefer;

import static com.example.sheshank.prefer.AppPrefernceConnstants.APP_REFERENCE;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {


    public String getStringValue(Context context, String key) {
        synchronized (this) {
            if (context == null) {
                return null;
            }
            SharedPreferences appPref = context.getApplicationContext().getSharedPreferences(

                    APP_REFERENCE, Context.MODE_PRIVATE);

            String gcmID = appPref.getString(key, "none");
            return gcmID;
        }
    }

    public void setStringValue(Context context, String key, String value) {

        if (context != null) {
            SharedPreferences appPref = context.getApplicationContext().getSharedPreferences(
                    APP_REFERENCE, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = appPref.edit();
            if (editor != null) {
                String newPassword = value;
                editor.putString(key, newPassword);
                editor.apply();
            }
        }

    }

    public int getIntValue(Context context, String key) {
        synchronized (this) {

            int defaultValue = 0;


            if (context == null) {
                return defaultValue;
            }
            SharedPreferences appPref = context.getApplicationContext().getSharedPreferences(
                    APP_REFERENCE, Context.MODE_PRIVATE);

            int intValue = appPref.getInt(key, defaultValue);

            return intValue;
        }
    }

    public void setIntValue(Context context, String key, int value) {

        if (context != null) {
            SharedPreferences appPref = context.getApplicationContext().getSharedPreferences(
                    APP_REFERENCE, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = appPref.edit();
            if (editor != null) {
                editor.putInt(key, value);
                editor.apply();
            }
        }

    }

    public boolean getBoolValue(Context context, String key) {
        synchronized (this) {
            if (context == null) {
                return false;
            }

            SharedPreferences appPref = context.getApplicationContext().getSharedPreferences(
                    APP_REFERENCE, Context.MODE_PRIVATE);

            boolean gcmID = appPref.getBoolean(key, false);

            return gcmID;
        }
    }

    public void setBoolValue(Context context, String key, boolean value) {
//        synchronized (this) {
        if (context != null) {
            SharedPreferences appPref = context.getApplicationContext().getSharedPreferences(
                    APP_REFERENCE, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = appPref.edit();
            if (editor != null) {
                editor.putBoolean(key, value);
                editor.apply();
            }
        }
//        }
    }

    public double getDoubleValue(Context context, String key) {
        synchronized (this) {
            if (context == null) {
                return 0;
            }
            SharedPreferences appPref = context.getApplicationContext().getSharedPreferences(
                    APP_REFERENCE, Context.MODE_PRIVATE);

            String gcmID = appPref.getString(key, "0");

            return Double.parseDouble(gcmID);
        }
    }

    public void setDoubleValue(Context context, String key, double value) {

        if (context != null) {
            SharedPreferences appPref = context.getApplicationContext().getSharedPreferences(
                    APP_REFERENCE, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = appPref.edit();
            if (editor != null) {
                editor.putString(key, "" + value);
                editor.apply();
            }
        }

    }
}
