package com.byx.xiuboss.xiuboss.Mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.byx.xiuboss.xiuboss.Bean.MyBalanceBean;
import com.byx.xiuboss.xiuboss.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwenjie001 on 2018/10/4.
 */

public class BalanceAdapter extends RecyclerView.Adapter<BalanceAdapter.ViewHolder> {
        private List<MyBalanceBean.DataBeanX.DataBean> lance=new ArrayList<>();
        private Context context;

    public BalanceAdapter(List<MyBalanceBean.DataBeanX.DataBean> lance, Context context) {
        this.lance = lance;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.balance_item, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.money.setText("- "+lance.get(position).getGet_fee());
        String channel = lance.get(position).getChannel();
        if (channel.equals("alipay")){
            holder.putforward.setText("提现-支付宝");
        }else{
            holder.putforward.setText("提现 - "+lance.get(position).getChannel());
        }

        holder.time.setText(lance.get(position).getRegisterTime());
        int i = Integer.parseInt(lance.get(position).getStatus());
        if (i==1){
            holder.status.setText("已到账");
            holder.status.setTextColor(0xFF999999);
        }else if (i==2){
            holder.status.setText("处理中");
            holder.status.setTextColor(0xFF999999);
        }else if (i==3){
            holder.status.setText("提现被撤销");
            holder.status.setTextColor(0xFFFE2741);
        }

    }

    @Override
    public int getItemCount() {
        return lance.isEmpty()?0:lance.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private  TextView putforward,time,money,status;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            putforward = itemView.findViewById(R.id.Putforward);
             time = itemView.findViewById(R.id.time);
             money = itemView.findViewById(R.id.money);
             status = itemView.findViewById(R.id.status);
        }
    }
}
