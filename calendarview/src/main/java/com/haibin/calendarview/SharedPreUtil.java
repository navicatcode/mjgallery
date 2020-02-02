package com.haibin.calendarview;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreUtil {
    private static SharedPreUtil instance;
    private static String NAME = "setting";
    Context context;
    private SharedPreferences sharedPreferences;

    private SharedPreUtil(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreUtil getInstance(Context context) {
        if (instance == null) {
            Class var1 = SharedPreUtil.class;
            synchronized (SharedPreUtil.class) {
                if (instance == null) {
                    instance = new SharedPreUtil(context);
                }
            }
        }

        return instance;
    }

    public SharedPreUtil saveParam(String key, Object value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, ((Integer) value).intValue());
        } else if (value instanceof Long) {
            editor.putLong(key, ((Long) value).longValue());
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) value).booleanValue());
        } else if (value instanceof Float) {
            editor.putFloat(key, ((Float) value).floatValue());
        } else {
            new Throwable("错误的类型!");
        }

        editor.commit();
        return instance;
    }

    public <T> T getParam(String key, Class<T> clzss) {
        return (T) (clzss.equals(String.class) ? this.sharedPreferences.getString(key, "") : (clzss.equals(Integer.class) ? Integer.valueOf(this.sharedPreferences.getInt(key, 0)) : (clzss.equals(Long.class) ? Long.valueOf(this.sharedPreferences.getLong(key, 0L)) : (clzss.equals(Boolean.class) ? Boolean.valueOf(this.sharedPreferences.getBoolean(key, false)) : (clzss.equals(Float.class) ? Float.valueOf(this.sharedPreferences.getFloat(key, 0.0F)) : null)))));
    }

    public SharedPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }

    public void clear() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public int getInt(String key) {
        return this.sharedPreferences.getInt(key, 0);
    }

    public long getLong(String key) {
        return this.sharedPreferences.getLong(key, 0L);
    }

    public boolean getBoolean(String key) {
        return this.sharedPreferences.getBoolean(key, false);
    }

    public String getString(String key) {
        return this.sharedPreferences.getString(key, "");
    }

    public float getFloat(String key) {
        return this.sharedPreferences.getFloat(key, 0.0F);
    }
}