package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button button_login;
    Button button_register;

    EditText editText_name;
    EditText editText_password;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_login = (Button)findViewById(R.id.button_login);
        button_register = (Button)findViewById(R.id.button_register);

        editText_name = (EditText)findViewById(R.id.editText_name);
        editText_password = (EditText)findViewById(R.id.editText_password);

        checkBox =(CheckBox)findViewById(R.id.checkBox);

        button_login.setOnClickListener(this);
        button_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_login:
                String name = editText_name.getText().toString();
                String password = editText_password.getText().toString();

                //判断checkbox是否选中
                if (checkBox.isChecked()) {
                    if(name.equals("aaa") && password.equals("111")){
//Activity跳转
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
//
////关闭自身
//                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this,"账号密码错误",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,"请点击接受协议",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_register:
                //Toast.makeText(LoginActivity.this,"欢迎注册",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Register_Activity.class);
                startActivity(intent);

                finish();
                break;
        }
    }

}

