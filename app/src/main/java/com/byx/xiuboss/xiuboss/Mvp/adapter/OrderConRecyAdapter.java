package com.byx.xiuboss.xiuboss.Mvp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.byx.xiuboss.xiuboss.Bean.OrderConfirmBean;
import com.byx.xiuboss.xiuboss.Mvp.activity.XiangQingActivity;
import com.byx.xiuboss.xiuboss.Mvp.model.User;
import com.byx.xiuboss.xiuboss.R;
import com.scwang.smartrefresh.header.material.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by TauchMe on 2018/7/19.
 *
 */

public class OrderConRecyAdapter extends RecyclerView.Adapter<OrderConRecyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderConfirmBean.DataBean> orderConfirmBeans;
    private ButtonInterface buttonInterface;
    private Imgmessage imgmessage;
    private ButtonCancelInterface buttonCancelInterface;
    private boolean sex;

    public OrderConRecyAdapter(Context context, ArrayList<OrderConfirmBean.DataBean> orderConfirmBeans, boolean sex) {
        this.context = context;
        this.orderConfirmBeans = orderConfirmBeans;
        this.sex = sex;
    }

    public void buttonSetOnclick(ButtonInterface buttonInterface) {
        this.buttonInterface = buttonInterface;
    }

    public interface ButtonInterface {
        public void onclick(View view, int position);
    }
    public interface Imgmessage {
        public void onclick(View view, int position);
    }

    public void buttonCancelSetOnclick(ButtonCancelInterface buttonCancelInterface) {
        this.buttonCancelInterface = buttonCancelInterface;
    }

    public void Imgmessage(Imgmessage imgmessage) {
        this.imgmessage = imgmessage;
    }

    public interface ButtonCancelInterface {
        public void onclick(View view, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.con_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final HashMap<String, String> objectObjectHashMap = new HashMap<>();
        final Intent intent = new Intent(context, XiangQingActivity.class);
        String s = User.XIANG + orderConfirmBeans.get(position).getId();
        intent.putExtra("url", s);
        String username = orderConfirmBeans.get(position).getUsername();
        String sex = orderConfirmBeans.get(position).getSex();
//        String substring = username.substring(0, 1);
//        String name;
//
//
//        if (sex.equals("先生")) {
//            name = substring + "先生";
//            holder.nameText.setText(name);
//        } else {
//            name = substring + "女士";
//            holder.nameText.setText(name);
//        }


        holder.nameText.setText(username+sex);

        holder.time.setText(orderConfirmBeans.get(position).getDelivery_day());
        holder.price.setText(orderConfirmBeans.get(position).getPlateform_serve().getNote());
        if (orderConfirmBeans.size() != 0) {
            List<OrderConfirmBean.DataBean.CartBean> cart = orderConfirmBeans.get(position).getCart();
            holder.recy.setAdapter(new OrderConRecyItemAdapter(context, cart, s));
        }
        Log.e("stringS", orderConfirmBeans.contains("note") + "");
//        if (orderConfirmBeans.get(position).toString().indexOf("note") != -1) {
//            holder.remarkes.setText(orderConfirmBeans.get(position).getNote());
//
//        }

        holder.song.setText(orderConfirmBeans.get(position).getDelivery_time());
        holder.buttWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(intent);
            }
        });
//        holder.remarkes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(intent);
//            }
//        });
        Glide.with(context).load(orderConfirmBeans.get(position).getAvatar()).into(holder.headIcon);
        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + orderConfirmBeans.get(position).getMobile()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.priceNub.setText(orderConfirmBeans.get(position).getPrice());

        if (orderConfirmBeans.get(position).getStatus().equals("1") && orderConfirmBeans.get(position).getDelivery_status().equals("0")){
            holder.sure.setText("待确认");
        }else if (orderConfirmBeans.get(position).getStatus().equals("4") && orderConfirmBeans.get(position).getDelivery_status().equals("7")){
            holder.sure.setText("待配送");
        }else if (orderConfirmBeans.get(position).getStatus().equals("4") && orderConfirmBeans.get(position).getDelivery_status().equals("4")){
            holder.sure.setText("配送中");
        }else if (orderConfirmBeans.get(position).getStatus().equals("5") && orderConfirmBeans.get(position).getDelivery_status().equals("5")){
            holder.sure.setText("已完成");
        }else if (orderConfirmBeans.get(position).getStatus().equals("6") && orderConfirmBeans.get(position).getDelivery_status().equals("6")){
            holder.sure.setText("已取消");
        }

        if (orderConfirmBeans.get(position).getStatus().equals("1") && orderConfirmBeans.get(position).getDelivery_status().equals("0")) {
            holder.buttonCon.setText("确认订单");
        } else if (orderConfirmBeans.get(position).getStatus().equals("4") && orderConfirmBeans.get(position).getDelivery_status().equals("7")) {
            holder.buttonCon.setText("通知本店配送员");
            holder.buttonCon.setBackgroundResource(R.drawable.button_tz);
        } else if (orderConfirmBeans.get(position).getStatus().equals("4") && orderConfirmBeans.get(position).getDelivery_status().equals("4")){
            holder.buttonCon.setText("完成订单");
            holder.buttonCon.setBackgroundResource(R.drawable.button_over);
        }else {
            holder.buttonCon.setVisibility(View.INVISIBLE);
        }
        holder.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonCancelInterface != null) {
//                  接口实例化后的而对象，调用重写后的方法
                    buttonCancelInterface.onclick(view, position);
                }
            }
        });
        holder.buttonCon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (buttonInterface != null) {
//                  接口实例化后的而对象，调用重写后的方法
                    buttonInterface.onclick(view, position);
                }

            }
        });
    holder.number.setText(orderConfirmBeans.get(position).getSerial_sn());
            holder.message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (imgmessage != null) {
//                  接口实例化后的而对象，调用重写后的方法
                        imgmessage.onclick(view, position);
                    }
                }
            });
            holder.recyLin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(intent);

                }
            });
            holder.lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(intent);

                }
            });

    }

    @Override
    public int getItemCount() {
        return orderConfirmBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout lin;
        private LinearLayout recyLin;
        private ImageView message;
        private TextView number;
        private Button buttonCon;
        private TextView buttonCancel;
        private RelativeLayout buttonRela;
        private ImageView phone;
        private RelativeLayout buttWeb;
        private TextView sure;
        private CircleImageView headIcon;
        private View view;
        private TextView song;
        private TextView remarkes;
        private RecyclerView recy;
        private TextView price;
        private TextView priceNub;
        private TextView time;
        private TextView nameText;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            nameText = itemView.findViewById(R.id.nameText);
            time = itemView.findViewById(R.id.time);
            price = itemView.findViewById(R.id.priceTrue);
            recy = itemView.findViewById(R.id.recyCommodity);
            recy.setLayoutManager(new LinearLayoutManager(context){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
//            recy.setSaveEnabled(false);
//            remarkes = itemView.findViewById(R.id.remarks);
            song = itemView.findViewById(R.id.song);
            headIcon = itemView.findViewById(R.id.headIcon);
            sure = itemView.findViewById(R.id.sure);
            buttWeb = itemView.findViewById(R.id.buttWeb);
            phone = itemView.findViewById(R.id.phone);
            priceNub = itemView.findViewById(R.id.price);
            buttonRela = itemView.findViewById(R.id.buttonRela);
            buttonCon = itemView.findViewById(R.id.buttonConfirm);
            buttonCancel = itemView.findViewById(R.id.buttonCancel);
            number = itemView.findViewById(R.id.number);
            message = itemView.findViewById(R.id.xiaoxi);
            recyLin = itemView.findViewById(R.id.recyLin);
            lin = itemView.findViewById(R.id.rela);
        }
    }
}
