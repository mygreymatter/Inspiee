package com.mayo.inspiee.alarm;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


public class AlarmUtils {
	//request code for am : 0 and pm : 1	
	public static void setAlarm(Context context,int hour,int minute,int requestCode){

//		Toast.makeText(context, "Alarm Set " + Integer.toString(requestCode), Toast.LENGTH_SHORT).show();
		AlarmManager alarmMgr;
		PendingIntent alarmIntent;
		
		alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(context,requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		
		Date date = new Date();
		// Set the alarm to start at approximately 2:00 p.m.
		Calendar calAlarm = Calendar.getInstance();
		calAlarm.setTime(date);
//		calAlarm.setTimeInMillis(System.currentTimeMillis());
		calAlarm.set(Calendar.HOUR_OF_DAY, hour);
		calAlarm.set(Calendar.MINUTE, minute);
		calAlarm.set(Calendar.SECOND, 0);
		
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTime(date);

		if (calAlarm.before(currentDate)) {
			calAlarm.add(Calendar.DATE, 1);
		}
		
		// With setInexactRepeating(), you have to use one of the AlarmManager interval
		// constants--in this case, AlarmManager.INTERVAL_DAY.
		alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calAlarm.getTimeInMillis(),
		        AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
	}
	
	public static void cancelAlarm(Context context,int requestCode) {
		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context,requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

		alarmManager.cancel(sender);
	}
}
