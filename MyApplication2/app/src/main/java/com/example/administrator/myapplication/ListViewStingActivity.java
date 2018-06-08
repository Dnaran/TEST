package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListViewStingActivity extends AppCompatActivity {

    ListView listView;
    List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_sting);

        listView = (ListView)findViewById(R.id.listView);

        //页面写死数据
        list.add("Dimebag");
        list.add("Zakk Wylde");
        list.add("Slash");
        list.add("Eddie Van Halen");
        list.add("Steve Vai");

        //绑定适配器
        MyStringAdapter myAdapter = new MyStringAdapter(ListViewStingActivity.this,list);
        listView.setAdapter(myAdapter);
    }
}
