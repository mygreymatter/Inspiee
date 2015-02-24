package com.mayo.inspiee.alarm;

import java.io.IOException;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.mayo.inspiee.Inspiee;
import com.mayo.inspiee.R;
import com.mayo.inspiee.activities.ActInspiration;


public class AlarmReceiver extends BroadcastReceiver {
	private MediaPlayer mMediaPlayer;
	private NotificationManager mNotificationManager;
	private static final int NOTIFICATION_ID = 1;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Alarm Fired", Toast.LENGTH_SHORT).show();
		Log.i(Inspiee.TAG_INFO,"Alarm Fired");
		playSound(context, getAlarmUri());
		sendNotification("Inspiration is a click away..!", context);				
	}

	private void playSound(Context context, Uri alert) {
		mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(context, alert);
			final AudioManager audioManager = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
			if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
				mMediaPlayer.prepare();
				mMediaPlayer.start();
				
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						mMediaPlayer.stop();
						Log.i(Inspiee.TAG_INFO,"Media Player stopped.");
					}
				}, 10000);
			}
		} catch (IOException e) {
			System.out.println("OOPS");
		}
	}

	// Get an alarm sound. Try for an alarm. If none set, try notification,
	// Otherwise, ringtone.
	private Uri getAlarmUri() {
		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alert == null) {
			alert = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			if (alert == null) {
				alert = RingtoneManager
						.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			}
		}
		return alert;
	}
	
    private void sendNotification(String msg,Context context) {
        mNotificationManager = (NotificationManager)
               context.getSystemService(Context.NOTIFICATION_SERVICE);
    
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
            new Intent(context,ActInspiration.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle("Get Inspired")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))        
        .setContentText(msg)
        .setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

}

