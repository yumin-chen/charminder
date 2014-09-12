package com.pujoy.charminder;

import com.pujoy.charminder.views.Charmy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class MainActivity extends Activity {
	public static Context con;
	public static int iLang;
	public static final int NUM_CIRCLE_ITEMS = 7;
	static boolean vInitialized;
	public static Charmy charmy;
	public static TimerThread timerThread;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finish();
		if(timerThread == null)
			timerThread = new TimerThread();
		
		if (vInitialized){
			if(charmy != null){
				if(!charmy.isCreated())
					charmy.create();
				charmy.PushBubble(getResources().getString(R.string.b_user_guide));
			}
			return;
		}
		con = this;
		iLang = getResources().getString(R.string.floating_icon_name).equals("Charmy")? 0 : 1;
		if(charmy == null){
			charmy = new Charmy();
		}
		charmy.create();
		charmy.PushBubble(getResources().getString(R.string.b_welcome_greeting,
				getResources().getString(R.string.floating_icon_name)).concat(
				getResources().getString(R.string.b_user_guide)));
		vInitialized = true;
	}
	
}
