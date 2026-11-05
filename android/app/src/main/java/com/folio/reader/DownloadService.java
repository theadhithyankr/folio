package com.folio.reader;
import android.app.*;import android.content.Intent;import android.os.IBinder;
public class DownloadService extends Service {
 @Override public void onCreate(){super.onCreate();NotificationManager nm=getSystemService(NotificationManager.class);nm.createNotificationChannel(new NotificationChannel("downloads","Book downloads",NotificationManager.IMPORTANCE_LOW));Intent launch=new Intent(this,MainActivity.class);PendingIntent intent=PendingIntent.getActivity(this,0,launch,PendingIntent.FLAG_IMMUTABLE);startForeground(20,new Notification.Builder(this,"downloads").setContentTitle("Folio is downloading books").setContentText("Tap to return to your library").setSmallIcon(android.R.drawable.stat_sys_download).setContentIntent(intent).setOngoing(true).build());}
 @Override public int onStartCommand(Intent i,int flags,int id){return START_NOT_STICKY;}
 @Override public IBinder onBind(Intent i){return null;}
}
