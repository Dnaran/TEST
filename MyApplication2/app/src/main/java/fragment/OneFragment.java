package fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Strategy;

import java.util.ArrayList;
import java.util.List;

import activity.Strategy_Activity;
import adapter.MyStrategyAdapter;

/**
 * Created by Administrator on 2017/8/31.
 */

public class OneFragment extends Fragment{

    ListView listView;
    List<Strategy> list = new ArrayList<Strategy>();


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
        Strategy person = new Strategy();
        person.setStrategyname("青秀山壮乡风情之旅");
        person.setName("西门-广西十二世居民族雕塑群-壮锦广场-状元泉文化园-千年苏铁园-龙象塔-雨林大观-凤凰阁");
        person.setImg("milk_tea");

        Strategy person_1 = new Strategy();
        person.setStrategyname("青秀山壮乡风情之旅");
        person_1.setName("南宁吃货赞不绝口的螺蛳粉超全集合！");
        person_1.setImg("luosifen");

        Strategy person_2 = new Strategy();
        person.setStrategyname("青秀山壮乡风情之旅");
        person_2 .setName("在南宁中转，土著带你逛吃市井！");
        person_2.setImg("xiaochi");

        list.add(person);
        list.add(person_1);
        list.add(person_2);

        //绑定BaseAdapter
        MyStrategyAdapter myPersonAdapter = new MyStrategyAdapter(getActivity(), list);
        listView.setAdapter(myPersonAdapter);

        return view;
    }
}
