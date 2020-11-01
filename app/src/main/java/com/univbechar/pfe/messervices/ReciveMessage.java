package com.univbechar.pfe.messervices;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ReciveMessage extends BroadcastReceiver {

    public static String infos[];
    private String msg2;
    DBConnection dbConnection;


    final int RECEIVE_SMS_PERMISSION_REQUEST_CODE = 2;
    final int READ_SMS_PERMISSION_REQUEST_CODE = 3;
    public boolean checkPermission(Context context, String permission){
        int check = ContextCompat.checkSelfPermission(context, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }


    @TargetApi(10000)
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onReceive(Context context, Intent intent) {
        dbConnection = new DBConnection(context);
        if(checkPermission(context, Manifest.permission.RECEIVE_SMS)){
            if(checkPermission(context, Manifest.permission.READ_SMS)){
                Bundle bundle1 = intent.getExtras();
                    final Object[] msgContent = (Object[]) bundle1.get("pdus");
                    for (int i = 0; i < msgContent.length; i++) {
                        SmsMessage newSms = SmsMessage.createFromPdu((byte[]) msgContent[i]);
                        String msg = newSms.getDisplayMessageBody();
                        if(msg.startsWith("SPGSystem")){
                            infos = msg.split("\\,");
                            switch (infos[1]) {
                                case "1":
                                case "2":
                                case "3":
                                case "5":
                                case "6":
                                    msg2 = infos[2];
                                    notifyMe2(context, newSms.getDisplayOriginatingAddress(), infos[2]);
                                    break;
                                case "4":
                                    int index = msg.indexOf(":")+1;
                                    msg = msg.substring(index);
                                    infos = msg.split("\\,");
                                    msg2 = msg;
                                    Client client = new Client(infos[0], infos[1], infos[2], infos[3], infos[4], infos[5], infos[6]);
                                    dbConnection.insertData(client);
                                    notifyMe(context, newSms.getDisplayOriginatingAddress(), newSms.getDisplayMessageBody().substring(12));
                                    break;
                            }
                        }
                    }

            }else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECEIVE_SMS}, RECEIVE_SMS_PERMISSION_REQUEST_CODE);
        }
    }

    static int id = 1;
    NotificationManager manager;
    @RequiresApi(api = Build.VERSION_CODES.CUR_DEVELOPMENT)
    public void notifyMe(Context context, String title, String text){
        manager = (NotificationManager)  context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_stat_new_message)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent = new Intent(context, ShowMessage.class);
        intent.putExtra("msg", msg2);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ShowMessage.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resutIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resutIntent);
        builder.addAction(R.drawable.show, "Affichier", resutIntent);
        manager.notify(id, builder.build());
        id++;

    }

    NotificationManager manager2;
    @RequiresApi(api = Build.VERSION_CODES.CUR_DEVELOPMENT)
    public void notifyMe2(Context context, String title, String text){
        manager2 = (NotificationManager)  context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_stat_new_message)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent = new Intent(context, ShowMessage2.class);
        intent.putExtra("msg", msg2);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ShowMessage2.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent resutIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resutIntent);
        builder.addAction(R.drawable.show, "Affichier", resutIntent);
        manager2.notify(id, builder.build());
        id++;

    }
}
