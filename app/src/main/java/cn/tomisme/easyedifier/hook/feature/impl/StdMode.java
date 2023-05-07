package cn.tomisme.easyedifier.hook.feature.impl;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.Arrays;

import cn.tomisme.easyedifier.hook.feature.IHook;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * [-86, 2, -63, 3, 33, -119], false => 环境声模式
 * [-86, 2, -63, 1, 33, -121], false => 标准模式
 * [-86, 2, -63, 2, 33, -120], false => 降噪模式
 *
 * [-86, 1, -52, 33, -112], false => 握手包发现了一堆，下标为 1 的数据是 1
 */
public class StdMode implements IHook {
    private static String TAG = "StdMode";
    @Override
    public String getClassName() {
        return "com.edifier.edifierconnect.service.AppWidgetService";
    }

    @Override
    public boolean hookHandle(Class<?> clz, ClassLoader loader) {
        try {
            Method method = clz.getDeclaredMethod("sendData2Ble", byte[].class, boolean.class);
            byte[] stdMode = new byte[]{-86, 2, -63, 1, 33, -121};
            XposedBridge.hookMethod(method, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.i(TAG, "beforeHookedMethod: start");
                    byte[] d = (byte[]) param.args[0];
                    boolean b = (boolean) param.args[1];
                    Log.i(TAG, "beforeHookedMethod: " + Arrays.toString(d) + ", " + b);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    byte[] d = (byte[]) param.args[0];
                    if (d == null || d.length < 4) {
                        return;
                    }
                    if (d[1] == 1 && d[2] == -14 && d[4] == -74) {
                        Log.i(TAG, "afterHookedMethod: 握手包结束,正在发送hook数据");
                        Class<?> app = loader.loadClass("com.edifier.edifierconnect.EdifierApplication");
                        if (app != null) {
                            Log.i(TAG, "afterHookedMethod: 拿到了app类");
                            Object service = XposedHelpers.getStaticObjectField(app, "bindService");
                            Log.i(TAG, "afterHookedMethod: " + method.getName() + " serivce: " + (service == null));
                            XposedHelpers.callMethod(service, method.getName(), stdMode, false);
                            Log.i(TAG, "afterHookedMethod: 发送完成");
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "hookHandle: ", e);
        }
        return false;
    }
}
