package com.byx.xiuboss.xiuboss.Mvp.orderFragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.byx.xiuboss.xiuboss.Application.JgApplication;
import com.byx.xiuboss.xiuboss.Bean.OrderConfirmBean;
import com.byx.xiuboss.xiuboss.Mvp.activity.ChatActivity;
import com.byx.xiuboss.xiuboss.Mvp.adapter.OrderConRecyAdapter;
import com.byx.xiuboss.xiuboss.Mvp.model.User;
import com.byx.xiuboss.xiuboss.Mvp.net.OkHttpUtils;
import com.byx.xiuboss.xiuboss.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import okhttp3.Call;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderIngFragment extends Fragment {


    private PullToRefreshRecyclerView conRecy;
    private OrderConRecyAdapter orderConRecyAdapter;
    private ArrayList<OrderConfirmBean.DataBean> orderConfirmBeans;
    private View viewById;
    private String url;
    private String name;
    private HashMap<String, String> objectObjectHashMap;
    private int i = 0;
    private boolean sex;
    private String APPKEY = "f4bb353825d2be3954a8ba00";
    private SharedPreferences share;
    private String sid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_order_son, container, false);
        initView(inflate);
        Bundle arguments = getArguments();
        share = getContext().getSharedPreferences("login_sucess", getContext().MODE_PRIVATE);
        sid = share.getString("sid", "");
        url = "https://www.ourdaidai.com/CI/index.php/order/cart?sid=" + sid + "&start=0&status=4&delivery_status=4";
        name = "配送中";
        initData(url);
        return inflate;
    }

    private void initData(String urlNew) {

        OkHttpUtils.getInstance().getDataAsynFromNet(urlNew, new OkHttpUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException {
                getData(response.body().string());
            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });

    }

    private void getData(final String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject data = jsonObject.getJSONObject("data");
            sex = data.has("sex");
            data.has("sex");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                if (string.length() < 100 && orderConfirmBeans.size() == 0) {
                    viewById.setVisibility(View.VISIBLE);
                    conRecy.setVisibility(View.INVISIBLE);
                } else {
                    OrderConfirmBean orderConfirmBean = gson.fromJson(string, OrderConfirmBean.class);
                    List<OrderConfirmBean.DataBean> data = orderConfirmBean.getData();
                    viewById.setVisibility(View.INVISIBLE);
                    conRecy.setVisibility(View.VISIBLE);
                    orderConfirmBeans.addAll(data);
                    orderConRecyAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    private void initView(View inflate) {
        objectObjectHashMap = new HashMap<>();
        conRecy = inflate.findViewById(R.id.conRecy);
        conRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        orderConfirmBeans = new ArrayList<>();
        orderConRecyAdapter = new OrderConRecyAdapter(getContext(), orderConfirmBeans, sex);
        conRecy.setAdapter(orderConRecyAdapter);
        viewById = inflate.findViewById(R.id.no);
        viewById.setVisibility(View.VISIBLE);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData(url);
            }
        });
        orderConRecyAdapter.Imgmessage(new OrderConRecyAdapter.Imgmessage() {


            @Override
            public void onclick(View view, int position) {
                String openid=orderConfirmBeans.get(position).getOpenid();
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                Conversation conv = Conversation.createSingleConversation(openid, APPKEY);
                String targetId = ((UserInfo) conv.getTargetInfo()).getUserName();
                intent.putExtra(JgApplication.TARGET_ID, targetId);
                intent.putExtra(JgApplication.TARGET_APP_KEY, APPKEY);
//                intent.putExtra(JGApplication.DRAFT, openid);

                intent.putExtra(JgApplication.CONV_TITLE, ((UserInfo) conv.getTargetInfo()).getNickname());
                startActivity(intent);

            }
        });
        orderConRecyAdapter.buttonCancelSetOnclick(new OrderConRecyAdapter.ButtonCancelInterface() {
            @Override
            public void onclick(View view, int position) {
//                objectObjectHashMap.put("sid",orderConfirmBeans.get(position).getSid());
                objectObjectHashMap.put("id",orderConfirmBeans.get(position).getId());
//                objectObjectHashMap.put("sid",orderConfirmBeans.get(position).getSid());
                objectObjectHashMap.put("status", "6");
                objectObjectHashMap.put("delivery_status", "6");
                OkHttpUtils.getInstance().postDataAsynToNet(User.ORDERDATA, objectObjectHashMap, new OkHttpUtils.MyNetCall() {
                    @Override
                    public void success(Call call, Response response) throws IOException {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                conRecy.onRefresh();
                                orderConRecyAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void failed(Call call, IOException e) {

                    }
                });
            }
        });
        orderConRecyAdapter.buttonSetOnclick(new OrderConRecyAdapter.ButtonInterface() {
            @Override
            public void onclick(View view, int position) {
//                objectObjectHashMap.put("sid",orderConfirmBeans.get(position).getSid());
                objectObjectHashMap.put("id",orderConfirmBeans.get(position).getId());
                objectObjectHashMap.put("status", orderConfirmBeans.get(position).getStatus());
                objectObjectHashMap.put("delivery_status", orderConfirmBeans.get(position).getDelivery_status());


                OkHttpUtils.getInstance().postDataAsynToNet(User.ORDERDATA, objectObjectHashMap, new OkHttpUtils.MyNetCall() {
                    @Override
                    public void success(Call call, Response response) throws IOException {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                conRecy.onRefresh();
                                orderConRecyAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void failed(Call call, IOException e) {
                    }
                });
            }
        });
        conRecy.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                orderConfirmBeans.clear();
                initData(url);
                orderConRecyAdapter.notifyDataSetChanged();

                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                conRecy.setRefreshComplete();
            }

            @Override
            public void onLoadMore() {

                i++;
                String urlNew = url.replaceAll("start=0", "start=" + i);
                initData(urlNew);
                conRecy.setLoadMoreComplete();
            }
        });
    }
}