package cn.tomisme.easyedifier.hook.feature.impl;

import android.util.Log;

import org.json.JSONObject;

import cn.tomisme.easyedifier.hook.config.XModuleConfig;
import cn.tomisme.easyedifier.hook.feature.IHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;

public class RemoveUpdate implements IHook {
    private static String TAG = RemoveUpdate.class.getName();

    @Override
    public String getClassName() {
        return "com.xiaomi.market.sdk.XiaomiUpdateAgent$CheckUpdateTask";
    }

    @Override
    public boolean hookHandle(Class<?> clz, ClassLoader loader) {
        if (XModuleConfig.getInstance().updateRemove()) {
            Log.i(TAG, "hookHandle: unActive");
            return false;
        }
        try {
            XposedBridge.hookMethod(clz.getDeclaredMethod("parseUpdateInfo", JSONObject.class), new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return null;
                }
            });
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "hookHandle: ", e);
            return false;
        }

        return true;
    }
}
