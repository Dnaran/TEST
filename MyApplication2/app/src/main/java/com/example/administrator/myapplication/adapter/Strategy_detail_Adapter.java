package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Strategy_detail;

import java.util.List;

import com.example.administrator.myapplication.thread.ImageThread;

/**
 * Created by Administrator on 2018/6/12.
 */

public class Strategy_detail_Adapter extends BaseAdapter {

    List<Strategy_detail> strategies;
    Context context;
    LayoutInflater inflater;
    Handler handler;

    public Strategy_detail_Adapter(Context context, Handler handler, List<Strategy_detail> strategies) {
        this.handler=handler;
        this.context=context;
        this.strategies=strategies;
        inflater = LayoutInflater.from(context);//从MainActivity中上下文对象中获取LayoutInflater；所以说这个context,和handler对象很重要，贯穿整项目
    }

    @Override
    public int getCount() {
        return strategies.size();
    }

    @Override
    public Object getItem(int position) {
        return strategies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //重写getView方法，即设置ListView每一项的视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;

        if(convertView==null){
            convertView=inflater.inflate(R.layout.list_strategy_detail,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);//设置tag
        }else {
            holder= (ViewHolder) convertView.getTag(); //获取tag
        }

        holder.brief.setText(strategies.get(position).brief);
        //System.out.println(strategies.get(position).brief);
        holder.name.setText(strategies.get(position).name);
        // System.out.println(students.get(position).name);
        new ImageThread(strategies.get(position).url, handler,holder.image).start();//开启新线程下载图片并在新线程中更新UI，所以要传递handler对象
        return convertView;
    }

    //用于暂时保存视图对象
    class ViewHolder{
        public TextView name;
        public TextView brief;
        public ImageView image;

        public ViewHolder(View view){
            name= (TextView) view.findViewById(R.id.title_strategy);
            brief= (TextView) view.findViewById(R.id.detail_strategy);
            image= (ImageView) view.findViewById(R.id.image_strategy);
        }
    }

}
