package com.pujoy.charminder.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.ActivityBase;

public class ReminderListActivity extends ActivityBase implements
OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder_list);
	}

	@Override
	public void onClick(View v) {
	}

}
