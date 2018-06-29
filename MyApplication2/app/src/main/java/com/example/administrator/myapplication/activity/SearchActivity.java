package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.FilterListener;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private EditText et_ss;
    private ListView lsv_ss;
    private List<String> list = new ArrayList<String>();
    private List<String> list_id = new ArrayList<String>();
    boolean isFilter;
    private MyAdapter adapter = null;

    public JSONObject object;
    ArrayList<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        requestData();
        setViews();// 控件初始化
        setData();// 给listView设置adapter
        setListeners();// 设置监听

    }

    /**
     * 数据初始化并设置adapter
     */
    private void setData() {
        //initData();// 初始化数据

        // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取
        adapter = new MyAdapter(list, this, new FilterListener() {
            // 回调方法获取过滤后的数据
            public void getFilterData(List<String> list) {
                // 这里可以拿到过滤后数据，所以在这里可以对搜索后的数据进行操作
                Log.e("TAG", "接口回调成功");
                Log.e("TAG", list.toString());
                setItemClick(list);
            }
        });
        lsv_ss.setAdapter(adapter);
    }

    /**
     * 给listView添加item的单击事件
     * @param filter_lists  过滤后的数据集
     */
    protected void setItemClick(final List<String> filter_lists) {
        lsv_ss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
                Toast.makeText(SearchActivity.this, filter_lists.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchActivity.this, Scene_Activity.class);
                Log.i("mm",filter_lists.get(position));
                TextView tv = (TextView) view.findViewById(R.id.textView_search);
                //tv.getText();
                intent.putExtra("name",tv.getText());
                startActivity(intent);
            }
        });
    }

    /**
     * 简单的list集合添加一些测试数据
     */
//    private void initData() {
//        list.add("看着飞舞的尘埃   掉下来");
//        list.add("没人发现它存在");
//        list.add("多自由自在");
//        list.add("可世界都爱热热闹闹");
//        list.add("容不下   我百无聊赖");
//        list.add("不应该   一个人 发呆");
//        list.add("只有我   守着安静的沙漠");
//        list.add("等待着花开");
//        list.add("只有我   看着别人的快乐");
//    }

    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
        setItemClick(list);

        /**
         * 对编辑框添加文本改变监听，搜索的具体功能在这里实现
         * 很简单，文本该变的时候进行搜索。关键方法是重写的onTextChanged（）方法。
         */
        et_ss.addTextChangedListener(new TextWatcher() {

            /**
             *
             * 编辑框内容改变的时候会执行该方法
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 如果adapter不为空的话就根据编辑框中的内容来过滤数据
                if(adapter != null){
                    adapter.getFilter().filter(s);
                }
            }

            //在文本框改变之前
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (s.length() == 0){
                    lsv_ss.setVisibility(View.GONE);
                }else {
                    lsv_ss.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    /**
     * 控件初始化
     */
    private void setViews() {
        et_ss = (EditText) findViewById(R.id.search_EditText);// EditText控件
        lsv_ss = (ListView)findViewById(R.id.search_list);// ListView控件
    }

    //请求数据
    public void requestData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlPath = "http://193.112.144.229/travel/?search/search";
                URL url = null;
                try {
                    url = new URL(urlPath);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", et_ss.getText());
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
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder res = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        res.append(line);
                    }
                    String r = res.toString();
                    Log.i("返回数据", r);

                    jsonJX(r);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jsonJX(String data){
        // Log.i("参数",data);
        if (data != null){
            try {
                JSONObject jsonObject = new JSONObject(data);
                String resultCode = jsonObject.getString("success");
                if (resultCode.equals("true")){
                    JSONArray resultJsonArray = jsonObject.getJSONArray("data");
                    for (int i=0; i<resultJsonArray.length(); i++){
                        object = resultJsonArray.getJSONObject(i);

                        Map<String, Object> map = new HashMap<String, Object>();

                        try {
                            String name = object.getString("name");
                            String id = object.getString("id");
                            //String brief = object.getString("brief");
                            //String picture = "http://193.112.144.229/travel/" + object.getString("picture");
                            map.put("name", name);
                            map.put("id", id);
                            //map.put("brief",brief);
                           // map.put("picture", picture);
                            lists.add(map);
                            list.add(name);
                            //list.add(id);
//                            list.addAll(list);
                            Log.i("数据",list.toString());
                            //Log.i("数据",list_id.toString());

//                            Log.i("返回数据",name);
//                            Log.i("返回数据",id);
                           // Log.i("返回数据",picture);
                            //Log.i("返回数据", brief);



                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
//
//                    Message message = new Message();
//                    message.what = 1;
//                    handler.sendMessage(message);
                }

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
