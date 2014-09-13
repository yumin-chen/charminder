package com.pujoy.charminder;

import com.pujoy.charminder.views.FloatingText;

import android.graphics.Color;

public class Constants {
	public static final int COLOR_LIGHTBLUE = Color.rgb(228, 242, 254);
	public static final int COLOR_DARKBLUE = Color.rgb(48, 78, 98);
	public static final int COLOR_GREENBLUE = Color.rgb(100, 191, 206);
	public static final int COLOR_TRANSPARENT = Color.argb(0, 255, 255, 255);
	public static FloatingText floatingText;
	
	public static void pushText(String text){
		pushText(text, 5);
	}
	
	public static void pushText(String text, int time){
		if(floatingText != null){
			if(floatingText.isCreated())
				floatingText.remove();
			floatingText = null;
		}
		floatingText = new FloatingText();
		floatingText.setText(text);
		floatingText.iTimer = time;
		floatingText.create();
	}
}
