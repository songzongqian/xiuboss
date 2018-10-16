package com.byx.xiuboss.xiuboss.Mvp.fragmment;


import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.byx.xiuboss.xiuboss.Application.JgApplication;
import com.byx.xiuboss.xiuboss.Bean.MyFragmentBean;
import com.byx.xiuboss.xiuboss.Jgim.utils.SharePreferenceManager;
import com.byx.xiuboss.xiuboss.Jgim.utils.ToastUtil;
import com.byx.xiuboss.xiuboss.Jpush.MyReceiver;
import com.byx.xiuboss.xiuboss.MainActivity;
import com.byx.xiuboss.xiuboss.Mvp.activity.BalanceActivity;
import com.byx.xiuboss.xiuboss.Mvp.activity.SettingActivity;
import com.byx.xiuboss.xiuboss.Mvp.activity.SwichActivity;
import com.byx.xiuboss.xiuboss.Mvp.net.OkHttpUtils;
import com.byx.xiuboss.xiuboss.Mvp.view.CommonPopupWindow;
import com.byx.xiuboss.xiuboss.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.UserInfo;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener, CommonPopupWindow.ViewInterface {

    private ImageView settings;
    private TextView title;
    private TextView mobile;
    private TextView adMinistrators;
    private ImageView imageView;
    private TextView Accountbalance;
    private ImageView buttonImage;
    private Switch isOpen;
    private ImageView buttonImageTwo;
    private Button switchBtn;
    private RelativeLayout Accountbalance_btn;
    private RelativeLayout relativeLayout_btn;
    private SharedPreferences sharedPreferences;
    private Map<String, String> tags = new HashMap<>();
    private String url = "https://www.ourdaidai.com/CI/index.php/StoreMy/myHome";
    private Intent intent;
    private CommonPopupWindow popupWindow;
    private String mobile1;
    private String managerMobile1;
    private String id;
    private String sid;
    private Activity activity;
    private PopupWindow window;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        //EventBus.getDefault().register(this);
        sharedPreferences = getActivity().getSharedPreferences("login_sucess", getActivity().MODE_PRIVATE);
        initView(view);
        isOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    registerBroadcast();
                }else{
                    isOpen.setChecked(false);
                }
            }
        });
        initData();
        return view;

    }

    //处理事件逻辑
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEventBus(String object) {
        if (object.equals(sharedPreferences.getString("sid", ""))) {
            initData();
        }
    }

    private void initData() {

        sid = sharedPreferences.getString("sid", "");
        id = sharedPreferences.getString("id", "");
        mobile1 = sharedPreferences.getString("mobile", "");
        managerMobile1 = sharedPreferences.getString("managerMobile", "");
        Log.e("---------id--------", id);
        Log.e("-----sid-----", sid);
        Log.e("-----mobile-----", mobile1);
        tags.put("sid", sid);
        tags.put("mobile", mobile1);
        OkHttpUtils.getInstance().postDataAsynToNet(url, tags, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("-----string------", string);
                getData(string);

            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });

    }

    private void initView(View view) {
        settings = (ImageView) view.findViewById(R.id.settings);
        title = (TextView) view.findViewById(R.id.title);
        mobile = (TextView) view.findViewById(R.id.mobile);
        adMinistrators = (TextView) view.findViewById(R.id.adMinistrators);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        Accountbalance = (TextView) view.findViewById(R.id.Accountbalance);
        Accountbalance_btn = (RelativeLayout) view.findViewById(R.id.Accountbalance_btn);
        relativeLayout_btn = (RelativeLayout) view.findViewById(R.id.relativeLayout_btn);
        buttonImage = (ImageView) view.findViewById(R.id.button_image);
        isOpen = (Switch) view.findViewById(R.id.isOpen);
        buttonImageTwo = (ImageView) view.findViewById(R.id.button_image_two);
        switchBtn = (Button) view.findViewById(R.id.switch_btn);
        activity = new MainActivity();
        Accountbalance_btn.setOnClickListener(this);
        relativeLayout_btn.setOnClickListener(this);
        switchBtn.setOnClickListener(this);
        settings.setOnClickListener(this);
    }

    private void getData(final String data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                MyFragmentBean myFragmentBean = gson.fromJson(data, MyFragmentBean.class);
                title.setText(myFragmentBean.getData().getUsername());
                mobile.setText(myFragmentBean.getData().getMobile());
                adMinistrators.setText(myFragmentBean.getData().getUsername() + " |" + myFragmentBean.getData().getRole());
                Glide.with(getActivity()).load(myFragmentBean.getData().getLogo()).bitmapTransform(new CropCircleTransformation(getActivity())).into(imageView);
                Accountbalance.setText(myFragmentBean.getData().getAmount());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings:
                //设置
                intent = new Intent(getActivity(), SettingActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.Accountbalance_btn:
                //账户余额
                intent = new Intent(getActivity(), BalanceActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.relativeLayout_btn:

                showPopupWindow();
                backgroundAlpha(0.5f);
                window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1.0f);
                    }
                });
                //客服电话
                break;
            case R.id.switch_btn:
                intent = new Intent(getActivity(), SwichActivity.class);
                intent.putExtra("id", sid);
                getActivity().startActivity(intent);
                //切换店铺
                break;



        }
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_phone, null, false);
        window = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        RelativeLayout manager = contentView.findViewById(R.id.manager);
        TextView mobile = contentView.findViewById(R.id.mobile);
        TextView managerMobile = contentView.findViewById(R.id.managerMobile);
        RelativeLayout Customerservice = contentView.findViewById(R.id.Customerservice);
        final TextView managerName = contentView.findViewById(R.id.manager_name);
        final TextView customerServiceName = contentView.findViewById(R.id.customerservice_name);
        Button cancel = contentView.findViewById(R.id.cancel);
        mobile.setText("0330-3576321");
        managerMobile.setText(managerMobile1);
        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                showCallPopupWindow(managerName.getText().toString(),managerMobile1);

            }
        });
        Customerservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                showCallPopupWindow(customerServiceName.getText().toString(),"0330-3576321");

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (window != null) {
                    window.dismiss();
                }
            }
        });
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.showAtLocation(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null), Gravity.BOTTOM, 0, 0);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call(managerMobile1);
                } else {
                    Toast.makeText(activity, "请手动开启打电话权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2000:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call(mobile1);
                } else {
                    Toast.makeText(activity, "请手动开启打电话权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 调用拨号界面	 * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        getActivity().startActivity(intent);
    }



    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_phone:
                RelativeLayout manager = view.findViewById(R.id.manager);
                TextView mobile = view.findViewById(R.id.mobile);
                TextView managerMobile = view.findViewById(R.id.managerMobile);
                final TextView managerName = view.findViewById(R.id.manager_name);
                final TextView customerServiceName = view.findViewById(R.id.customerservice_name);
                RelativeLayout Customerservice = view.findViewById(R.id.Customerservice);
                Button cancel = view.findViewById(R.id.cancel);
                mobile.setText("0330-3576321");
                managerMobile.setText(managerMobile1);
                manager.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        call(managerMobile1);
                        showCallPopupWindow(managerName.getText().toString(),managerMobile1);

                    }
                });
                Customerservice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCallPopupWindow(customerServiceName.getText().toString(),"0330-3576321");
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                break;
        }
    }
      private void showCallPopupWindow(String name,String call){
          View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_call, null, false);
          window = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
          TextView callName = contentView.findViewById(R.id.call_name);
          final TextView callNumber = contentView.findViewById(R.id.call_number);
          TextView callDial = contentView.findViewById(R.id.call_dial);
          TextView callCancel = contentView.findViewById(R.id.call_cancel);
          callName.setText(name);
          callNumber.setText(call);
          callDial.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  //使用兼容库就无需判断系统版本
                  if (Build.VERSION.SDK_INT >= 22) {
                      if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                          ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 2000);
                      }else {

                          call(callNumber.getText().toString());

                      }
                  } else {
                      call(callNumber.getText().toString());

                  }
              }
          });
          callCancel.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  window.dismiss();

              }
          });
          window.setOutsideTouchable(true);
          window.setTouchable(true);
          backgroundAlpha(0.5f);
          window.setOnDismissListener(new PopupWindow.OnDismissListener() {
              @Override
              public void onDismiss() {
                  backgroundAlpha(1.0f);
              }
          });

          window.showAtLocation(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null), Gravity.CENTER, 0, 0);
      }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    MyReceiver receiver;

    private void registerBroadcast() {
        // 注册广播接收者
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("exit_app");
//        this.registerReceiver(receiver, filter);
    }
}