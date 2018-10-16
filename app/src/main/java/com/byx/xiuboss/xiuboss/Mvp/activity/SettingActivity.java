package com.byx.xiuboss.xiuboss.Mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.byx.xiuboss.xiuboss.Jgim.utils.ToastUtil;
import com.byx.xiuboss.xiuboss.R;
import com.byx.xiuboss.xiuboss.Utils.FileCacheUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.setting_image_clear)
    ImageView settingImageClear;
    @BindView(R.id.setting_image_updata)
    ImageView settingImageUpdata;
    @BindView(R.id.button_exit)
    Button buttonExit;
    @BindView(R.id.clear)
    TextView clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        File file = new File("/data/data/com.byx.xiuboss.xiuboss/files");
        try {
            String cacheSize = FileCacheUtil.getCacheSize(file);
            String substring = cacheSize.substring(0, cacheSize.indexOf(3));
            clear.setText( substring+"k");

            Log.e("-----file-----", substring);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.img_back, R.id.setting_image_clear, R.id.setting_image_updata, R.id.button_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.setting_image_clear:
                FileCacheUtil.cleanInternalCache(getApplication());
                ToastUtil.shortToast(this, "清除成功");
                break;
            case R.id.setting_image_updata:
                ToastUtil.shortToast(this, "当前已是最新版本");
                break;
            case R.id.button_exit:
                JMessageClient.logout();
                Intent logoutIntent = new Intent(this, LoginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutIntent);
                break;
        }
    }
}
