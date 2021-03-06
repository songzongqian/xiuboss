package com.byx.xiuboss.xiuboss.Mvp.view;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.byx.xiuboss.xiuboss.Jgim.utils.ScrollControlViewPager;
import com.byx.xiuboss.xiuboss.R;


/**
 * Created by ASUS on 2018/7/23.
 */

public class MainView extends RelativeLayout {

    private RelativeLayout[] mBtnList;
    private int[] mBtnListID;
    private ScrollControlViewPager mViewContainer;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void initModule() {
        // R.id.actionbar_msg_btn  ,R.id.actionbar_me_bts,
        mBtnListID = new int[] {
                R.id.actionbar_home_btn  , R.id.actionbar_bill_btn, R.id.actionbar_my_btn
        };
        mBtnList = new RelativeLayout[3];
        for (int i = 0; i < 3; i++) {
            mBtnList[i] = (RelativeLayout) findViewById(mBtnListID[i]);
        }
        mViewContainer = (ScrollControlViewPager) findViewById(R.id.viewpager);
//        mBtnList[0].setTextColor(getResources().getColor(R.color.actionbar_pres_color));
        mBtnList[0].setSelected(true);
    }

    public void setOnClickListener(OnClickListener onclickListener) {
        for (int i = 0; i < mBtnListID.length; i++) {
            mBtnList[i].setOnClickListener(onclickListener);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mViewContainer.addOnPageChangeListener(onPageChangeListener);
    }

    public void setViewPagerAdapter(FragmentPagerAdapter adapter) {
        mViewContainer.setAdapter(adapter);
    }

    public void setCurrentItem(int index, boolean scroll) {
        mViewContainer.setCurrentItem(index, scroll);
    }

    public void setButtonColor(int index) {
        for (int i = 0; i < 3; i++) {
            if (index == i) {
                mBtnList[i].setSelected(true);
//                mBtnList[i].setBackgroundColor(getResources().getColor(R.color.actionbar_pres_color));
            } else {
                mBtnList[i].setSelected(false);
//                mBtnList[i].setTextColor(getResources().getColor(R.color.action_bar_txt_color));
            }
        }
    }

}
