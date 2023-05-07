package cn.tomisme.easyedifier.hook.config;

import android.content.Context;
import android.util.Log;

import cn.tomisme.easyedifier.me.share.MultiprocessSharedPreferences;

public class XModuleConfig extends BaseConfig {
    private final static String TAG = XModuleConfig.class.getName();
    private boolean updateRemove;

    private static XModuleConfig instance = new XModuleConfig();

    private XModuleConfig() {}

    public static XModuleConfig getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance.sp = MultiprocessSharedPreferences.getSharedPreferences(context, "def", Context.MODE_PRIVATE);
        Log.i(TAG, "init updateRemove: " + instance.sp.getBoolean("updateRemove", false));
        instance.updateRemove = instance.sp.getBoolean("updateRemove", false);
    }

    public boolean updateRemove() {
        Log.i(TAG, "updateRemove: " + updateRemove);
        return updateRemove;
    }
}
