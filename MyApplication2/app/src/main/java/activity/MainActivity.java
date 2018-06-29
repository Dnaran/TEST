package activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import fragment.OneFragment;
import fragment.TwoFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = MainActivity.class.getSimpleName();
    private long firstTime = 0;

    View view;
    ImageView mainLeftImageView;
    ImageView mainRightImageView;
    TextView titleTextView;

    FrameLayout frameLayout;

    android.support.v4.app.Fragment oneFragment;
    android.support.v4.app.Fragment twoFragment;
    android.support.v4.app.Fragment threeFragment;

    android.support.v4.app.FragmentManager fragmentManager;

    RelativeLayout oneRelativeLayout;
    RelativeLayout twoRelativeLayout;
    RelativeLayout threeRelativeLayout;

    int white = 0xffffff;
    int black = 0xff000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //   Log.i(TAG, "hh");
        //  System.out.println("错误");

        view = (View)findViewById(R.id.activity_title);
        mainLeftImageView = (ImageView)view.findViewById(R.id.mainLeftImageView);
        mainRightImageView = (ImageView)view.findViewById(R.id.mainRightImageView);

        mainLeftImageView.setImageResource(R.drawable.position);
        mainRightImageView.setImageResource(R.drawable.ar_camera);

//        titleTextView.setText(this.getString(R.string.main_title));

        oneRelativeLayout = (RelativeLayout)findViewById(R.id.oneRelativeLayout);
        twoRelativeLayout = (RelativeLayout)findViewById(R.id.twoRelativeLayout);

        oneRelativeLayout.setOnClickListener((View.OnClickListener) this);
        twoRelativeLayout.setOnClickListener((View.OnClickListener) this);

        mainLeftImageView.setOnClickListener(this);
        mainRightImageView.setOnClickListener(this);

        fragmentManager = getSupportFragmentManager();
        //默认加载第一个Fragment
        setChioceItem(0);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.oneRelativeLayout:
                setChioceItem(0);
                break;
            case R.id.twoRelativeLayout:
                setChioceItem(1);
                break;

            case R.id.mainLeftImageView:
                //调用打电话功能
                //制作Dialog弹框
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //赋值标题
                builder.setTitle("提示")
                        .setIcon(R.mipmap.tel)
                        .setMessage("确定拨打电话吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent();
                                //设置Intent对象动作
                                intent.setAction(Intent.ACTION_CALL);
                                //设置拨打电话号码
                                intent.setData(Uri.parse("tel:13800138000"));
                                startActivity(intent);
                            }
                        })
                        //取消按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                //显示弹框
                builder.show();
                break;
            case R.id.mainRightImageView:
//                Intent intent = new Intent();
//                //设置Intent对象
//                intent.setAction(Intent.ACTION_VIEW);
//                //设置手机自带浏览器打开网址
//                intent.setData(Uri.parse("http://www.tmall.com"));
//                startActivity(intent);
                break;
        }
    }

    public void setChioceItem(int index) {
        //开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //清空重置选项
        clearChioce();
        //隐藏所有Fragment
        hideFragments(fragmentTransaction);

        switch (index) {
            case 0:
                oneRelativeLayout.setBackgroundColor(black);

                if (twoFragment == null) {
                    twoFragment = new TwoFragment();
                    fragmentTransaction.add(R.id.frameLayout, twoFragment);
                } else {
                    fragmentTransaction.show(twoFragment);
                }
                break;
            case 1:
                twoRelativeLayout.setBackgroundColor(black);

                if (oneFragment == null) {
                    oneFragment = new OneFragment();
                    fragmentTransaction.add(R.id.frameLayout, oneFragment);
                } else {
                    fragmentTransaction.show(oneFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 清空重置选项
     */
    public void clearChioce() {
        oneRelativeLayout.setBackgroundColor(white);
        twoRelativeLayout.setBackgroundColor(white);
    }


    /**
     * 隐藏所有Fragment
     */
    public void hideFragments(android.support.v4.app.FragmentTransaction fragmentTransaction) {
        if (oneFragment != null) {
            fragmentTransaction.hide(oneFragment);
        }
        if (twoFragment != null) {
            fragmentTransaction.hide(twoFragment);
        }
    }
    //获取回退事件
    public  boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            //使用计时器实现双击退出功能
            if(System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(MainActivity.this,"双击确定退出",Toast.LENGTH_SHORT).show();
//重新赋值初始时间
                firstTime = System.currentTimeMillis();
            }else{
//Activity关闭自身
                finish();
//彻底退出
                System.exit(0);
            }
        }
        return false;
    }

}