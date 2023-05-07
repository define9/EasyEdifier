package cn.tomisme.easyedifier.me.ui.setting;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import cn.tomisme.easyedifier.R;
import cn.tomisme.easyedifier.hook.config.XModuleConfig;

public class SwitchSetting extends LinearLayout {

    private static String TAG = SwitchSetting.class.getName();
    private View mView; //界面控件

    private TextView title = null;
    private TextView description = null;
    private String key = null;
    private Switch open = null;

    public SwitchSetting(Context context) {
        super(context);
        init(context, null);
    }

    public SwitchSetting(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwitchSetting(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public SwitchSetting(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public Switch getOpen() {
        return open;
    }


    private void init(Context context, AttributeSet attrs) {
        if(attrs == null) {
            return;
        }

        // 1. 加载布局文件
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.switch_setting, this, true); //加载布局文件

        // 2. 加载布局的控件
        title = mView.findViewById(R.id.switchSettingTitle);
        description = mView.findViewById(R.id.switchSettingDesc);
        open = mView.findViewById(R.id.switchSettingOpen);

        // 3. 加载属性文件
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SwitchSetting);
        title.setText(array.getString(R.styleable.SwitchSetting_title));
        description.setText(array.getString(R.styleable.SwitchSetting_description));
        key = array.getString(R.styleable.SwitchSetting_key);
        boolean defOpen = array.getBoolean(R.styleable.SwitchSetting_open, false);

        // 4. 开关绑定事件
        open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                XModuleConfig.getInstance().setBoolean(key, isChecked);
            }
        });
        open.setChecked(XModuleConfig.getInstance().updateRemove());
    }
}
