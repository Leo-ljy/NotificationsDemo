package com.example.notifications.pool;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.notifications.R;
import com.example.notifications.TestActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 描述：                                                               <br>
 * 作者：        追梦                                                <br>
 * 邮箱：        1521541979@qq.com                        <br>
 * 公司：        杭州码友网络科技有限公司             <br>
 * 日期：        2016/8/17 16:38                               <br>
 */
public class PollingService extends Service
{
    public static final String ACTION = "com.example.notifications.pool";
    //循环5次发次通知
    public int time = 5;
    int count = 0;
    private Notification mNotification;
    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;

    /**
     * 间隔时间
     */
    public static int INTERVAL_SECS = 3;
    /**
     * 定时任务工具类
     */
    public static Timer timer = new Timer();

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        initNotifiManager();
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        Log.d("", "开始轮询");
        startLoop();
    }

    //初始化通知栏配置
    private void initNotifiManager()
    {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //设置图片,通知标题,发送时间,提示方式等属性
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("轮询测试")
                .setContentText("测试内容")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" +
                                    R.raw.biaobiao))  //设置自定义的提示音
                .setTicker("轮询通知来啦")//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
                .setSmallIcon(R.drawable.ic_launcher);
    }

    private void showNotification()
    {
        mBuilder.setWhen(System.currentTimeMillis());
        Intent i = new Intent(this, TestActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        mBuilder.setContentIntent(pendingIntent);
        mManager.notify(0, mBuilder.build());
    }


    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags)
    {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    /**
     * 启动轮询拉去消息
     */
    private void startLoop()
    {
        if (timer == null)
        {
            timer = new Timer();
        }
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println("Polling...");
                count++;
                //当计数能被5整除时弹出通知
                if (count % time == 0)
                {
                    showNotification();
                    System.out.println("New message!");
                }
            }
        }, 0, INTERVAL_SECS * 1000);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        timer.cancel();
        timer = new Timer();
        Log.d("", "Service:onDestroy");
    }


}
