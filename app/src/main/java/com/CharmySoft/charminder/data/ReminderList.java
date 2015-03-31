/*
**  Class ReminderList
**  src/com/CharmySoft/charminder/data/ReminderList.java
*/
package com.CharmySoft.charminder.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.other.G;

import java.util.ArrayList;

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
    		r.sTimePhrase = sp.getString("r"+i+"timePhrase", "");
    		r.mTimeToRemind.setTimeInMillis(sp.getLong("r"+i+"timeToRemind", 0));
    		r.mTimeCreated.setTimeInMillis(sp.getLong("r"+i+"timeCreated", 0));
    		mReminders.add(r);
    	}
	}
	
	public void save() {
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
    		editor.putString("r"+i+"timePhrase", mReminders.get(i).sTimePhrase);
    		editor.putLong("r"+i+"timeToRemind", mReminders.get(i).mTimeToRemind.getTimeInMillis());
    		editor.putLong("r"+i+"timeCreated", mReminders.get(i).mTimeCreated.getTimeInMillis());
    	}
	    editor.commit();
	}
	
	public int size(){
		return mReminders.size();
	}
	
	public Reminder get(int index) {
		return mReminders.get(index);
	}
	
	public Reminder remove(int index){
		Reminder ret = mReminders.remove(index);
		save();
		return ret;
	}
	
	public void add(Reminder newReminder) {
		add(newReminder, false);
	}

	public void add(Reminder newReminder, boolean pushBubble) {
		mReminders.add(newReminder);
		save();
		if(pushBubble){
			G.mCharmy.pushBubble(getBubbleText(newReminder));
		}
	}

	private String getBubbleText(Reminder reminder) {
		String[] bubble = G.context.getResources().getStringArray(
				R.array.bubble_add_reminder);
		return String.format(bubble[reminder.iType-1],
				Reminder.formatRemindTime(reminder));
	}



	
}
