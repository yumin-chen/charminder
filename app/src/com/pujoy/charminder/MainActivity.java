package com.pujoy.charminder;

import com.pujoy.charminder.views.Charmy;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class MainActivity extends Activity {
	public static int iLang;
	static boolean bInitialized;
	public static Context mCon;
	public static Charmy mCharmy;
	public static TimerThread mTimerThread;
	
	public static final int NUM_CIRCLE_ITEMS = 7;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finish();
		iLang = getResources().getString(R.string.floating_icon_name).equals("Charmy")? 0 : 1;
		if(mTimerThread == null)
			mTimerThread = new TimerThread();
		
		if (bInitialized){
			if(mCharmy != null){
				if(!mCharmy.isCreated())
					mCharmy.create();
				mCharmy.PushBubble(getResources().getString(R.string.b_user_guide));
			}
			return;
		}
		mCon = this;
		if(mCharmy == null){
			mCharmy = new Charmy();
		}
		mCharmy.create();
		mCharmy.PushBubble(getResources().getString(R.string.b_welcome_greeting,
				getResources().getString(R.string.floating_icon_name)).concat(
				getResources().getString(R.string.b_user_guide)));
		bInitialized = true;
	}
	
}
