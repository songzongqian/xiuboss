package com.byx.xiuboss.xiuboss.Mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.byx.xiuboss.xiuboss.Bean.TurnoverTwoData;
import com.byx.xiuboss.xiuboss.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wwj on 2018/9/1.
 */

public class TurnoverTwoAdapter extends RecyclerView.Adapter<TurnoverTwoAdapter.ViewHolder> {

    private Context context;
    private List<TurnoverTwoData.DataBean> data;
    private View VIEW_HEADER;
    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER  = 1002;

    private  onListener listener;


    public TurnoverTwoAdapter(Context context, List<TurnoverTwoData.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<TurnoverTwoData.DataBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new ViewHolder( LayoutInflater.from(context).inflate(R.layout.turn_over_adapter_item_two, parent, false));


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder){
            String stat_day = data.get(position).getStat_day();
            String month = stat_day.substring(4, 6);
            String day = stat_day.substring(6, 8);
            Log.e("month",month);
            Log.e("day",day);
            holder.text_money.setText(month+"月"+day+"日"+"收款"+data.get(position).getSum()+"笔,退款"+data.get(position).getReturnMoney()+"笔,合计");
            holder.text_time.setText(data.get(position).getStat_day());
            holder.text_content.setText(data.get(position).getPrice());
            holder.recycler_wwj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnListener(position);
                }
            });

        }

    }

    public static String getDateToString(long milSecond, String pattern) {
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    @Override
    public int getItemCount() {

        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text_time, text_money, text_content;
        private final RelativeLayout recycler_wwj;

        public ViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            text_time = itemView.findViewById(R.id.text_time_item_two);
            text_money = itemView.findViewById(R.id.text_money_item_two);
            text_content = itemView.findViewById(R.id.text_content_item_two);
            recycler_wwj = (RelativeLayout)itemView.findViewById(R.id.recycler_wwj);
        }
    }
    public interface  onListener{
        void OnListener(int i);
    }
    public void setListener( onListener listener) {
        this.listener = listener;
    }



    }
