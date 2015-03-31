/*
**  Class MainActivity
**  src/com/CharmySoft/charminder/activities/MainActivity.java
*/
package com.CharmySoft.charminder.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.base.ActivityBase;
import com.CharmySoft.charminder.base.ViewBase;
import com.CharmySoft.charminder.data.ReminderList;
import com.CharmySoft.charminder.data.Settings;
import com.CharmySoft.charminder.other.G;
import com.CharmySoft.charminder.other.Log;
import com.CharmySoft.charminder.other.MainService;
import com.CharmySoft.charminder.views.Charmy;

public class MainActivity extends ActivityBase {
	static boolean bInitialized;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		finish();
		if(G.settings == null){
			G.settings = new Settings();
		}
		
		if(Build.BRAND.contentEquals("Xiaomi") && !G.settings.bSkipFloatingWindowCheck){	
			new ViewBase();
			G.goToActivity(NoFloatingWindowActivity.class);
			return;
		}
		
		
		if(G.reminders == null){
			G.reminders = new ReminderList();
		}
		
		if (bInitialized) {
			if (G.mCharmy != null) {
				if (!G.mCharmy.isCreated())
					G.mCharmy.create();
				G.mCharmy.pushBubble(getResources().getString(
						R.string.b_user_guide));
			}
			return;
		}

		if (G.mCharmy == null) {
			G.mCharmy = new Charmy();
		}
		G.mCharmy.create();
		G.mCharmy.pushBubble(getResources().getString(
				R.string.b_welcome_greeting,
				getResources().getString(R.string.floating_icon_name)).concat(
				getResources().getString(R.string.b_user_guide)));
		
		if(startService(new Intent(this, MainService.class)) == null)
		{
			Log.w("Failed to create service");
		}
		bInitialized = true;
	}
		

}
