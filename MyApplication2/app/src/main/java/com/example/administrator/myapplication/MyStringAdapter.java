package com.example.administrator.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 2017/8/30.
 */

public class MyStringAdapter extends BaseAdapter{

    Context context;
    List<String> list;
    LayoutInflater inflator;

    public MyStringAdapter(Context context, List<String> list){
        this.context = context;
        this.list = list;
        inflator = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        //加载内容的长度
        return list.size();
    }

    @Override
    public String getItem(int i) {
        //获取当前第几个资源
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        //获取目录下标
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view = inflator.inflate(R.layout.list_view_string,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView)view.findViewById(R.id.textView);

            viewHolder.textView.setText(list.get(i));
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        return view;
    }

    class ViewHolder{
        TextView textView;
    }
}
