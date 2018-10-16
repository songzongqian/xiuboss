package com.byx.xiuboss.xiuboss.Mvp.activity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.byx.xiuboss.xiuboss.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS on 2018/8/1.
 */

public class XiangQingActivity extends AppCompatActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.webXiang)
    WebView mWebView;
    @BindView(R.id.titleLin)
    LinearLayout titleLin;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        fullScreen(XiangQingActivity.this);
        url = intent.getStringExtra("url");
        Log.e("url", url);
        mWebView.loadUrl(url);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setSavePassword(false);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
// 设置可以支持缩放
//        mWebView.getSettings().setSupportZoom(true);
// 扩大比例的缩放
//        mWebView.getSettings().setUseWideViewPort(true);
// 自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.e("urlSta",url);
                if (url.contains("list")){
                    XiangQingActivity.this.finish();
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("urlFin",url);
                super.onPageFinished(view, url);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionKey(KeyEvent.KEYCODE_BACK);
            }
        });
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent event) {
                    if ( mWebView.canGoBack()) {  //表示按返回键

                        XiangQingActivity.this.finish();   //后退

                        //webview.goForward();//前进
                        return true;    //已处理
                    }

                return false;
            }
        });
        titleLin.setPadding(10, getStatusBarHeight() + 10, 10, 20);
    }

    public int getStatusBarHeight() {
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }
    public void actionKey(final int keyCode) {
        new Thread() {
            public void run () {
                try {
                    Instrumentation inst=new Instrumentation();
                    inst.sendKeyDownUpSync(keyCode);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            WebBackForwardList backForwardList = mWebView.copyBackForwardList();
            if (backForwardList != null && backForwardList.getSize() != 0) {
                //当前页面在历史队列中的位置
                int currentIndex = backForwardList.getCurrentIndex();
                WebHistoryItem historyItem =
                        backForwardList.getItemAtIndex(currentIndex - 1);
                if (historyItem != null) {
                    mWebView.goBack();
                }
            }

            return true;

        }else {
            XiangQingActivity.this.finish();
        }
        return false;
    }

    @OnClick(R.id.textView2)
    public void onViewClicked() {
        this.finish();
    }
}


