package com.byx.xiuboss.xiuboss.Mvp.fragmment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.byx.xiuboss.xiuboss.Bean.ShouYeBean;
import com.byx.xiuboss.xiuboss.Bean.TimeBean;
import com.byx.xiuboss.xiuboss.Bean.TurnoverData;
import com.byx.xiuboss.xiuboss.Bean.TurnoverTwoData;
import com.byx.xiuboss.xiuboss.Mvp.activity.DetailsActivity;
import com.byx.xiuboss.xiuboss.Mvp.activity.MyerweimaActivity;
import com.byx.xiuboss.xiuboss.Mvp.adapter.TurnoverAdapter;
import com.byx.xiuboss.xiuboss.Mvp.adapter.TurnoverTwoAdapter;
import com.byx.xiuboss.xiuboss.Mvp.net.OkHttpUtils;
import com.byx.xiuboss.xiuboss.R;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by ASUS on 2018/7/17.
 */

public class FindFragment extends Fragment {


    private SharedPreferences share;
    private SharedPreferences.Editor edit;
    private String urls = "https://www.ourdaidai.com/CI/index.php/Login/login";
    private RollPagerView pager;
    private WebView webFind;
    private TextView homeTodayText;
    private TextView homeDown;
    private TextView todayUp;
    private PopupWindow popupWindow;
    private View homeTime;
    private String url;
    private String sid;
    private boolean isGetData = false;
    private View view;
    private Switch isOpen;
    private TextView shopName;
    private View findEWM;
    private View linFind;
    private int i=0;
    private RecyclerView recyclerView;
    private TurnoverAdapter mAdapter_item;
    private TurnoverTwoAdapter mAdapter_item_two;
    private static final String url_one = "https://www.ourdaidai.com/CI/index.php/Store/todayMoney";
    private static final String url_two = "https://www.ourdaidai.com/CI/index.php/Store/storeMoneySum";
    private static final String url_there = "https://www.ourdaidai.com/CI/index.php/Store/todayMoney";
    private List<TurnoverData.DataBean> listData;
    private List<TurnoverTwoData.DataBean> data;
    private View footView;

    private int pageIndex = 0;
    private RecyclerView recycler_two;
    private List<TimeBean.DataBean> listtime = new ArrayList<>();
    private SmartRefreshLayout smar;
    private RecyclerView reclcler;
    private ListView lvs;
    private FindFragment findFragment;


    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_find, container, false);
            initView(view);
            initData();
            syncCookie(urls);
            findFragment = new FindFragment();

            return view;

    }

    private void initData() {
        share = getContext().getSharedPreferences("login_sucess", getContext().MODE_PRIVATE);
        sid = share.getString("sid", "");
        Log.e("+++++++++++sid",sid+"");
        listData = new ArrayList<>();
        mAdapter_item = new TurnoverAdapter(getActivity(), listData);
        mAdapter_item.addFooterView(footView);
        recyclerView.setAdapter(mAdapter_item);
        obtainDataOne();
        obtainDataThree();



        /******/
        OkHttpUtils.getInstance().getDataAsynFromNet("https://www.ourdaidai.com/CI/index.php/Store/money?sid=" + sid, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                getData(response.body().string());

            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });

        final ArrayList<View> views = new ArrayList<>();
        View homeToday = LayoutInflater.from(getContext()).inflate(R.layout.home_today, null);
        View viewById = homeToday.findViewById(R.id.linFindF);
        viewById.setPadding(0,getStatusBarHeight()+25,0,0);
        homeTodayText = homeToday.findViewById(R.id.homeToday);
        shopName.setPadding(10,getStatusBarHeight()+15,10,0);
        findEWM.setPadding(10,getStatusBarHeight()+15,10,0);

        View homeSum = LayoutInflater.from(getContext()).inflate(R.layout.home_sum, null);
        homeDown = homeSum.findViewById(R.id.homeDown);
        View viewById1 = homeSum.findViewById(R.id.linSun);
        viewById1.setPadding(0,getStatusBarHeight()+25,0,0);

        homeTime = LayoutInflater.from(getContext()).inflate(R.layout.home_time, null);
        View viewById2 = homeTime.findViewById(R.id.linTime);
        viewById2.setPadding(0,getStatusBarHeight()+15,0,0);

        View homeTodayUp = LayoutInflater.from(getContext()).inflate(R.layout.home_today_up, null);
        View viewById3 = homeTodayUp.findViewById(R.id.linTU);
        viewById3.setPadding(0,getStatusBarHeight()+25,0,0);

        todayUp = homeTodayUp.findViewById(R.id.todayUp);


        views.add(homeToday);
        views.add(homeSum);
        views.add(homeTodayUp);

        pager.setAdapter(new StaticPagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public View getView(ViewGroup container, int position) {

                return views.get(position);
            }
        });
        pager.setPlayDelay(3000);
    }

    /****/


    /****/
    private void getData(String string) {
        Gson gson = new Gson();
        Log.e("======aaa=====",string);
        final ShouYeBean shouYeBean = gson.fromJson(string, ShouYeBean.class);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Log.e("shouyename", shouYeBean.getData().getTitle());
                homeTodayText.setText("￥" + shouYeBean.getData().getSum());
                homeDown.setText("￥" + shouYeBean.getData().getDown());
                todayUp.setText("￥" + shouYeBean.getData().getUp());
                shopName.setText("" + shouYeBean.getData().getTitle());

                Log.e("shouyename", shouYeBean.getData().getTitle());
                if (shouYeBean.getData().getIs_in_business().equals("1")) {
                    isOpen.setChecked(true);
                } else {
                    isOpen.setChecked(false);

                }
            }
        });
    }

    public boolean syncCookie(String url) {
        share = getActivity().getSharedPreferences("login_sucess", getActivity().MODE_PRIVATE);
        String mycookies = share.getString("mycookie", "");
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, "0122_we7_wmall_manager_session_2=" + mycookies);
        String newCook = cookieManager.getCookie(url);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(getActivity());
            cookieSyncManager.sync();
        }
        return TextUtils.isEmpty(newCook) ? false : true;
    }


    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_wang);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        footView = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_foot_item, null);
        recycler_two = footView.findViewById(R.id.recycler_two);
        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        recycler_two.setLayoutManager(manager1);
        share = getContext().getSharedPreferences("login_sucess", getContext().MODE_PRIVATE);
        sid = share.getString("sid", "");
        pager = view.findViewById(R.id.pager);
      /*  webFind = view.findViewById(R.id.webFind);*//***byx*/
        isOpen = view.findViewById(R.id.isOpen);
        shopName = view.findViewById(R.id.shopName);
        findEWM = view.findViewById(R.id.fineEWM);
        linFind = view.findViewById(R.id.linFind);

        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                obtainDataOne();
                Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
            }
        });
        //加载更多
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
                obtainDataTwo();
            }
        });

        //设置 Header 为 Material样式
        refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        lvs=(ListView)  view.findViewById(R.id.lv);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.fineEWM)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), MyerweimaActivity.class));
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

    private void obtainDataThree() {
        //请求列表二的数据
        Map<String, String> params = new HashMap<>();
        params.put("sid", sid);

        OkHttpUtils.getInstance().postDataAsynToNet(url_two, params, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, final Response response) throws IOException {
                final String message = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("第二个接口的请求数据", message);
                        Gson gson = new Gson();
                        TurnoverTwoData turnoverTwoData = gson.fromJson(message, TurnoverTwoData.class);
                        data = turnoverTwoData.getData();
                        mAdapter_item_two = new TurnoverTwoAdapter(getActivity(),data);
                        recycler_two.setAdapter(mAdapter_item_two);
                        mAdapter_item_two.setListener(new TurnoverTwoAdapter.onListener() {
                            @Override
                            public void OnListener(int i) {
                                Map<String,String> map=new HashMap<>();
                                map.put("sid",sid);
                                map.put("start",pageIndex+"");
                                map.put("stat_day",data.get(i).getStat_day());
                                Intent intent=new Intent(getContext(),DetailsActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("map", (Serializable) map);
                                intent.putExtras(bundle);
                                getActivity().startActivity(intent);

                            }
                        });
                    }
                });
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    public void obtainDataTwo() {
        //第二个列表加载更多请求
        Map<String,String> map=new HashMap<>();
        map.put("sid",sid);
        map.put("start",(pageIndex+=5)+"");
        OkHttpUtils.getInstance().postDataAsynToNet(url_there, map, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.e("----shuju-----",string);
                getActivity().runOnUiThread(new Runnable() {

                    private List<TurnoverTwoData.DataBean> list;

                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        list=new ArrayList<>();
                        TurnoverTwoData turnoverTwoData = gson.fromJson(string, TurnoverTwoData.class);
                        list = turnoverTwoData.getData();
                        if (list==null){
                            Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                        }else{
                            data.addAll(list);
                            mAdapter_item_two.notifyDataSetChanged();
                        }
                    }
                });

            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    private void obtainDataOne() {
        //请求列表一的数据
        Map<String, String> params = new HashMap<>();
        params.put("sid", sid);
        params.put("start", pageIndex + "");

        OkHttpUtils.getInstance().postDataAsynToNet(url_one, params, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, final Response response) throws IOException {
                final String message = response.body().string();
               getActivity(). runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("第一个接口的请求数据", message);
                        Gson gson = new Gson();
                        TurnoverData data = gson.fromJson(message, TurnoverData.class);
                        listData = data.getData();
                        mAdapter_item.setData(listData);
                    }
                });
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

}
