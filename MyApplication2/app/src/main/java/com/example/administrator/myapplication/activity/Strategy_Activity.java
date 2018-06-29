package com.example.administrator.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.administrator.myapplication.R;

import org.json.JSONObject;

import com.example.administrator.myapplication.thread.ListView_detail_Thread;

public class Strategy_Activity extends AppCompatActivity {

    public JSONObject object;
    //JsonUtil jsonUtil;
    //Handler handler = new Handler();
    final String url = "http://193.112.144.229/travel/?route/detail";
    String id;
    //String r;
    public Context context;
    ListView listView;
    Handler handler;
    com.example.administrator.myapplication.adapter.Strategy_detail_Adapter Strategy_detail_Adapter;

    //ArrayList<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy);
//
//        final TextView textView = (TextView) findViewById(R.id.strategy_name);
//        final TextView textView1 = (TextView) findViewById(R.id.strategy_detail);
//       // final ImageView imageView = (ImageView) findViewById(R.id.strategy_img);
//        final MyImageView myImageView = (MyImageView) findViewById(R.id.strategy_img);

       // ListView listView = (ListView) findViewById(R.id.strategy_detail_desc);

        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");
        Log.i("id",id);
       // new JsonUtil(url, id).Post_Data();

        context = this;
        listView = (ListView)findViewById(R.id.strategy_detail_desc);
        handler = new Handler();
        new ListView_detail_Thread(context,listView,url,handler,id).start();

    }

}
