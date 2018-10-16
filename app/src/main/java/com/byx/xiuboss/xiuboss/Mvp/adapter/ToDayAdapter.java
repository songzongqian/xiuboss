package com.byx.xiuboss.xiuboss.Mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.byx.xiuboss.xiuboss.Bean.ToDayBean;
import com.byx.xiuboss.xiuboss.Bean.TurnoverTwoData;
import com.byx.xiuboss.xiuboss.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wangwenjie001 on 2018/10/12.
 */

public class ToDayAdapter extends RecyclerView.Adapter<ToDayAdapter.ViewHoder> {
    private List<ToDayBean.DataBean> toDayList;
    private Context mContext;

    public ToDayAdapter(List<ToDayBean.DataBean> toDayList, Context mContext) {
        this.toDayList = toDayList;
        this.mContext = mContext;
    }
    public void setData(List<ToDayBean.DataBean> toDayList) {
        this.toDayList = toDayList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.turn_over_adapter_item, null);

        return new ViewHoder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        Glide.with(mContext).load(toDayList.get(position).getAvatar()).into(holder.image_item_one);
        holder.text_name.setText(toDayList.get(position).getNickname());
        String addtime = toDayList.get(position).getAddtime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Long aLong = Long.valueOf(addtime);
        long lt = new Long(aLong*1000);
        Date date = new Date(lt);
        holder.text_time.setText(simpleDateFormat.format(date));
        holder.text_money.setText("+"+toDayList.get(position).getPrice());
        if (toDayList.get(position).getMoney_total().equals("0")){
            holder.text_money_item_wwj.setVisibility(View.GONE);
        }else{
            holder.text_money_item_wwj.setText(toDayList.get(position).getMoney_total());
        }
    }

    @Override
    public int getItemCount() {
        return toDayList.isEmpty() ? 0 : toDayList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {

        private ImageView image_item_one;
        private TextView text_name, text_time, text_money, text_jia,text_money_item_wwj;
        public ViewHoder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            image_item_one = itemView.findViewById(R.id.image_head_item_one);
            text_name = itemView.findViewById(R.id.text_name_item_one);
            text_time = itemView.findViewById(R.id.text_time_item_one);
            text_money = itemView.findViewById(R.id.text_money_item_one);
            text_money_item_wwj = itemView.findViewById(R.id.text_money_item_wwj);
        }
    }
}
