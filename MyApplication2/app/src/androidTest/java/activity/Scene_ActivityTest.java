package activity;

import org.junit.Test;

/**
 * Created by 黄仙仙 on 2018/6/25.
 */
public class Scene_ActivityTest {
    @Test
    public void onCreate() throws Exception {
        System.out.println("开始执行...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);//这里模拟耗时操作或网络请求时间
                }catch (InterruptedException e ){
                    e.printStackTrace();
                }
                System.out.println("网络请求执行到这里了...");
            }
        }).start();
        System.out.println("开始结束..");

    }

    @Test
    public void onDestroy() throws Exception {

    }

    @Test
    public void onPageScrolled() throws Exception {

    }

    @Test
    public void onPageSelected() throws Exception {

    }

    @Test
    public void onPageScrollStateChanged() throws Exception {

    }

}