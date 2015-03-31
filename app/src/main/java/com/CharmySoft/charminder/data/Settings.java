/*
**  Class Settings
**  src/com/CharmySoft/charminder/data/Settings.java
*/
package com.CharmySoft.charminder.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public class Settings {
	// Settings Data
	public boolean bPersistentNotification;
	public boolean bAutoDeleteExpiredReminder;
	public int iBubbleTimeOut;
	public double dCircleAngle;
	public PrioritySetting[] mPrioritySetting = new PrioritySetting[C.NUM_CIRCLE_ITEMS];
	public byte[] mCircleSection;
	public boolean bSkipFloatingWindowCheck;
	

	public Settings() {
		SharedPreferences sp = G.context.getSharedPreferences("Settings",
				Context.MODE_PRIVATE);
		mCircleSection = formatByteArray(sp.getString("mCircleSection",
				"0123456"));
		bPersistentNotification = sp.getBoolean(
				"bPersistentNotification", true);
		bAutoDeleteExpiredReminder = sp.getBoolean(
				"bAutoDeleteExpiredReminder", false);
		bSkipFloatingWindowCheck = sp.getBoolean(
				"bSkipFloatingWindowCheck", false);
		iBubbleTimeOut = sp.getInt("iBubbleTimeOut", 5);
		dCircleAngle = Double.longBitsToDouble(sp.getLong("dCircleAngle", 0));
		mPrioritySetting[0] = new PrioritySetting(sp.getString(
				"mPrioritySetting1", "10000"));
		mPrioritySetting[1] = new PrioritySetting(sp.getString(
				"mPrioritySetting2", "11000"));
		mPrioritySetting[2] = new PrioritySetting(sp.getString(
				"mPrioritySetting3", "11100"));
		mPrioritySetting[3] = new PrioritySetting(sp.getString(
				"mPrioritySetting4", "11110"));
		mPrioritySetting[4] = new PrioritySetting(sp.getString(
				"mPrioritySetting5", "11111"));
	}

	public void save() {
		SharedPreferences sp = G.context.getSharedPreferences("Settings",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("mCircleSection",
				generateStringFromByteArray(mCircleSection));
		editor.putBoolean("bPersistentNotification",
				bPersistentNotification);
		editor.putBoolean("bAutoDeleteExpiredReminder",
				bAutoDeleteExpiredReminder);
		editor.putBoolean("bSkipFloatingWindowCheck",
				bSkipFloatingWindowCheck);
		editor.putLong("dCircleAngle", Double.doubleToLongBits(dCircleAngle));
		editor.putInt("iBubbleTimeOut", iBubbleTimeOut);
		editor.putString("mPrioritySetting1",
				mPrioritySetting[0].generateString());
		editor.putString("mPrioritySetting2",
				mPrioritySetting[1].generateString());
		editor.putString("mPrioritySetting3",
				mPrioritySetting[2].generateString());
		editor.putString("mPrioritySetting4",
				mPrioritySetting[3].generateString());
		editor.putString("mPrioritySetting5",
				mPrioritySetting[4].generateString());
		editor.commit();
	}
	
	public void deleteCircleItem(int index){
		byte[] bytes = new byte[mCircleSection.length - 1];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = mCircleSection[i + (i >= index? 1: 0)];
		}
		mCircleSection = bytes;
	}
	public void addCircleItem(byte item, int index){
		byte[] bytes = new byte[mCircleSection.length + 1];
		for (int i = 0; i < mCircleSection.length; i++) {
			bytes[i + (i >= index? 1: 0)] = mCircleSection[i];
		}
		bytes[index] = item;
		mCircleSection = bytes;
	}

	private byte[] formatByteArray(String string) {
		byte[] bytes = string.getBytes();
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] -= '0';
		}
		return bytes;
	}

	private String generateStringFromByteArray(byte[] bytes) {
		byte[] b = bytes.clone();
		for (int i = 0; i < b.length; i++) {
			b[i] += '0';
		}
		return new String(b);
	}

}
