package com.byx.xiuboss.xiuboss.Jgim.utils;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.byx.xiuboss.xiuboss.MainActivity;
import com.byx.xiuboss.xiuboss.Mvp.adapter.ViewPagerAdapter;
import com.byx.xiuboss.xiuboss.Mvp.fragmment.BusinessFragment;
import com.byx.xiuboss.xiuboss.Mvp.fragmment.ConversationListFragment;
import com.byx.xiuboss.xiuboss.Mvp.fragmment.FindFragment;
import com.byx.xiuboss.xiuboss.Mvp.fragmment.MyFragment;
import com.byx.xiuboss.xiuboss.Mvp.view.MainView;
import com.byx.xiuboss.xiuboss.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2018/7/23.
 */

public class MainController implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private MainView mMainView;
    private MainActivity mContext;
    private ConversationListFragment mConvListFragment;
    private MyFragment mMyFragment;
    private FindFragment mContactsFragment;
    private BusinessFragment mBusinessFragment;

    public MainController(MainView mMainView, MainActivity context) {
        this.mMainView = mMainView;
        this.mContext = context;
        setViewPager();
    }

    private void setViewPager() {
        final List<Fragment> fragments = new ArrayList<>();
        // init Fragment
        mConvListFragment = new ConversationListFragment();
        mContactsFragment = new FindFragment();
        mMyFragment = new MyFragment();

        mBusinessFragment =new BusinessFragment();
        fragments.add(mContactsFragment);
      //  fragments.add(orderFragment);
        fragments.add(mConvListFragment);
        fragments.add(mBusinessFragment);
        fragments.add(mMyFragment);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext.getSupportFragmentManger(),
                fragments);
        mMainView.setViewPagerAdapter(viewPagerAdapter);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {//    actionbar_me_bts
            case R.id.actionbar_home_btn:
                mMainView.setCurrentItem(0, false);
                break;
            case R.id.actionbar_bill_btn:
                mMainView.setCurrentItem(1, false);
                break;
           /* case R.id.   actionbar_msg_btn:
                mMainView.setCurrentItem(2, false);
                break;
            case R.id.actionbar_me_bts:
                mMainView.setCurrentItem(3, false);
                break;*/
            case R.id.actionbar_my_btn:
                mMainView.setCurrentItem(3, false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mMainView.setButtonColor(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
