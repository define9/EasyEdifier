package cn.tomisme.easyedifier.hook.feature.impl;

import java.util.List;

import cn.tomisme.easyedifier.hook.feature.IHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;

public class RemoveAdv implements IHook {
    @Override
    public String getClassName() {
        return "com.edifier.edifierconnect.service.AdvService$Companion";
    }

    @Override
    public boolean hookHandle(Class<?> clz, ClassLoader loader) {
        try {
            XposedBridge.hookMethod(clz.getDeclaredMethod("getWeightChooseAdv", List.class), new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return null;
                }
            });
        } catch (NoSuchMethodException e) {
            return false;
        }

        return true;
    }
}
