package cn.tomisme.easyedifier.hook.entry;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import cn.tomisme.easyedifier.hook.config.XModuleConfig;
import cn.tomisme.easyedifier.hook.feature.IHook;
import cn.tomisme.easyedifier.hook.feature.impl.RemoveAdv;
import cn.tomisme.easyedifier.hook.feature.impl.RemoveUpdate;
import cn.tomisme.easyedifier.hook.feature.impl.StdMode;
import cn.tomisme.easyedifier.me.share.MultiprocessSharedPreferences;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookEntry implements IXposedHookLoadPackage {
    private static String TAG = HookEntry.class.getName();
    private ClassLoader classLoader;
    private Context mContext;
    private final static String hookPackName = "com.edifier.edifierconnect";
    private final static String selfPackName = "cn.tomisme.easyedifier";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!hookPackName.equals(loadPackageParam.packageName)) {
            return;
        }

        //Class<?> startPage = XposedHelpers.findClass("com.edifier.edifierconnect.StartPageActivity", classLoader);

        Set<Class<? extends IHook>> replaceClasses = new HashSet<>();

        replaceClasses.add(RemoveUpdate.class);
        replaceClasses.add(RemoveAdv.class);
        replaceClasses.add(StdMode.class);

        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                mContext = (Context) param.args[0];
                classLoader = mContext.getClassLoader();
                MultiprocessSharedPreferences.setAuthority("cn.tomisme.provider");
                XModuleConfig.init(mContext);

                for (Class<? extends IHook> replaceClz : replaceClasses) {
                    Class<?> clz = null;
                    IHook iHook = replaceClz.newInstance();
                    String className = iHook.getClassName();

                    // 1. 定位class位置
                    Log.i(TAG, "start location class: " + className);
                    try {
                        clz = classLoader.loadClass(className);
                    } catch (Exception  ignore) {
                    }

                    if (clz != null) {
                        Log.i(TAG, "start location class: " + clz.getName());

                        iHook.hookHandle(clz, classLoader);
                    } else {
                        Log.e(TAG, "class not found" + className);
                    }
                }
            }
        });
    }
}
