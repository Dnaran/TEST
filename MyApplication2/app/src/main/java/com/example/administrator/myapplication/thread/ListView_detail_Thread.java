package com.example.administrator.myapplication.thread;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import com.example.administrator.myapplication.Strategy_detail;

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
import java.util.List;

import com.example.administrator.myapplication.adapter.Strategy_detail_Adapter;

/**
 * Created by Administrator on 2018/6/12.
 */

public class ListView_detail_Thread extends Thread {

    Context context;
    ListView listView;
    String url;
    Handler handler;//关键参数 整个小项目中的核心之一，会在JsonThread和JsonAdapter，ImageThread中传递，用于更新UI界面
    List<Strategy_detail> strategies;
    Strategy_detail_Adapter Strategy_detail_Adapter;

    String id;

    public ListView_detail_Thread(Context context, ListView listView, String url, Handler handler, String id ) {
        this.context=context;
        this.listView=listView;
        this.url=url;
        this.handler=handler;
        this.id = id;
    }
    //从String中解析所需数据，如name,url，将他们装入Student中，再将Student逐条加入List<Student>中
    private List<Strategy_detail> getStrategy(String data){
        List<Strategy_detail> strategies1=new ArrayList<Strategy_detail>();
        try {
            JSONObject object=new JSONObject(data);
            String resultCode = object.getString("success");
            if(resultCode.equals("true")){
                JSONArray jsonArray=object.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject strategyObject= (JSONObject) jsonArray.get(i);
                    Strategy_detail strategy=new Strategy_detail();
                    strategy.name=strategyObject.getString("name");
                    System.out.println(strategy.name);
                    strategy.brief=strategyObject.getString("brief");
                    System.out.println(strategy.brief);
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
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            String param = jsonObject.toString();
            Log.i("参数", param);
            HttpURLConnection connection= (HttpURLConnection) Url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("ser-Agent", "Fiddler");
            connection.setRequestProperty("Content-Type", "application/json");

            //写输出流，将要转的参数写入流里
            OutputStream os = connection.getOutputStream();
            os.write(param.getBytes()); //字符串写进二进流
            os.flush();
            os.close();
            InputStream inputStream=connection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                result.append(line);
            }
            System.out.println(result);
            strategies=getStrategy(result.toString());//调用解析方法
            Log.i("树",strategies.toString());
            inputStream.close();
            bufferedReader.close();

            handler.post(new Runnable() {
                @Override
                public void run() {

                    Strategy_detail_Adapter = new Strategy_detail_Adapter(context,handler,strategies); //传递关键参数MainActivity上下文对象context，MainActivity主线程的handler对象,处理好的List<Student>对象

                    listView.setAdapter(Strategy_detail_Adapter);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
