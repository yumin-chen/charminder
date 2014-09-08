package com.pujoy.charminder;

import android.content.SharedPreferences;



public class eSettings {
	private static final int NUM_CIRCLE_ITEMS = 5;
	boolean autoDeleteExpiredReminder = false;
	int bubbleTimeOut = 5;
	boolean[] mainMenuSection = new boolean[6];
	boolean[] circleSection = new boolean[6];
	LevelSetting[] levelSetting = new LevelSetting[NUM_CIRCLE_ITEMS];
	public eSettings(SharedPreferences sp){
		FormatBoolArray(sp.getString("mainMenuSection", "110000"), mainMenuSection);
		FormatBoolArray(sp.getString("circleSection", "111111"), circleSection);
		autoDeleteExpiredReminder = sp.getBoolean("autoDeleteExpiredReminder", false);
		bubbleTimeOut = sp.getInt("bubbleTimeOut", 5);
		levelSetting[0] = new LevelSetting(sp.getString("levelSetting1", "10000"));
		levelSetting[1] = new LevelSetting(sp.getString("levelSetting2", "11000"));
		levelSetting[2] = new LevelSetting(sp.getString("levelSetting3", "11100"));
		levelSetting[3] = new LevelSetting(sp.getString("levelSetting4", "11110"));
		levelSetting[4] = new LevelSetting(sp.getString("levelSetting5", "11111"));
		bubbleTimeOut = 5;
	}
	
	public void save(SharedPreferences sp){
	       SharedPreferences.Editor editor = sp.edit();
	       editor.putString("mainMenuSection", genStringFromBoolArray(mainMenuSection));
	       editor.putString("circleSection", genStringFromBoolArray(circleSection));
	       editor.putBoolean("autoDeleteExpiredReminder", autoDeleteExpiredReminder);
	       editor.putInt("bubbleTimeOut", bubbleTimeOut);
	       editor.putString("levelSetting1", levelSetting[0].genString());
	       editor.putString("levelSetting2", levelSetting[1].genString());
	       editor.putString("levelSetting3", levelSetting[2].genString());
	       editor.putString("levelSetting4", levelSetting[3].genString());
	       editor.putString("levelSetting5", levelSetting[4].genString());
	      editor.commit();
	}
	
	private void FormatBoolArray(String s, boolean[] b){
		for(int i=0;i<s.length();i++){
			b[i] = s.charAt(i) != '0';
		}
	}
	private String genStringFromBoolArray(boolean[] b){
		String s="";
		for(int i=0;i<b.length;i++){
			s+=b[i]? "1" : "0";
		}
		return s;
	}
}
