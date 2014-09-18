package com.pujoy.charminder.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pujoy.charminder.R;
import com.pujoy.charminder.activities.layout.ReminderItem;
import com.pujoy.charminder.base.ActivityBase;
import com.pujoy.charminder.other.G;

public class ReminderListActivity extends ActivityBase implements
OnClickListener {
	ReminderItem[] aReminderItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder_list);
		update();
	}

	private void update() {
		ImageView imageEmpty = (ImageView)findViewById(R.id.reminder_list_empty);
		TextView textEmpty = (TextView)findViewById(R.id.reminder_list_empty_text);
		if(G.reminders.size() > 0){
			imageEmpty.setVisibility(View.GONE);
			textEmpty.setVisibility(View.GONE);
			View foot = (View)findViewById(R.id.reminder_foot);
			aReminderItem = new ReminderItem[G.reminders.size()];
			LinearLayout mainView = (LinearLayout)findViewById(R.id.acvitity_main_view);
			for(int i = 0; i < G.reminders.size(); i++){
				aReminderItem[i] = new ReminderItem(this);
				mainView.addView(aReminderItem[i]);
				aReminderItem[i].setReminder(i);
			}
			mainView.bringChildToFront(foot);
		}else{
			imageEmpty.setVisibility(View.VISIBLE);
			textEmpty.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
	}

}
