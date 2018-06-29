package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.Hot_scene;
import com.example.administrator.myapplication.R;

import java.util.List;

import com.example.administrator.myapplication.thread.ImageThread;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MyGridViewAdapter extends BaseAdapter {

    List<Hot_scene> scenes;
    Context context;
    LayoutInflater inflater;
    Handler handler;

    public MyGridViewAdapter(Context context, Handler handler, List<Hot_scene> scenes) {
        this.handler=handler;
        this.context=context;
        this.scenes=scenes;
        inflater= LayoutInflater.from(context);//从MainActivity中上下文对象中获取LayoutInflater；所以说这个context,和handler对象很重要，贯穿整项目
    }

    @Override
    public int getCount() {
        return scenes.size();
    }

    @Override
    public Object getItem(int position) {
        return scenes.get(position);
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
            convertView=inflater.inflate(R.layout.grid_view,null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);//设置tag
        }else {
            holder= (MyGridViewAdapter.ViewHolder) convertView.getTag(); //获取tag
        }

        holder.id.setText(scenes.get(position).id);
        holder.name.setText(scenes.get(position).name);
        System.out.println(scenes.get(position).name);
        new ImageThread(scenes.get(position).url, handler,holder.image).start();//开启新线程下载图片并在新线程中更新UI，所以要传递handler对象
        return convertView;
    }

    //用于暂时保存视图对象
    class ViewHolder{
        public TextView name;
        public TextView id;
        public ImageView image;

        public ViewHolder(View view){
            name= (TextView) view.findViewById(R.id.title_scene);
            image= (ImageView) view.findViewById(R.id.img_scene);
            id = (TextView) view.findViewById(R.id.id_scene);
        }
    }
}
