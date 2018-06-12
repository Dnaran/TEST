package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Strategy;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class MyStrategyAdapter extends BaseAdapter{

    Context context;
    List<Strategy> list;
    LayoutInflater inflater;

    public MyStrategyAdapter(Context context, List<Strategy> list){
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

        ViewHolder viewHolder;
        if(view == null){
            view = inflater.inflate(R.layout.list_view_strategy,null);
            viewHolder = new ViewHolder();
            viewHolder.strategyname=(TextView)view.findViewById(R.id.strategy_name);
            viewHolder.name = (TextView)view.findViewById(R.id.list_name);
            viewHolder.img = (ImageView)view.findViewById(R.id.img);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.strategyname.setText(list.get(i).getStrategyname());
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.img.setImageResource(context.getResources().getIdentifier(list.get(i).getImg(),"mipmap",context.getPackageName()));
        return view;
    }

    class  ViewHolder{
        TextView strategyname;
        TextView name;
        ImageView img;
    }
}
