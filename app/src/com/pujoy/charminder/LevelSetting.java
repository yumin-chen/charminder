package com.pujoy.charminder;

public class LevelSetting {
public boolean bubble;
public boolean notification;
public boolean wakeScreen;
public boolean vibrate;
public boolean sound;
public LevelSetting(boolean b, boolean n, boolean w, boolean v, boolean s){
	bubble = b;
	notification = n;
	wakeScreen = w;
	vibrate = v;
	sound = s;
}
}
