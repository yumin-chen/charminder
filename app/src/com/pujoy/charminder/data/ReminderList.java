package com.pujoy.charminder.data;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

import com.pujoy.charminder.R;
import com.pujoy.charminder.other.G;

public class ReminderList {
	private ArrayList<Reminder> mReminders;
	
	public ReminderList(){
		SharedPreferences sp = (G.context.getSharedPreferences("Reminders", Context.MODE_PRIVATE));
		mReminders = new ArrayList<Reminder>();
		int size = sp.getInt("reminderCount", 0);
    	for(int i=0; i<size; i++){
    		Reminder r = new Reminder(0);
    		r.iType = sp.getInt("r"+i+"type", 0);
    		r.iPriority = sp.getInt("r"+i+"level", 0);
    		r.iRepeat = sp.getInt("r"+i+"repeat", 0);
    		r.bValidity = sp.getBoolean("r"+i+"validity", false);
    		r.sTitle = sp.getString("r"+i+"title", "");
    		r.sLocation = sp.getString("r"+i+"location", "");
    		r.sNote = sp.getString("r"+i+"note", "");
    		r.mTimeToRemind.setTimeInMillis(sp.getLong("r"+i+"timeToRemind", 0));
    		r.mTimeCreated.setTimeInMillis(sp.getLong("r"+i+"timeCreatedd", 0));
    		mReminders.add(r);
    	}
	}
	
	private void save() {
		SharedPreferences sp = (G.context.getSharedPreferences("Reminders", Context.MODE_PRIVATE));
    	SharedPreferences.Editor editor = sp.edit();
    	editor.putInt("reminderCount", mReminders.size());
    	for(int i=0; i<mReminders.size(); i++){
    		editor.putInt("r"+i+"type", mReminders.get(i).iType);
    		editor.putInt("r"+i+"level", mReminders.get(i).iPriority);
    		editor.putInt("r"+i+"repeat", mReminders.get(i).iRepeat);
    		editor.putBoolean("r"+i+"validity", mReminders.get(i).bValidity);
    		editor.putString("r"+i+"title", mReminders.get(i).sTitle);
    		editor.putString("r"+i+"location", mReminders.get(i).sLocation);
    		editor.putString("r"+i+"note", mReminders.get(i).sNote);
    		editor.putLong("r"+i+"timeToRemind", mReminders.get(i).mTimeToRemind.getTimeInMillis());
    		editor.putLong("r"+i+"timeCreated", mReminders.get(i).mTimeCreated.getTimeInMillis());
    	}
	    editor.commit();
	}
	
	public void add(Reminder newReminder) {
		add(newReminder, false);
	}

	public void add(Reminder newReminder, boolean pushBubble) {
		mReminders.add(newReminder);
		save();
		if(pushBubble){
			G.mCharmy.PushBubble(getBubbleText(newReminder));
		}
	}

	private String getBubbleText(Reminder reminder) {
		String[] bubble = G.context.getResources().getStringArray(
				R.array.bubble_add_reminder);
		return String.format(bubble[reminder.iType-1],
				Reminder.formatTime(reminder));
	}

	
}
