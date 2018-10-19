package com.byx.xiuboss.xiuboss.Mvp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.byx.xiuboss.xiuboss.Bean.ToDayBean;
import com.byx.xiuboss.xiuboss.Mvp.adapter.ToDayAdapter;
import com.byx.xiuboss.xiuboss.Mvp.net.OkHttpUtils;
import com.byx.xiuboss.xiuboss.R;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class TodayMoneyActivity extends BaseActivity {

    @BindView(R.id.title_back_image)
    ImageView titleBackImage;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.today_recycler)
    RecyclerView todayRecycler;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.empty)
    LinearLayout empty;
    private String todayUrl = "https://www.ourdaidai.com/CI/index.php/Store/todayMoney";
    private int start = 0;
    private SharedPreferences sp;
    private List<ToDayBean.DataBean> toDayList;
    private ToDayAdapter adapter;
    private String sid;
    private String stat_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_money);
        ButterKnife.bind(this);
        sp = getSharedPreferences("login_sucess", MODE_PRIVATE);
        initData();
    }

    private void initData() {
        titleText.setText("今日收入明细");
        //刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                adapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
            }
        });
        //加载更多
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshLayout) {
                obtainDataTwo();
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadmore();
            }
        });
        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        stat_day = year + "" + month + day + "";
        sid = sp.getString("sid", "");
        Map<String, String> map = new HashMap<>();
        map.put("sid", sid);
        map.put("stat_day", stat_day);
        map.put("start", start + "");
        OkHttpUtils.getInstance().postDataAsynToNet(todayUrl, map, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                String string = response.body().string();
                getData(string);
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });

    }

    private void getData(String data) {
        if (data==null){

            refreshLayout.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }else {

            refreshLayout.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            Gson gson = new Gson();
            final ToDayBean toDayBean = gson.fromJson(data, ToDayBean.class);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toDayList = toDayBean.getData();
                    adapter = new ToDayAdapter(toDayList, TodayMoneyActivity.this);
                    todayRecycler.setLayoutManager(new LinearLayoutManager(TodayMoneyActivity.this));
                    todayRecycler.setAdapter(adapter);
                }
            });
        }

    }

    @OnClick(R.id.title_back_image)
    public void onViewClicked() {
        finish();
    }

    public void obtainDataTwo() {
        //第二个列表加载更多请求
        Map<String, String> map = new HashMap<>();
        map.put("sid", sid);
        map.put("stat_day", stat_day);
        map.put("start", start + 10 + "");
        OkHttpUtils.getInstance().postDataAsynToNet(todayUrl, map, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Gson gson = new Gson();
                final ToDayBean turnoverTwoData = gson.fromJson(string, ToDayBean.class);
                final List<ToDayBean.DataBean> list = turnoverTwoData.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (turnoverTwoData.getMessage().equals("没有数据")) {
                            Toast.makeText(TodayMoneyActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                        } else {
                            toDayList.addAll(list);
                            adapter.setData(toDayList);
                        }
                    }
                });

            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

}
