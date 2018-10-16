package com.byx.xiuboss.xiuboss.Jpush;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import com.byx.xiuboss.xiuboss.Bean.JpBean;
import com.byx.xiuboss.xiuboss.MainActivity;
import com.byx.xiuboss.xiuboss.Utils.TTSUtils;
import com.google.gson.Gson;
import cn.jpush.android.api.JPushInterface;



/**
 * Created by TauchMe on 2018/8/3.
 *
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";
    private TTSUtils instance;
    private String string;
    private Intent intent1;
    private String extra;
    private JpBean jpBean;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        instance = TTSUtils.getInstance();
        instance.init();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //nd the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知"+bundle.getString(JPushInterface.EXTRA_ALERT));
            string = bundle.getString(JPushInterface.EXTRA_ALERT);
            extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Logger.d(TAG,""+ string);
            Gson gson=new Gson();
            jpBean = gson.fromJson(extra, JpBean.class);
            Logger.d(TAG,""+ extra);
            if (jpBean.getType().equals("orderPush")){
                Logger.d(TAG,"--------"+extra);
                instance.speak("您有新的休休团购订单，请及时处理。");
            }else {
                intent1 = new Intent(context, MainActivity.class);
                intent1.putExtra("MESSAGE", string);
                intent1.putExtra("TITLE", jpBean.getTitle());
                intent1.putExtra("TYPE", jpBean.getType());
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                instance.speak("休休团购收款"+ string +"元");//
                context.startActivity(intent1);
            }

           // SharedPreferencesUtils.setParam(JGApplication.context,"name","休休团购收款"+ string +"元整");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..


        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        return "123";
    }

    //nd msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
    }

}