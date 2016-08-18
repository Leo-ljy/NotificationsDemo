package com.example.notifications;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * ������                                                               <br>
 * ���ߣ�        ׷��                                                <br>
 * ���䣺        1521541979@qq.com                        <br>
 * ��˾��        ������������Ƽ����޹�˾             <br>
 * ���ڣ�        2016/8/17 15:56                               <br>
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
            tvTest.setText("���ݹ����������ǣ�" + data);
        }
    }
}
