package cn.tomisme.easyedifier;

import android.app.Activity;
import android.os.Bundle;

import cn.tomisme.easyedifier.hook.config.XModuleConfig;
import cn.tomisme.easyedifier.me.share.MultiprocessSharedPreferences;

public class MainActivity extends Activity {
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XModuleConfig.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}