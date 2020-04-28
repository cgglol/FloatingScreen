package com.cgg.floatingscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    RelativeLayout layout_floatingscreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //加载 飘屏 布局
        layout_floatingscreen = findViewById(R.id.rl_floatingscreen);

        //初始化
        new FloatingScreenUtils().getInstance().initBulletChat(layout_floatingscreen);
    }

    public void add(View view) {
        FloatingScreenBean.TextBean textBean1 = initFloatingScreenBean();
        new FloatingScreenUtils().getInstance().add(textBean1);
    }

    public void show(View view) {
        new FloatingScreenUtils().getInstance().showBulletChat();
    }

    public void hide(View view) {
        new FloatingScreenUtils().getInstance().hideBulletChat();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        new FloatingScreenUtils().getInstance().destroy(false);
    }

    //模拟数据
    private FloatingScreenBean.TextBean initFloatingScreenBean(){
        FloatingScreenBean.TextBean textBean = new FloatingScreenBean.TextBean();
        textBean.setType("1");
        textBean.setName("礼物飘屏");
        textBean.setImg("模拟图片");
        textBean.setTitle("<font color=\"#73FFEA\">小爱</font> 赠送 <font color=\"#73FFEA\">热爱每一天</font>  飞吻");
        textBean.setGift_img("模拟图片");
        textBean.setGift_name("飞吻");
        textBean.setGift_num("1");
        textBean.setIn_time("2.5");
        textBean.setOut_time("0.5");
        textBean.setShow_time("1");
        textBean.setIcon_img("模拟图片");
        return textBean;
    }
}
