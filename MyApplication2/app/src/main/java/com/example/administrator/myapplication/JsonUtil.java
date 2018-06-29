package com.example.administrator.myapplication;

import android.util.Log;

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
import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */

public class JsonUtil {

    public JSONObject object;
    String urls;
    String id;

    ArrayList<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();

    //构造方法
    public JsonUtil(String urls, String id){
        this.urls = urls;
        this.id = id;
    }

    public JsonUtil(String url){
        this.urls = url;
    }


    //获取后台数据GET方法
    public void Get_Data() {
        //lists.clear();
       // listView = (ListView) view.findViewById(R.id.listView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlPath = urls;
                URL url = null;
                try {
                    url = new URL(urlPath);

                    HttpURLConnection con = con = (HttpURLConnection) url.openConnection(); //开启连接
                    con.setConnectTimeout(5000);
                    con.setDoInput(true);

                    con.setRequestMethod("GET");

                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder res = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        res.append(line);
                    }
                    String r = res.toString();
                    //json = r;
                    Log.i("返回数据", r);
                    jsonJX(r);

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //从后台获取数据，参数，POST
    public void Post_Data() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlPath = urls;
                URL url = null;
                try {
                    url = new URL(urlPath);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", id);
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
                            String brief = object.getString("brief");
                           String picture = "http://193.112.144.229/travel/" + object.getString("picture");
                            map.put("name", name);
                            map.put("id", id);
                            map.put("brief",brief);
                            map.put("picture", picture);
                            lists.add(map);
//                            scence_name = name;
//                            scence_brief = brief;
//                            scence_picture = picture;
                            Log.i("返回数据",name);
                            Log.i("返回数据",id);
                            Log.i("返回数据",picture);
                            Log.i("返回数据", brief);
//
//                            for (Map<String, Object> m : lists){
//                                for (String k : m.keySet()){
//                                    Log.i("",k + ":" + m.get(k));
//                                }
//                            }


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
