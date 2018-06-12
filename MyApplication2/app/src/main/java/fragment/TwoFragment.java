package fragment;


import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.Work;

import java.util.ArrayList;
import java.util.List;

import activity.Scene_Activity;
import adapter.MyGridViewAdapter;

/**
 * Created by Administrator on 2017/8/31.
 */

public class TwoFragment extends Fragment {

    GridView gridView;
    List<Work> list = new ArrayList<Work>();

    //图片封装在一个数组内
    int[] image = {R.mipmap.pt4,R.mipmap.pt5,R.mipmap.pt6,R.mipmap.pt7};
    //String封装在一个数组内
    String[] titles = {"海边日落","清晨","鲜花","江南水乡"};

    private ViewPager viewPager;
    private LinearLayout point_group;
    private TextView image_desc;
    // 图片资源id
    private final int[] images = {R.mipmap.pt1, R.mipmap.pt2,R.mipmap.pt3,
            R.mipmap.pt4, R.mipmap.pt5};
    // 图片标题集合
    private final String[] imageDescriptions = {"第一张图片",
            "第二张图片", "第三张图片", "第四张图片", "第五张图片"};

    private ArrayList<ImageView> imageList;
    // 上一个页面的位置
    protected int lastPosition = 0;

    // 判断是否自动滚动viewPager
    private boolean isRunning = true;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 执行滑动到下一个页面
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (isRunning) {
                // 在发一个handler延时
                handler.sendEmptyMessageDelayed(0, 5000);
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two,null);

        gridView = (GridView)view.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getActivity(), "片名" + list.get(i).getName() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), Scene_Activity.class);
                startActivity(intent);

            }
        });

        for (int i = 0; i<image.length; i++){
            Work work = new Work();
            work.setImg(image[i]);
            work.setTitle(titles[i]);
            list.add(work);
        }

        MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter(getActivity(),list);
        gridView.setAdapter(myGridViewAdapter);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        point_group = (LinearLayout) view.findViewById(R.id.point_group);
        image_desc = (TextView) view.findViewById(R.id.image_desc);
        image_desc.setText(imageDescriptions[0]);

        // 初始化图片资源
        imageList = new ArrayList<ImageView>();
        for (int i : images) {
            // 初始化图片资源
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(i);
            imageList.add(imageView);

            // 添加指示小点
            ImageView point = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100,15);
            params.rightMargin = 20;
            params.bottomMargin = 10;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point_bg);
            if (i == R.mipmap.p1) {
                //默认聚焦在第一张
                point.setBackgroundResource(R.drawable.point_focured);
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }

            point_group.addView(point);
        }

        viewPager.setAdapter(new MyPageAdapter());
        // 设置当前viewPager的位置
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2
                - (Integer.MAX_VALUE / 2 % imageList.size()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // 页面切换后调用， position是新的页面位置

                // 实现无限制循环播放
                position %= imageList.size();

                image_desc.setText(imageDescriptions[position]);

                // 把当前点设置为true,将上一个点设为false；并设置point_group图标
                point_group.getChildAt(position).setEnabled(true);
                point_group.getChildAt(position).setBackgroundResource(R.drawable.point_focured);//设置聚焦时的图标样式
                point_group.getChildAt(lastPosition).setEnabled(false);
                point_group.getChildAt(lastPosition).setBackgroundResource(R.drawable.point_bg);//上一张恢复原有图标
                lastPosition = position;

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // 页面正在滑动时间回调

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // 当pageView 状态发生改变的时候，回调

            }
        });

        /**
         * 自动循环： 1.定时器：Timer 2.开子线程：while true循环 3.ClockManger
         * 4.用Handler发送延时信息，实现循环，最简单最方便
         *
         */

        handler.sendEmptyMessageDelayed(0, 3000);

        return view;
    }


    @Override
    public void onDestroy() {
// 停止滚动
        isRunning = false;
        super.onDestroy();
    }

    private class MyPageAdapter extends PagerAdapter {
        // 需要实现以下四个方法

        @Override
        public int getCount() {
            // 获得页面的总数
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            // 判断view和Object对应是否有关联关系
            if (view == object) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 获得相应位置上的view； container view的容器，其实就是viewpage自身,
            // position: viewpager上的位置
            // 给container添加内容
            container.addView(imageList.get(position % imageList.size()));

            return imageList.get(position % imageList.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 销毁对应位置上的Object
            // super.destroyItem(container, position, object);
            container.removeView((View) object);
            object = null;
        }

    }

}
