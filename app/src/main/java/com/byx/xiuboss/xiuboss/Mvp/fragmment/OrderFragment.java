package com.byx.xiuboss.xiuboss.Mvp.fragmment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.byx.xiuboss.xiuboss.Mvp.adapter.OrderPagerAdapter;
import com.byx.xiuboss.xiuboss.Mvp.orderFragment.OrderAllFragment;
import com.byx.xiuboss.xiuboss.Mvp.orderFragment.OrderCancleFragment;
import com.byx.xiuboss.xiuboss.Mvp.orderFragment.OrderConfirmFragment;
import com.byx.xiuboss.xiuboss.Mvp.orderFragment.OrderDistributionFragment;
import com.byx.xiuboss.xiuboss.Mvp.orderFragment.OrderIngFragment;
import com.byx.xiuboss.xiuboss.Mvp.orderFragment.OrderOverFragment;
import com.byx.xiuboss.xiuboss.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private TabLayout tabTitle;
    private ViewPager pager;
    private ArrayList<String> tabString;
    private ArrayList<Fragment> fragments;
    private TextView sure;
    private SharedPreferences share;
    private SharedPreferences.Editor edit;
    private String sid;
    private boolean isGetData = false;

    private android.support.v4.app.FragmentManager manager;
    private FragmentTransaction ft;
    private TextView titleOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tabTitle = view.findViewById(R.id.tabTitle);
        titleOrder = view.findViewById(R.id.titleOrder);
        titleOrder.setPadding(0,getStatusBarHeight()+10,0,20);
        pager = view.findViewById(R.id.pager);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.con_item, null);
        sure = inflate.findViewById(R.id.sure);
    }
    private void initData() {
        tabString = new ArrayList<>();
        tabString.add("待确认");
        tabString.add("待配送");
        tabString.add("配送中");
        tabString.add("已完成");
        tabString.add("已取消");
        tabString.add("所有");

        fragments = new ArrayList<>();



        OrderConfirmFragment orderConfirmFragment = new OrderConfirmFragment();
        OrderAllFragment orderAllFragment = new OrderAllFragment();
        OrderCancleFragment orderCancleFragment = new OrderCancleFragment();
        OrderDistributionFragment orderDistributionFragment = new OrderDistributionFragment();
        OrderIngFragment orderIngFragment = new OrderIngFragment();
        OrderOverFragment orderOverFragment = new OrderOverFragment();


        fragments.add(orderConfirmFragment);
        fragments.add(orderDistributionFragment);
        fragments.add(orderIngFragment);
        fragments.add(orderOverFragment);
        fragments.add(orderCancleFragment);
        fragments.add(orderAllFragment);


        Bundle bundle;

        OrderPagerAdapter orderPagerAdapter = new OrderPagerAdapter(getChildFragmentManager(), tabString, fragments);

        pager.setAdapter(orderPagerAdapter);
        tabTitle.setupWithViewPager(pager);
        orderPagerAdapter.notifyDataSetChanged();

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
}
