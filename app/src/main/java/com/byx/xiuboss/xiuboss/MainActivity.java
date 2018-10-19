package com.byx.xiuboss.xiuboss;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.byx.xiuboss.xiuboss.Jgim.utils.ScrollControlViewPager;
import com.byx.xiuboss.xiuboss.Mvp.activity.BaseActivity;
import com.byx.xiuboss.xiuboss.Mvp.adapter.ViewPagerAdapter;
import com.byx.xiuboss.xiuboss.Mvp.fragmment.BillFragment;
import com.byx.xiuboss.xiuboss.Mvp.fragmment.HomeFragment;
import com.byx.xiuboss.xiuboss.Mvp.fragmment.MyFragment;
import com.byx.xiuboss.xiuboss.Utils.NotificationsUtils;
import com.byx.xiuboss.xiuboss.Utils.PermissionInterface;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private long firstTime = 0;
    private RelativeLayout actionbarHomeBtn;
    private RelativeLayout actionbarBillBtn;
    private RelativeLayout actionbarMyBtn;
    private ScrollControlViewPager viewpager;
    private List<Fragment> mFragmentList;
    private ImageView homeImage;
    private TextView homeText;
    private ImageView billImage;
    private TextView billText;
    private ImageView myImage;
    private TextView myText;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permission = ActivityCompat.checkSelfPermission(this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        }
        initView();
        initAdapter();
        initNotifications();

    }

    private void initAdapter() {
        mFragmentList = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        BillFragment billFragment = new BillFragment();
        MyFragment myFragment = new MyFragment();
        mFragmentList.add(homeFragment);
        mFragmentList.add(billFragment);
        mFragmentList.add(myFragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManger(), mFragmentList);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        homeBackGround();

    }

    private void initNotifications() {
        if (!NotificationsUtils.isNotificationEnabled(this)) {
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.show();

            View view = View.inflate(this, R.layout.dialog, null);
            dialog.setContentView(view);

            TextView context = (TextView) view.findViewById(R.id.tv_dialog_context);
            context.setText("检测到您没有打开通知权限，是否去打开");

            TextView confirm = (TextView) view.findViewById(R.id.btn_confirm);
            confirm.setText("确定");
            confirm.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 9) {
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                    } else if (Build.VERSION.SDK_INT <= 8) {
                        localIntent.setAction(Intent.ACTION_VIEW);

                        localIntent.setClassName("com.android.settings",
                                "com.android.settings.InstalledAppDetails");

                        localIntent.putExtra("com.android.settings.ApplicationPkgName",
                                MainActivity.this.getPackageName());
                    }
                    startActivity(localIntent);
                }
            });

            TextView cancel = (TextView) view.findViewById(R.id.btn_off);
            cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
        }
    }

    public FragmentManager getSupportFragmentManger() {
        return getSupportFragmentManager();
    }

    private void initView() {
      /*  mMainView.initModule();
        mMainController = new MainController(mMainView, this);
        mMainView.setOnClickListener(mMainController);
        mMainView.setOnPageChangeListener(mMainController);
        fullScreen(this);*/
        actionbarHomeBtn = (RelativeLayout) findViewById(R.id.actionbar_home_btn);
        actionbarBillBtn = (RelativeLayout) findViewById(R.id.actionbar_bill_btn);
        actionbarMyBtn = (RelativeLayout) findViewById(R.id.actionbar_my_btn);
        viewpager = (ScrollControlViewPager) findViewById(R.id.viewpager);
        actionbarHomeBtn.setOnClickListener(this);
        actionbarBillBtn.setOnClickListener(this);
        actionbarMyBtn.setOnClickListener(this);
        homeImage = (ImageView) findViewById(R.id.home_image);
        homeText = (TextView) findViewById(R.id.home_text);
        billImage = (ImageView) findViewById(R.id.bill_image);
        billText = (TextView) findViewById(R.id.bill_text);
        myImage = (ImageView) findViewById(R.id.my_image);
        myText = (TextView) findViewById(R.id.my_text);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                finish();
            }
        }
        return keyCode == KeyEvent.KEYCODE_BACK;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_home_btn:
                viewpager.setCurrentItem(0);
                homeBackGround();
                break;
            case R.id.actionbar_bill_btn:
                viewpager.setCurrentItem(1);
                billText.setTextColor(0xFFC6A04D);
                Glide.with(this).load(R.drawable.my_icon_bill_2).into(billImage);
                homeText.setTextColor(0xFF999999);
                Glide.with(this).load(R.drawable.my_icon_index).into(homeImage);
                myText.setTextColor(0xFF999999);
                Glide.with(this).load(R.drawable.my_icon_me_2).into(myImage);

                break;
            case R.id.actionbar_my_btn:
                viewpager.setCurrentItem(2);
                myText.setTextColor(0xFFC6A04D);
                Glide.with(this).load(R.drawable.my_icon_me).into(myImage);
                homeText.setTextColor(0xFF999999);
                Glide.with(this).load(R.drawable.my_icon_index).into(homeImage);
                billText.setTextColor(0xFF999999);
                Glide.with(this).load(R.drawable.my_icon_bill).into(billImage);
                break;
        }
    }
    public void homeBackGround(){
        homeText.setTextColor(0xFFC6A04D);
        Glide.with(this).load(R.drawable.my_icon_index_2).into(homeImage);
        myText.setTextColor(0xFF999999);
        Glide.with(this).load(R.drawable.my_icon_me_2).into(myImage);
        billText.setTextColor(0xFF999999);
        Glide.with(this).load(R.drawable.my_icon_bill).into(billImage);
    }




}
