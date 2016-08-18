package com.example.notifications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 描述：                                                               <br>
 * 作者：        追梦                                                <br>
 * 邮箱：        1521541979@qq.com                        <br>
 * 公司：        杭州码友网络科技有限公司             <br>
 * 日期：        2016/8/17 15:56                               <br>
 */
public class TestActivity extends Activity
{

    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tvTest = (TextView) findViewById(R.id.tv_test);

        Intent i = getIntent();
        Bundle bundle = i.getBundleExtra("test");
        if (bundle != null)
        {

            String data = bundle.getString("test");
            tvTest.setText("传递过来的数据是：" + data);
        }
    }
}
