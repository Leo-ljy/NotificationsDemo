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
 * ������                                                               <br>
 * ���ߣ�        ׷��                                                <br>
 * ���䣺        1521541979@qq.com                        <br>
 * ��˾��        ������������Ƽ����޹�˾             <br>
 * ���ڣ�        2016/8/17 16:38                               <br>
 */
public class PollingService extends Service
{
    public static final String ACTION = "com.example.notifications.pool";
    //ѭ��5�η���֪ͨ
    public int time = 5;
    int count = 0;
    private Notification mNotification;
    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;

    /**
     * ���ʱ��
     */
    public static int INTERVAL_SECS = 3;
    /**
     * ��ʱ���񹤾���
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
        Log.d("", "��ʼ��ѯ");
        startLoop();
    }

    //��ʼ��֪ͨ������
    private void initNotifiManager()
    {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //����ͼƬ,֪ͨ����,����ʱ��,��ʾ��ʽ������
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("��ѯ����")
                .setContentText("��������")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" +
                                    R.raw.biaobiao))  //�����Զ������ʾ��
                .setTicker("��ѯ֪ͨ����")//֪ͨ�״γ�����֪ͨ��������������Ч����
                .setWhen(System.currentTimeMillis())//֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
                .setPriority(Notification.PRIORITY_DEFAULT)//���ø�֪ͨ���ȼ�
                .setAutoCancel(true)//���������־���û��������Ϳ�����֪ͨ���Զ�ȡ��
                .setOngoing(false)//ture��������Ϊһ�����ڽ��е�֪ͨ������ͨ����������ʾһ����̨����,�û���������(�粥������)����ĳ�ַ�ʽ���ڵȴ�,���ռ���豸(��һ���ļ�����,ͬ������,������������)
                .setDefaults(Notification.DEFAULT_VIBRATE)//��֪ͨ������������ƺ���Ч������򵥡���һ�µķ�ʽ��ʹ�õ�ǰ���û�Ĭ�����ã�ʹ��defaults���ԣ�������ϣ�
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
     * @��ȡĬ�ϵ�pendingIntent,Ϊ�˷�ֹ2.3�����°汾����
     * @flags����: �ڶ�����פ:Notification.FLAG_ONGOING_EVENT
     * ���ȥ���� Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags)
    {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    /**
     * ������ѯ��ȥ��Ϣ
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
                //�������ܱ�5����ʱ����֪ͨ
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
