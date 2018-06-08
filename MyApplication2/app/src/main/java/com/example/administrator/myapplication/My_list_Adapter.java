package com.example.administrator.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/6/4.
 */

public class My_list_Adapter extends BaseAdapter{
    Context context;
    List<Person> list;
    LayoutInflater inflater;

    public My_list_Adapter(Context context,List<Person> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        My_list_Adapter.ViewHolder viewHolder;
        if(view == null){
            view = inflater.inflate(R.layout.list_view_mine,null);
            viewHolder = new My_list_Adapter.ViewHolder();
            viewHolder.name = (TextView)view.findViewById(R.id.list_name_mine);
            viewHolder.img = (ImageView)view.findViewById(R.id.image_mine);
            view.setTag(viewHolder);
        }else{
            viewHolder = (My_list_Adapter.ViewHolder)view.getTag();
        }

        viewHolder.name.setText(list.get(i).getName());
        viewHolder.img.setImageResource(context.getResources().getIdentifier(list.get(i).getImg(),"mipmap",context.getPackageName()));
        return view;
    }

    class  ViewHolder{
        TextView name;
        ImageView img;
    }
}
