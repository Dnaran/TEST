package com.example.administrator.myapplication.thread;

/**
 * Created by Administrator on 2018/6/10.
 */
import android.content.Context;
import android.os.Handler;
import android.widget.ListView;

import com.example.administrator.myapplication.Strategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.administrator.myapplication.adapter.MyListViewAdapter;

/**
 * Created by CAPTON on 2016/11/25.
 */
//访问目标网址，得到json数据，保存List<Student>数据，等待传入JsonAdapter
public class ListView_Thread extends Thread {
    Context context;
    ListView listView;
    String url;
    Handler handler;//关键参数 整个小项目中的核心之一，会在JsonThread和JsonAdapter，ImageThread中传递，用于更新UI界面
    List<Strategy> strategies;
    MyListViewAdapter MyListViewAdapter;

    public ListView_Thread(Context context, ListView listView, String url, Handler handler ) {
        this.context=context;
        this.listView=listView;
        this.url=url;
        this.handler=handler;
    }
    //从String中解析所需数据，如name,url，将他们装入Student中，再将Student逐条加入List<Student>中
    private List<Strategy> getStrategy(String data){
        List<Strategy> strategies1=new ArrayList<Strategy>();
        try {
            JSONObject object=new JSONObject(data);
            String resultCode = object.getString("success");
            if(resultCode.equals("true")){
                JSONArray jsonArray=object.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject strategyObject= (JSONObject) jsonArray.get(i);
                    Strategy strategy=new Strategy();
                    strategy.id = strategyObject.getString("id");
                    System.out.println(strategy.id);
                    strategy.name=strategyObject.getString("route");
                    System.out.println(strategy.name);
                    strategy.url= "http://193.112.144.229/travel" + strategyObject.getString("picture");
                    System.out.println(strategy.url);
                    strategies1.add(strategy);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  strategies1;
    }

    @Override
    public void run() {

        //从网络中获取数据，转换为String类型
        StringBuffer result=new StringBuffer();
        try {
            URL Url=new URL(url);
            HttpURLConnection connection= (HttpURLConnection) Url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            InputStream inputStream=connection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                result.append(line);
            }
            System.out.println(result);
            strategies=getStrategy(result.toString());//调用解析方法
            inputStream.close();
            bufferedReader.close();

            handler.post(new Runnable() {
                @Override
                public void run() {

                    MyListViewAdapter =new MyListViewAdapter(context,handler,strategies); //传递关键参数MainActivity上下文对象context，MainActivity主线程的handler对象,处理好的List<Student>对象
                    listView.setAdapter(MyListViewAdapter);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
