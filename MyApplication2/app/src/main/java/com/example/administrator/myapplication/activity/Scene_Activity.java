package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Scene_Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    public JSONObject object;
    //JsonUtil jsonUtil;
    Handler handler = new Handler();
    final String url = "http://193.112.144.229/travel/?spot/show";
    String id;
    String r;
    String name;

    ArrayList<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();

    //轮播图
    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        final TextView textView = (TextView) findViewById(R.id.scene_detail_name);
        final TextView textView1 = (TextView) findViewById(R.id.scene_detail_desc);
        // final ImageView imageView = (ImageView) findViewById(R.id.strategy_img);
        //final MyImageView myImageView = (MyImageView) findViewById(R.id.scene_detail_img);

        Intent intent = this.getIntent();
        id = intent.getStringExtra("id");
        //Log.i("id",id);
        name = intent.getStringExtra("name");
        // new JsonUtil(url, id).Post_Data();

        //获取后台数据
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String urlPath = url;
                URL url = null;
                InputStream inputStream=null;
                try {
                    url = new URL(urlPath);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
                    jsonObject.put("name", name);
                    String param = jsonObject.toString();
                    Log.i("参数", param);
                    HttpURLConnection con = con = (HttpURLConnection) url.openConnection(); //开启连接
                    con.setConnectTimeout(5000);
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("ser-Agent", "Fiddler");
                    con.setRequestProperty("Content-Type", "application/json");
                    //写输出流，将要转的参数写入流里
                    OutputStream os = con.getOutputStream();
                    os.write(param.getBytes()); //字符串写进二进流
                    os.flush();
                    os.close();

                    inputStream = con.getInputStream();

                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder res = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        res.append(line);
                    }
                    r = res.toString();
                    Log.i("返回数据", r);
                    //final Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    //  final ImageThread img = new ImageThread(urlPath, handler, imageView);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (r != null){
                                Log.i("数据",r);
                                try {
                                    JSONObject jsonObject1 = new JSONObject(r);
                                    String resultCode = jsonObject1.getString("success");
                                    if (resultCode.equals("true")){
                                        JSONArray resultJsonArray = jsonObject1.getJSONArray("data");
                                        for (int i=0; i<resultJsonArray.length(); i++){
                                            object = resultJsonArray.getJSONObject(i);

                                            Map<String, Object> map = new HashMap<String, Object>();

                                            try {
                                                String name = object.getString("name");
                                                String id = object.getString("id");
                                                String brief = object.getString("brief");
                                                String picture = "http://193.112.144.229/travel" + object.getString("picture");
                                                map.put("name", name);
                                                map.put("id", id);
                                                map.put("brief",brief);
                                                map.put("picture", picture);
                                                lists.add(map);

                                                Log.i("返回数据",name);
                                                Log.i("返回数据",id);
                                                Log.i("返回数据",picture);

                                                textView.setText(name);
                                                textView1.setText(brief);

                                                //  Bitmap bitmap = getHttpBitmap(picture);

                                                //myImageView.setImageURL(picture);


                                            }catch (JSONException e){
                                                e.printStackTrace();
                                            }
                                        }

                                    }

                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

//                            MyListViewAdapter=new MyListViewAdapter(context,handler,students); //传递关键参数MainActivity上下文对象context，MainActivity主线程的handler对象,处理好的List<Student>对象
//                            listView.setAdapter(MyListViewAdapter);
                        }
                    });
                    //jsonJX(r);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        // 初始化布局 View视图
        initViews();

        // Model数据
        initData();

        // Controller 控制器
        initAdapter();

        // 开启轮询
        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            System.out.println("设置当前位置: " + viewPager.getCurrentItem());
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }

            ;
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager1);
        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
//      viewPager.setOffscreenPageLimit(1);// 左右各保留几个对象
        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);

        tv_desc = (TextView) findViewById(R.id.tv_desc);

    }

    /**
     * 初始化要显示的数据
     */
    private void initData() {
        // 图片资源id数组
        imageResIds = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e};

        // 文本描述
        contentDescs = new String[]{
                "山清水秀",
                "湖光山色",
                "春暖花开",
                "美不胜收",
                "诗情画意"
        };

        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);

            // 加小白点, 指示器
            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.selector_bg_point);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if (i != 0)
                layoutParams.leftMargin = 10;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new Scene_Activity.MyAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//          System.out.println("isViewFromObject: "+(view == object));
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            System.out.println("instantiateItem初始化: " + position);
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 4

//          newPosition = position % 5
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            System.out.println("destroyItem销毁: " + position);
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // 滚动时调用
    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        System.out.println("onPageSelected: " + position);
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);

//      for (int i = 0; i < ll_point_container.getChildCount(); i++) {
//          View childAt = ll_point_container.getChildAt(position);
//          childAt.setEnabled(position == i);
//      }
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);

        // 记录之前的位置
        previousSelectedPosition = newPosition;

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滚动状态变化时调用
    }


}
