package com.example.administrator.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class OneFragment extends Fragment{

    ListView listView;
    List<Person> list = new ArrayList<Person>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one,null);

        listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), "片名" + list.get(i).getName() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Strategy_Activity.class);
                startActivity(intent);

            }
        });


        //集合填充数据
        Person person = new Person();
        person.setName("攻略一之西大简介");
        person.setImg("pt13");

        Person person_1 = new Person();
        person_1.setName("攻略二之热门景点");
        person_1.setImg("pt11");

        Person person_2 = new Person();
        person_2 .setName("攻略三之餐馆推荐");
        person_2.setImg("pt12");

        list.add(person);
        list.add(person_1);
        list.add(person_2);

        //绑定BaseAdapter
        MyPersonAdapter myPersonAdapter = new MyPersonAdapter(getActivity(), list);
        listView.setAdapter(myPersonAdapter);

        return view;
    }
}
