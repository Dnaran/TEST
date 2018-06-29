package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.myapplication.R;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        //设置等待时间,单位为毫秒
        Integer time = 2000;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Activity页面跳转
                Intent intent = new Intent(LogoActivity.this,SlideActivity.class);
                startActivity(intent);
                //关闭自身
                finish();
            }
        }, time);

    }
}

