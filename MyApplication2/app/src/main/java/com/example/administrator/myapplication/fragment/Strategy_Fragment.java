package com.example.administrator.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import com.example.administrator.myapplication.activity.Strategy_Activity;
import com.example.administrator.myapplication.adapter.MyListViewAdapter;
import com.example.administrator.myapplication.thread.ListView_Thread;

/**
 * Created by Administrator on 2017/8/31.
 */

public class Strategy_Fragment extends Fragment{

    //ListView listView;
//    List<Person> list = new ArrayList<Person>();
    public JSONObject object;

    ArrayList<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();

    public Context context;
    ListView listView;
    Handler handler;
    MyListViewAdapter MyListViewAdapter;
    String url = "http://193.112.144.229/travel/?route/show";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_strategy,null);

        context = getActivity();
        listView = (ListView) view.findViewById(R.id.listView);
        handler = new Handler();
        new ListView_Thread(context,listView,url,handler).start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), "片名" + list.get(i).getName() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Strategy_Activity.class);

                TextView tv = (TextView) view.findViewById(R.id.list_id);
                //tv.getText();
                intent.putExtra("id",tv.getText());
                startActivity(intent);
            }
        });

       return view;
    }

}
