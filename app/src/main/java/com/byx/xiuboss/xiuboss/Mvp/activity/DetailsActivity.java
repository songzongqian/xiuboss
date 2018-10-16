package com.byx.xiuboss.xiuboss.Mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.byx.xiuboss.xiuboss.Bean.XiangQingBean;
import com.byx.xiuboss.xiuboss.Mvp.adapter.DetailsAdapter;
import com.byx.xiuboss.xiuboss.Mvp.net.OkHttpUtils;
import com.byx.xiuboss.xiuboss.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class DetailsActivity extends BaseActivity implements View.OnClickListener{

    private ListView myListView;
    private ImageView image_return;
    private List<XiangQingBean.DataBean> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        myListView = findViewById(R.id.MyListView);
        image_return = findViewById(R.id.image_return);
        image_return.setOnClickListener(this);
        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Map map = (Map) bundle.get("map");
        OkHttpUtils.getInstance().postDataAsynToNet("https://www.ourdaidai.com/CI/index.php/Store/todayMoney", map, new OkHttpUtils.MyNetCall() {

            @Override
            public void success(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("----DetailsActivity---",string);
                Gson gson=new Gson();
                XiangQingBean xiangQingBean = gson.fromJson(string, XiangQingBean.class);
               data = xiangQingBean.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DetailsAdapter adapter=new DetailsAdapter(data,DetailsActivity.this);
                        myListView.setAdapter(adapter);
                    }
                });

            }

            @Override
            public void failed(Call call, IOException e) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_return:
                finish();
                break;
        }
    }
}
