package com.byx.xiuboss.xiuboss.Mvp.fragmment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.byx.xiuboss.xiuboss.Bean.HomeBean;
import com.byx.xiuboss.xiuboss.Bean.HomePopup;
import com.byx.xiuboss.xiuboss.Jgim.utils.ToastUtil;
import com.byx.xiuboss.xiuboss.Mvp.activity.BalanceActivity;
import com.byx.xiuboss.xiuboss.Mvp.activity.ReceivablesActivity;
import com.byx.xiuboss.xiuboss.Mvp.activity.TipsActivity;
import com.byx.xiuboss.xiuboss.Mvp.activity.TodayMoneyActivity;
import com.byx.xiuboss.xiuboss.Mvp.activity.WeChatActivity;
import com.byx.xiuboss.xiuboss.Mvp.net.OkHttpUtils;
import com.byx.xiuboss.xiuboss.R;
import com.google.gson.Gson;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.iwgang.countdownview.CountdownView;
import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.home_scan_image)
    ImageView homeScanImage;
    @BindView(R.id.home_title)
    TextView home_title;
    @BindView(R.id.home_wechat_image)
    ImageView homeWechatImage;
    @BindView(R.id.total_fee)
    TextView total_fee;
    @BindView(R.id.customer)
    TextView customer;
    @BindView(R.id.textView19)
    TextView textView19;
    @BindView(R.id.textView21)
    TextView textView21;
    @BindView(R.id.Totalamount)
    TextView Totalamount;
    @BindView(R.id.Totalamount_money)
    TextView TotalamountMoney;
    @BindView(R.id.yesterday_money)
    TextView yesterdayMoney;
    @BindView(R.id.textView29)
    TextView textView29;
    @BindView(R.id.home_icon_tips)
    ImageView homeIconTips;
    @BindView(R.id.balance_money)
    TextView balanceMoney;
    @BindView(R.id.grade_money)
    TextView gradeMoney;
    @BindView(R.id.progressBarHorizontal)
    ProgressBar progressBarHorizontal;
    @BindView(R.id.backward_money)
    TextView backwardMoney;
    @BindView(R.id.button_reward)
    Button buttonReward;
    @BindView(R.id.mid_money)
    TextView midMoney;
    @BindView(R.id.max_money)
    TextView maxMoney;
    @BindView(R.id.textView35)
    TextView textView35;
    @BindView(R.id.home_max_money)
    TextView homeMaxMoney;
    @BindView(R.id.progressBarHorizontal_two)
    ProgressBar progressBarHorizontalTwo;
    @BindView(R.id.Service_Charge_money)
    TextView ServiceChargeMoney;
    Unbinder unbinder;
    @BindView(R.id.dengji)
    TextView dengji;
    @BindView(R.id.difference_money)
    TextView differenceMoney;
    @BindView(R.id.reward_money)
    TextView rewardMoney;
    @BindView(R.id.button_reward_two)
    CountdownView buttonRewardTwo;
    @BindView(R.id.relative_Totalamount)
    RelativeLayout relativeTotalamount;
    @BindView(R.id.receivables)
    LinearLayout receivables;
    @BindView(R.id.business)
    LinearLayout business;
    @BindView(R.id.commodity)
    LinearLayout commodity;
    @BindView(R.id.wallet)
    LinearLayout wallet;
    @BindView(R.id.receipt_code)
    LinearLayout receiptCode;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.ll_count)
    LinearLayout ll_btn2;

    @BindView(R.id.button_three)
    Button buttonThree;

    private String homeUrl = "https://www.ourdaidai.com/CI/index.php/StoreMy/storeHome";
    private String popUrl = "https://www.ourdaidai.com/CI/index.php/StoreMy/reward";
    private String title;
    private String sid;
    private HomeBean homeBean;
    private View inflate;
    private View popup;
    private PopupWindow window;
    private SharedPreferences login_sucess;
    private PopupWindow window1;
    private Intent intent;
    private int REQUEST_CODE_SCAN = 111;
    private CountDownTimer countDownTimer;
    private String strHinute;
    private String strMinute;
    private String strSecond;
    int flag=0;
    private boolean rewardValue;
    private long servermills;
    private int amountValue;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_home, container, false);
        popup = LayoutInflater.from(getActivity()).inflate(R.layout.popup_reward, null);
        login_sucess = getActivity().getSharedPreferences("login_sucess", MODE_PRIVATE);
        SharedPreferences isRewad=getActivity().getSharedPreferences("flag",MODE_PRIVATE);
        SharedPreferences serversp = getActivity().getSharedPreferences("serverValue",MODE_PRIVATE);
        rewardValue = isRewad.getBoolean("isreward",false);
        servermills = serversp.getLong("server",0);
        unbinder = ButterKnife.bind(this, this.inflate);
        initData();
        return this.inflate;
    }

    private void initData() {

        Map<String, String> map = new HashMap<>();
        sid = login_sucess.getString("sid", "");
        title = login_sucess.getString("title", "");
        home_title.setText(title);
        map.put("sid", sid);
        OkHttpUtils.getInstance().postDataAsynToNet(homeUrl, map, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("----home----", string);
                getData(string);
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    private void getData(final String data) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                homeBean = gson.fromJson(data, HomeBean.class);
                home_title.setText(login_sucess.getString("homeTitle", ""));
                total_fee.setText(homeBean.getData().getFee().getTotal_fee());
                customer.setText(homeBean.getData().getFee().getCustomer());
                Totalamount.setText("今日收款" + homeBean.getData().getTodaySum() + "笔,合计");
                TotalamountMoney.setText(homeBean.getData().getTodayFee() + "元");
                yesterdayMoney.setText("昨日实时：" + homeBean.getData().getYesterFee() + "元");
                HomeBean.DataBean.AmountBean amount = homeBean.getData().getAmount();
                balanceMoney.setText(amount.getAmountFee() + "元");
                midMoney.setText(homeBean.getData().getCurrentGrade().getF2());
                maxMoney.setText(homeBean.getData().getNextGrade().getF2());
                String id = homeBean.getData().getCurrentGrade().getId();
                Double xyz= Double.parseDouble(amount.getAmount());

                if(xyz<200){
                    buttonThree.setVisibility(View.VISIBLE);
                    buttonThree.setText("等级不足");
                    buttonReward.setVisibility(View.GONE);
                    ll_btn2.setVisibility(View.GONE);
                }else if(rewardValue==true){
                    buttonReward.setVisibility(View.GONE);
                    buttonThree.setVisibility(View.GONE);
                    long time = System.currentTimeMillis();
                    ll_btn2.setVisibility(View.VISIBLE);
                    long CurrentTimes= time;
                    if(CurrentTimes< servermills){
                        long chaValue= servermills-CurrentTimes;
                        buttonRewardTwo.start(chaValue);
                    }
                }else if(rewardValue==false && xyz>=200){
                    buttonReward.setVisibility(View.VISIBLE);
                    buttonReward.setText("领取奖励");
                    ll_btn2.setVisibility(View.GONE);
                    buttonThree.setVisibility(View.GONE);
                }

                if(rewardValue==true && xyz>200 ){
                    buttonReward.setVisibility(View.GONE);
                    long time = System.currentTimeMillis();
                    buttonRewardTwo.setVisibility(View.VISIBLE);
                    long CurrentTimes= time;
                    if(CurrentTimes< servermills){
                        long chaValue= servermills-CurrentTimes;
                        buttonRewardTwo.start(chaValue);
                    }
                }

                if (id == null) {
                    dengji.setText("Lv" + "0");
                } else {
                    dengji.setText("Lv" + homeBean.getData().getCurrentGrade().getId());
                }
                gradeMoney.setText(homeBean.getData().getCurrentGrade().getF4());
                backwardMoney.setText("Lv" + homeBean.getData().getNextGrade().getId());
                differenceMoney.setText(homeBean.getData().getNextDifference());
                rewardMoney.setText(homeBean.getData().getNextGrade().getF4());
                ServiceChargeMoney.setText(homeBean.getData().getMonthFee());
                progressBarHorizontal.setProgress(getProgressBar());
                progressBarHorizontalTwo.setProgress(getProgressBarTwo());
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.home_scan_image, R.id.home_wechat_image, R.id.home_icon_tips, R.id.button_reward, R.id.relative_Totalamount,R.id.receivables, R.id.business, R.id.commodity, R.id.wallet, R.id.receipt_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //扫一扫
            case R.id.home_scan_image:
                AndPermission.with(this)
                        .permission(Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE)
                        .onGranted(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                                /*ZxingConfig是配置类
                                 *可以设置是否显示底部布局，闪光灯，相册，
                                 * 是否播放提示音  震动
                                 * 设置扫描框颜色等
                                 * 也可以不传这个参数
                                 * */
                                ZxingConfig config = new ZxingConfig();
                                config.setPlayBeep(true);//是否播放扫描声音 默认为true
                                config.setShake(true);//是否震动  默认为true
                                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                                startActivityForResult(intent, REQUEST_CODE_SCAN);
                            }
                        })
                        .onDenied(new Action() {
                            @Override
                            public void onAction(List<String> permissions) {
                                Uri packageURI = Uri.parse("package:" + getActivity().getPackageName());
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);

                                Toast.makeText(getActivity(), "没有权限无法扫描呦", Toast.LENGTH_LONG).show();
                            }
                        }).start();
                break;
                //二维码
            case R.id.home_wechat_image:
                intent = new Intent(getActivity(), WeChatActivity.class);
                intent.putExtra("sid", sid);
                startActivity(intent);
                break;
                //等级规则
            case R.id.home_icon_tips:
                intent = new Intent(getActivity(), TipsActivity.class);
                startActivity(intent);
                break;
                //领取奖励
            case R.id.button_reward:
                showPopupWindow();
                backgroundAlpha(0.5f);
                window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1.0f);
                    }
                });
                break;
                //今日收款详情
            case R.id.relative_Totalamount:
                intent = new Intent(getActivity(), TodayMoneyActivity.class);
                startActivity(intent);
                break;
                //收款
            case R.id.receivables:
                intent=new Intent(getActivity(),ReceivablesActivity.class);
                startActivity(intent);
                break;
                //商家
            case R.id.business:
                break;
                //商品
            case R.id.commodity:
                break;
                //钱包
            case R.id.wallet:
                intent=new Intent(getActivity(),BalanceActivity.class);
                startActivity(intent);
                break;
                //收款码
            case R.id.receipt_code:
                break;
        }
    }

    private int getProgressBar() {
        String max = maxMoney.getText().toString();
        String min = midMoney.getText().toString();
        String amount = homeBean.getData().getAmount().getAmount();
        double i2 = Double.parseDouble(amount);
        int i = Integer.parseInt(max);
        int i1 = Integer.parseInt(min);
        double i3 = (i2 - i1) / (i - i1);
        Log.e("-i2--", i2 + "");
        Log.e("-i--", i + "");
        Log.e("-i1--", i1 + "");
        Log.e("i3", i3 + "");
        float v = (float) i3 * 100f;
        return (int) v;
    }

    private int getProgressBarTwo() {
        double monthMoney = homeBean.getData().getMonthMoney();
        int i1 = (int) monthMoney;
        float i2 = (30000 - i1) / 30000f;
        Log.e("-i2--", i2 + "");
        Log.e("-i1--", i1 + "");
        float v = i2 * 100f;
        return (int) v;
    }

    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void showPopupWindow() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.popup_reward, null, false);
        window = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ImageView icon_close = inflate.findViewById(R.id.icon_close);
        TextView money = inflate.findViewById(R.id.popup_money);
        Button nextReward = inflate.findViewById(R.id.btn_next_reward);
        Button reward = inflate.findViewById(R.id.btn_reward);
        money.setText(homeBean.getData().getCurrentGrade().getF4() + "元");
        icon_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        nextReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                getPopopSuccess();
            }
        });
        window.setBackgroundDrawable(new BitmapDrawable());
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.showAtLocation(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null), Gravity.CENTER, 0, 0);
    }
    private void getPopopSuccess(){
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.popup_success, null, false);
        window = new PopupWindow(inflate, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        ImageView close = inflate.findViewById(R.id.icon_close);
        TextView popupMoney = inflate.findViewById(R.id.popup_money);
        Button btnRecord = inflate.findViewById(R.id.btn_record);
        popupMoney.setText(homeBean.getData().getCurrentGrade().getF4() + "元");
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
                Intent intent = new Intent(getActivity(), BalanceActivity.class);
                getActivity().startActivity(intent);
            }
        });
        backgroundAlpha(0.5f);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        window.setOutsideTouchable(true);
        window.setTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        window.showAtLocation(LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null), Gravity.CENTER, 0, 0);
        getPopupData();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("flag",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isreward",true).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon_close:
            case R.id.btn_next_reward:
                window.dismiss();
                break;
            case R.id.btn_reward:
                showPopupWindow();
                break;
        }
    }

    private void getPopupData() {
        Map<String, String> map = new HashMap<>();
        String id = login_sucess.getString("id", "");
        map.put("sid", sid);
        map.put("clerk_id", id);
        map.put("money", homeBean.getData().getCurrentGrade().getF4());
        OkHttpUtils.getInstance().postDataAsynToNet(popUrl, map, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                getpopData(response.body().string());

            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    private void getpopData(final String pop) {
        Gson gson = new Gson();
        final HomePopup homePopup = gson.fromJson(pop, HomePopup.class);
        String message = homePopup.getMessage();
        Log.e("时间戳", message);
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run() {
                if (homePopup.getCode()==2000) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    long lt = new Long(homeBean.getData().getSigntime());

                    String format = simpleDateFormat.format(new Date(lt));
                    Log.e("转换之后时间戳", format);
                    SharedPreferences serverPerference = getActivity().getSharedPreferences("serverValue",MODE_PRIVATE);
                    serverPerference.edit().putLong("server",lt*1000+86400000).commit();
                    System.out.println("服务器返回领取时间"+lt);
                    long currentTime = System.currentTimeMillis();
                    System.out.println("系统当前时间"+currentTime);
                    long finalValue=(lt*1000+86400000-1000)-currentTime;
                    System.out.println("计算获取最终时间"+finalValue);
                    buttonRewardTwo.start(finalValue);
                    buttonRewardTwo.setEnabled(false);
                    buttonReward.setVisibility(View.GONE);
                    ll_btn2.setVisibility(View.VISIBLE);
                    buttonThree.setVisibility(View.GONE);
                }else{
                    buttonReward.setVisibility(View.GONE);
                    ll_btn2.setVisibility(View.GONE);
                    buttonThree.setVisibility(View.VISIBLE);
                    buttonThree.setText("领取时间未到");
                    System.out.println("服务器返回领取时间未到");
                }
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                final String content = data.getStringExtra(Constant.CODED_CONTENT);
                new AlertDialog.Builder(getActivity())
                        .setTitle("提示")
                        .setMessage("是否绑定该二维码为当前《醉清风》店铺的收款码？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                OkHttpUtils.getInstance().getDataAsynFromNet(content + "&sid=" + sid + "&type=app", new OkHttpUtils.MyNetCall() {
                                    @Override
                                    public void success(Call call, Response response) throws IOException {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtil.shortToast(getActivity(), "绑定成功");
                                            }
                                        });

                                        dialog.dismiss();

                                    }

                                    @Override
                                    public void failed(Call call, IOException e) {

                                    }
                                });
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                        .create()
                        .show();


            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

}
