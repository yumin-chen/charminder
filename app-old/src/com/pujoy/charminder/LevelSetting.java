package com.pujoy.charminder;

public class LevelSetting {
public boolean bubble;
public boolean notification;
public boolean wakeScreen;
public boolean vibrate;
public boolean sound;
public LevelSetting(String s){
	bubble = s.charAt(0) != '0';
	notification = s.charAt(1) != '0';
	wakeScreen = s.charAt(2) != '0';
	vibrate = s.charAt(3) != '0';
	sound = s.charAt(4) != '0';
}
public String genString(){
	String s="";
	s+=(bubble)? "1" : "0";
	s+=(notification)? "1" : "0";
	s+=(wakeScreen)? "1" : "0";
	s+=(vibrate)? "1" : "0";
	s+=(sound)? "1" : "0";
	return s;
}
}
