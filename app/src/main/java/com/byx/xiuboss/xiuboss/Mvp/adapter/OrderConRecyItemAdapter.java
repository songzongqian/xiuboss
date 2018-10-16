package com.byx.xiuboss.xiuboss.Mvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.byx.xiuboss.xiuboss.Bean.OrderConfirmBean;
import com.byx.xiuboss.xiuboss.Mvp.activity.XiangQingActivity;
import com.byx.xiuboss.xiuboss.R;

import java.util.List;

/**
 * Created by TauchMe on 2018/7/23.
 *
 */

public class OrderConRecyItemAdapter extends RecyclerView.Adapter<OrderConRecyItemAdapter.ViewHolder> {
    private Context context;
    private List<OrderConfirmBean.DataBean.CartBean> cart;
    private String string;

    public OrderConRecyItemAdapter(Context context, List<OrderConfirmBean.DataBean.CartBean> cart, String string) {
        this.context = context;
        this.cart = cart;
        this.string = string;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.con_recy_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.food.setText(cart.get(position).getTitle());
        String s = cart.get(position).getOptions().get(0).getNum() + "";
        if (Integer.parseInt(s)>1){
            holder.num.setTextColor(Color.RED);
        }
        holder.num.setText(s);
        holder.priceOne.setText(cart.get(position).getOptions().get(0).getPrice()+"");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, XiangQingActivity.class);
                intent.putExtra("url", string);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView priceOne;
        private TextView num;
        private TextView food;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            food = itemView.findViewById(R.id.food);
            num = itemView.findViewById(R.id.num);
            priceOne = itemView.findViewById(R.id.priceOne);
        }
    }
}
