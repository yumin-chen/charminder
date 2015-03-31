/*
**  Class ReminderListActivity
**  src/com/CharmySoft/charminder/activities/ReminderListActivity.java
*/
package com.CharmySoft.charminder.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.activities.layout.ReminderItem;
import com.CharmySoft.charminder.base.ActivityBase;
import com.CharmySoft.charminder.other.G;

public class ReminderListActivity extends ActivityBase implements
OnClickListener {
	static ReminderItem[] aReminderItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		update(this);
	}

	static public void update(Activity context) {
		context.setContentView(R.layout.activity_reminder_list);
		ImageView imageEmpty = (ImageView)context.findViewById(R.id.reminder_list_empty);
		TextView textEmpty = (TextView)context.findViewById(R.id.reminder_list_empty_text);
		if(G.reminders.size() > 0){
			imageEmpty.setVisibility(View.GONE);
			textEmpty.setVisibility(View.GONE);
			View foot = context.findViewById(R.id.reminder_foot);
			aReminderItem = new ReminderItem[G.reminders.size()];
			LinearLayout mainView = (LinearLayout)context.findViewById(R.id.acvitity_main_view);
			for(int i = 0; i < G.reminders.size(); i++){
				aReminderItem[i] = new ReminderItem(context);
				mainView.addView(aReminderItem[i]);
				aReminderItem[i].setReminder(i);
			}
			mainView.bringChildToFront(foot);
		}else{
			imageEmpty.setVisibility(View.VISIBLE);
			textEmpty.setVisibility(View.VISIBLE);
		}
	}
	
	static public void updateItem(int index) {
		aReminderItem[index].update();
	}
	
	public static void updateTime() {
		if(aReminderItem == null){
			return;
		}
		if(G.reminders.size() != aReminderItem.length){
			update((Activity) G.context);
			return;
		}
		for(int i = 0; i < G.reminders.size(); i++){
			aReminderItem[i].updateTime();
		}
	}
	@Override
	public void onClick(View v) {
	}

}
