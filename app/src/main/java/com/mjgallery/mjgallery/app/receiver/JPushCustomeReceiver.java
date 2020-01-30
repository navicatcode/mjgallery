package com.mjgallery.mjgallery.app.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.mvp.ui.activity.MainActivity;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;
import com.mjgallery.mjgallery.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 极光推送的广播接收
 */
public class JPushCustomeReceiver extends JPushMessageReceiver {

    /**
     * 设置极光消息推送
     *
     * @param context
     * @param userId  用户id
     */
    public static void setJPushMessTS(Context context, String userId) {
        //标签和别名
        setTagAndAlias(context, userId);
        //声音和振动
        setSoundAndVibrate(context, false, false);
    }

    /**
     * 设置用户在极光的标签与别名
     */
    public static void setTagAndAlias(Context context, String userId) {

        //设置别名（新的调用会覆盖之前的设置）
        JPushInterface.setAlias(context, 0, userId);

        //设置标签（同上）
        Set<String> tags = new HashSet<String>();
        tags.add(userId);//设置tag
        JPushInterface.setTags(context, 0, tags);
    }

    /**
     * 设置收到消息时的铃声及震动
     *
     * @param context
     * @param isOpenSound   true带铃声，false不带铃声
     * @param isOpenVibrate true震动，false震动
     */
    public static void setSoundAndVibrate(Context context, boolean isOpenSound, boolean isOpenVibrate) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);

        if (isOpenVibrate && !isOpenSound) {//只有振动
            builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
        } else if (isOpenSound && !isOpenVibrate) {//只有声音
            builder.notificationDefaults = Notification.DEFAULT_SOUND;
        } else if (isOpenSound && isOpenVibrate) {//两个都有
            builder.notificationDefaults = Notification.DEFAULT_SOUND
                    | Notification.DEFAULT_VIBRATE
                    | Notification.DEFAULT_LIGHTS;
        } else {//两个都没有，只有呼吸灯
            builder.notificationDefaults = Notification.DEFAULT_LIGHTS;
        }
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }

    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e("ddd", "点击了onTagOperatorResult");
        super.onTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCheckTagOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e("ddd", "点击了onCheckTagOperatorResult");
        super.onCheckTagOperatorResult(context, jPushMessage);
    }

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e("ddd", "点击了onAliasOperatorResult");
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        Log.e("ddd", "点击了onMobileNumberOperatorResult");

        super.onMobileNumberOperatorResult(context, jPushMessage);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e("ddd", "点击了onCommandResult");
        super.onCommandResult(context, cmdMessage);
    }

    @Override
    public void onConnected(Context context, boolean b) {
        Log.e("ddd", "点击了onMessage");
        super.onConnected(context, b);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage notificationMessage) {
        Log.e("ddd", "点击了onNotifyMessageDismiss");
        super.onNotifyMessageDismiss(context, notificationMessage);
    }

    @Override
    public void onRegister(Context context, String s) {
        Log.e("ddd", "点击了onRegister");
        super.onRegister(context, s);
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Log.e("ddd", "点击了CloneNotSupportedException");
        return super.clone();
    }

    /**
     * 接收到普通消息的推送
     * @param context
     * @param notificationMessage
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        Log.e("ddd", "点击了onNotifyMessageArrived");
        super.onNotifyMessageArrived(context, notificationMessage);
    }

    /**
     * 接收自定义消息的推送
     * @param context
     * @param customMessage
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e("ddd", "点击了onMessage");
        super.onMessage(context, customMessage);
        /*String channelID = "1";
        String channelName = "channel_name";

        // 点击通知消息要跳转的Activity
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        //适配安卓8.0的消息渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context, channelID);
        long[] vibratePattern = { 0, 100, 200, 300 };
        notification.setAutoCancel(true)
                .setContentText(customMessage.message)
                .setContentTitle("新消息:")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setSound(null)//把音乐关掉
                .setOnlyAlertOnce(true)
                .setVibrate(vibratePattern)
                .setAutoCancel(true);

        playMusic(context);//播放自己准备的音乐

        notificationManager.notify((int)(System.currentTimeMillis()/1000), notification.build());*/

    }

    /**
     * 点击了普通推送的通知就会进这个方法
     * @param context
     * @param notificationMessage
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {

        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
        super.onNotifyMessageOpened(context, notificationMessage);
    }


    /**
     * 播放自定义的铃声
     * @param context
     * @param uri
     */
    public void playMusic(Context context){

        String uriStr = "android.resource://" + context.getPackageName() + "/"+R.raw.getmoredata;
        Uri uri=Uri.parse(uriStr);

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(false);
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }
    }
}
