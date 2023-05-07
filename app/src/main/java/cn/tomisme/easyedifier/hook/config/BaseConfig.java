package cn.tomisme.easyedifier.hook.config;

import android.content.SharedPreferences;

public abstract class BaseConfig {

    protected SharedPreferences sp;

    public void setBoolean(String key, boolean val) {
        sp.edit().putBoolean(key, val).apply();
    }
}
