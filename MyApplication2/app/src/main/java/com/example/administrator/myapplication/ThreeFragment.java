package com.example.administrator.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.administrator.myapplication.R.id.my_list;

/**
 * Created by Administrator on 2017/8/31.
 */

public class ThreeFragment extends Fragment {

    ListView my_list;
    List<Person> list = new ArrayList<Person>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three,null);

        my_list = (ListView) view.findViewById(R.id.my_list);

        //集合填充数据
        Person list_name1 = new Person();
        list_name1.setName("我的收藏");
       list_name1.setImg("more");

        Person list_name2 = new Person();
        list_name2.setName("关于我们");
       list_name2.setImg("more");

        list.add(list_name1);
        list.add(list_name2);

        //绑定BaseAdapter
        My_list_Adapter my_list_Adapter = new My_list_Adapter(getActivity(), list);
        my_list.setAdapter(my_list_Adapter);

        setListViewHeightBasedOnChildren(my_list);

        return view;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

        listView.setLayoutParams(params);
    }
}
