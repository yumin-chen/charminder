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
		title="";
		location="";
		note="";
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
		case 4:
			String s;
			s = title + r.getString(R.string.bubble_reminder1);
			if(!(note.isEmpty() && location.isEmpty())){
				s += r.getString(R.string.bubble_reminder4) + location + note + 
						r.getString(R.string.bubble_reminder4end);
			}
			MainActivity.PushFloatingBubble(s);
			switch(repeat){
			case -1:
				validity = false;
			case 0:
				validity = false;
			case 1:
				time_to_remind.add(Calendar.DAY_OF_MONTH, 1);
			case 2:
				time_to_remind.add(Calendar.DAY_OF_MONTH, 7);
			case 3:
				time_to_remind.add(Calendar.MONTH, 1);
			case 4:
				time_to_remind.add(Calendar.YEAR, 1);
			}
			break;
		}
	}
}
