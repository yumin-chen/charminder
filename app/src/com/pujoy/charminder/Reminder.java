package com.pujoy.charminder;

import java.util.Calendar;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.view.WindowManager;

public class Reminder {
	public int type;
	public Calendar time_when_created;
	public Calendar time_to_remind;
	public String note;
	public boolean validity;
	public int level;
	public int repeat;
	public String title;
	public String location;
	public Reminder(int type_of_reminder){
		time_when_created = Calendar.getInstance();
		time_to_remind = Calendar.getInstance();
		validity = true;
		type = type_of_reminder;
	}
	public void Notify(Context c){
		Resources r = c.getResources();
		if(MainActivity.settings.levelSetting[level-1].vibrate){
			Vibrator v = (Vibrator)c.getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = {0, 100, 100, 100, 100};
			v.vibrate(pattern, -1);
		}
		if(MainActivity.settings.levelSetting[level-1].sound){
			Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone rt = RingtoneManager.getRingtone(c, notification);
			rt.play();
		}
		if(MainActivity.settings.levelSetting[level-1].wakeScreen){
			PowerManager powerManager = (PowerManager) c.getSystemService(c.POWER_SERVICE);
			if (!powerManager.isScreenOn())
				c.startActivity(new Intent(c, WakeUp.class));
		}
		switch(type){
		case 1:
			if(MainActivity.settings.levelSetting[level-1].bubble)
				MainActivity.PushFloatingBubble(note + r.getString(R.string.bubble_reminder1));
			if(MainActivity.settings.levelSetting[level-1].notification){
				NotificationManager notificationManager = (NotificationManager) 
						  c.getSystemService(c.NOTIFICATION_SERVICE); 
				Intent intent = new Intent(c, RemindersList.class);
				PendingIntent pIntent = PendingIntent.getActivity(c, 0, intent, 0);
				NotificationCompat.Builder mBuilder =
					    new NotificationCompat.Builder(c)
					    .setSmallIcon(R.drawable.ic_launcher)
				        .setContentIntent(pIntent)
				        .setContentTitle(r.getString(R.string.title_timer1))
				        .setContentText(note + r.getString(R.string.bubble_reminder1));
				notificationManager.notify(1, mBuilder.build()); 
			}
				
			validity = false;
			break;
		case 2:
			if(MainActivity.settings.levelSetting[level-1].bubble)
				MainActivity.PushFloatingBubble(note + r.getString(R.string.bubble_reminder1));
			if(MainActivity.settings.levelSetting[level-1].notification){
				NotificationManager notificationManager = (NotificationManager) 
						  c.getSystemService(c.NOTIFICATION_SERVICE); 
				Intent intent = new Intent(c, RemindersList.class);
				PendingIntent pIntent = PendingIntent.getActivity(c, 0, intent, 0);
				NotificationCompat.Builder mBuilder =
					    new NotificationCompat.Builder(c)
					    .setSmallIcon(R.drawable.ic_launcher)
				        .setContentIntent(pIntent)
				        .setContentTitle(r.getString(R.string.title_timer2))
				        .setContentText(note + r.getString(R.string.bubble_reminder1));
				notificationManager.notify(2, mBuilder.build()); 
			}
			validity = false;
			break;
		case 3:
			if(MainActivity.settings.levelSetting[level-1].bubble)
				MainActivity.PushFloatingBubble(time_to_remind.get(Calendar.HOUR) + ":" 
						+ time_to_remind.get(Calendar.MINUTE) + r.getString(R.string.bubble_reminder1));
			if(MainActivity.settings.levelSetting[level-1].notification){
				NotificationManager notificationManager = (NotificationManager) 
						  c.getSystemService(c.NOTIFICATION_SERVICE); 
				Intent intent = new Intent(c, RemindersList.class);
				PendingIntent pIntent = PendingIntent.getActivity(c, 0, intent, 0);
				NotificationCompat.Builder mBuilder =
					    new NotificationCompat.Builder(c)
					    .setSmallIcon(R.drawable.ic_launcher)
				        .setContentIntent(pIntent)
				        .setContentTitle(r.getString(R.string.title_timer3))
				        .setContentText(time_to_remind.get(Calendar.HOUR) + ":" 
								+ time_to_remind.get(Calendar.MINUTE) + r.getString(R.string.bubble_reminder1));
				notificationManager.notify(3, mBuilder.build()); 
			}
			time_to_remind.add(Calendar.HOUR, 1);
			break;
		case 4:
			String s;
			s = title + r.getString(R.string.bubble_reminder1);
			if(!(note.isEmpty() && location.isEmpty())){
				s += r.getString(R.string.bubble_reminder4) + location + note + 
						r.getString(R.string.bubble_reminder4end);
			}
			if(MainActivity.settings.levelSetting[level-1].bubble){
				MainActivity.PushFloatingBubble(s);
			}
			if(MainActivity.settings.levelSetting[level-1].notification){
				NotificationManager notificationManager = (NotificationManager) 
						  c.getSystemService(c.NOTIFICATION_SERVICE); 
				Intent intent = new Intent(c, RemindersList.class);
				PendingIntent pIntent = PendingIntent.getActivity(c, 0, intent, 0);
				NotificationCompat.Builder mBuilder =
					    new NotificationCompat.Builder(c)
					    .setSmallIcon(R.drawable.ic_launcher)
				        .setContentIntent(pIntent)
				        .setContentTitle(r.getString(R.string.title_timer4))
				        .setContentText(s);
				notificationManager.notify(4, mBuilder.build()); 
			}
			switch(repeat){
			case -1:
				validity = false;
				break;
			case 0:
				validity = false;
				break;
			case 1:
				time_to_remind.add(Calendar.DAY_OF_MONTH, 1);
				break;
			case 2:
				time_to_remind.add(Calendar.DAY_OF_MONTH, 7);
				break;
			case 3:
				time_to_remind.add(Calendar.MONTH, 1);
				break;
			case 4:
				time_to_remind.add(Calendar.YEAR, 1);
				break;
			}
			break;
		}
		MainActivity.SaveReminders();
	}
}
