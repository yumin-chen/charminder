package com.pujoy.charminder;

import java.util.Calendar;
import java.util.Date;

import android.content.res.Resources;

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
	public void Notify(Resources r){
		switch(type){
		case 1:
			MainActivity.PushFloatingBubble(note + r.getString(R.string.bubble_reminder1));
			validity = false;
			break;
		case 2:
			MainActivity.PushFloatingBubble(note + r.getString(R.string.bubble_reminder1));
			validity = false;
			break;
		case 3:
			MainActivity.PushFloatingBubble(time_to_remind.get(Calendar.HOUR) + ":" 
					+ time_to_remind.get(Calendar.MINUTE) + r.getString(R.string.bubble_reminder1));
			time_to_remind.add(Calendar.HOUR, 1);
			break;
			
		}
	}
}
