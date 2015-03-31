/*
**  Class NotificationController
**  src/com/CharmySoft/charminder/helper/NotificationController.java
*/
package com.CharmySoft.charminder.helper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.app.NotificationCompat;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.activities.MainActivity;
import com.CharmySoft.charminder.activities.ReminderListActivity;
import com.CharmySoft.charminder.data.Reminder;
import com.CharmySoft.charminder.other.G;

public class NotificationController{

	public final static int ID = 610104915;
	private static int iCount = 1; 

	public static Notification getNotification(int icon, Intent intent, CharSequence title, CharSequence content) {
		PendingIntent pIntent = PendingIntent.getActivity(G.context, 0, intent,
				0);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				G.context).setSmallIcon(icon).setLargeIcon(normalizeIcon(icon))
				.setContentIntent(pIntent)
				.setContentTitle(title)
				.setAutoCancel(true)
				.setContentText(content);
		return builder.build();
	}
	
	public static Notification getDefaultNotification() {
		return NotificationController.getNotification(R.drawable.charmy,
				new Intent(G.context, MainActivity.class), G.context.getString(R.string.app_name),
				G.context.getString(R.string.noti_default));
	}

	private static Bitmap normalizeIcon(int resID) {
		Bitmap b = BitmapFactory
				.decodeResource(G.context.getResources(), resID);
		Matrix matrix = new Matrix();
		G.context.getResources().getDimension(
				android.R.dimen.notification_large_icon_width);
		matrix.postScale(
				G.context.getResources().getDimension(
						android.R.dimen.notification_large_icon_width)
						/ b.getWidth(),
				G.context.getResources().getDimension(
						android.R.dimen.notification_large_icon_height)
						/ b.getHeight());
		return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(),
				matrix, true);
	}

	public static void pushReminder(Reminder reminder) {
		NotificationManager notificationManager = (NotificationManager) G.context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		TypedArray drawable = G.context.getResources().obtainTypedArray(
				R.array.reminder_list_icons);
		String[] names = G.context.getResources().getStringArray(
				R.array.main_menu_names);
		notificationManager.notify(ID + iCount, getNotification(drawable.getResourceId(reminder.iType - 1, -1),
				new Intent(G.context, ReminderListActivity.class), 
				names[reminder.iType - 1],
				Reminder.formatNotificationText(reminder)));
		drawable.recycle();
		iCount ++ ;
	}

}
