package com.pujoy.charminder;

public class eSettings {
	private static final int NUM_CIRCLE_ITEMS = 5;
	boolean autoDeleteExpiredReminder = false;
	int bubbleTimeOut = 5;
	LevelSetting[] levelSetting = new LevelSetting[NUM_CIRCLE_ITEMS];
	public eSettings(){
		levelSetting[0] = new LevelSetting(true, false, false, false, false);
		levelSetting[1] = new LevelSetting(true, true, false, false, false);
		levelSetting[2] = new LevelSetting(true, true, true, false, false);
		levelSetting[3] = new LevelSetting(true, true, true, true, false);
		levelSetting[4] = new LevelSetting(true, true, true, true, true);
		bubbleTimeOut = 5;
		autoDeleteExpiredReminder = false;
	}
}
