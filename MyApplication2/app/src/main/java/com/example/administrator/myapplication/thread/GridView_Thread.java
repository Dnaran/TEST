package com.example.administrator.myapplication.thread;

import android.content.Context;
import android.os.Handler;
import android.widget.GridView;

import com.example.administrator.myapplication.Hot_scene;

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

import com.example.administrator.myapplication.adapter.MyGridViewAdapter;

/**
 * Created by Administrator on 2018/6/10.
 */

public class GridView_Thread extends Thread {

    Context context;
    GridView gridView;
    String url;
    Handler handler;//关键参数 整个小项目中的核心之一，会在JsonThread和JsonAdapter，ImageThread中传递，用于更新UI界面
    List<Hot_scene> hot_scenes;
    MyGridViewAdapter myGridViewAdapter;

    public GridView_Thread(Context context, GridView gridView, String url, Handler handler ) {
        this.context=context;
        this.gridView=gridView;
        this.url=url;
        this.handler=handler;
    }
    //从String中解析所需数据，如name,url，将他们装入Student中，再将Student逐条加入List<Student>中
    private List<Hot_scene> getScene(String data){
        List<Hot_scene> scenes=new ArrayList<Hot_scene>();
        try {
            JSONObject object=new JSONObject(data);
            String resultCode = object.getString("success");
            if(resultCode.equals("true")){
                JSONArray jsonArray=object.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject sceneObject= (JSONObject) jsonArray.get(i);
                    Hot_scene scene=new Hot_scene();
                    scene.id = sceneObject.getString("id");
                    System.out.println(scene.id);
                    scene.name=sceneObject.getString("name");
                    System.out.println(scene.name);
                    scene.url= "http://193.112.144.229/travel" + sceneObject.getString("picture");
                    System.out.println(scene.url);
                    scenes.add(scene);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  scenes;
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
            hot_scenes=getScene(result.toString());//调用解析方法
            inputStream.close();
            bufferedReader.close();

            handler.post(new Runnable() {
                @Override
                public void run() {

                    myGridViewAdapter=new MyGridViewAdapter(context,handler,hot_scenes); //传递关键参数MainActivity上下文对象context，MainActivity主线程的handler对象,处理好的List<Student>对象
                    gridView.setAdapter(myGridViewAdapter);
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
