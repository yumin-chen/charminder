package com.pujoy.charminder.activities;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.ActivityBase;
import com.pujoy.charminder.data.PrioritySetting;
import com.pujoy.charminder.other.G;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class PrioritySettingActivity extends ActivityBase implements
		OnClickListener {
	static PrioritySetting mSetting;
	static int iPriority;

	public static void setSettings(PrioritySetting setting, int priority) {
		mSetting = setting;
		iPriority = priority;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_priority_setting);

		if (mSetting.bBubble) {
			ImageView Switch = (ImageView) findViewById(R.id.priority_bubble_switch);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if (mSetting.bNotification) {
			ImageView Switch = (ImageView) findViewById(R.id.priority_notification_center_switch);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if (mSetting.bWakeScreen) {
			ImageView Switch = (ImageView) findViewById(R.id.priority_wake_screen_switch);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if (mSetting.bVibrate) {
			ImageView Switch = (ImageView) findViewById(R.id.priority_vibrate_switch);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if (mSetting.bSound) {
			ImageView Switch = (ImageView) findViewById(R.id.priority_sound_switch);
			Switch.setImageResource(R.drawable.switch_1);
		}

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.priority_bubble_layout:
			if (mSetting.bBubble) {
				mSetting.bBubble = false;
				ImageView Switch = (ImageView) findViewById(R.id.priority_bubble_switch);
				Switch.setImageResource(R.drawable.switch_0);
			} else {
				mSetting.bBubble = true;
				ImageView Switch = (ImageView) findViewById(R.id.priority_bubble_switch);
				Switch.setImageResource(R.drawable.switch_1);
			}
			G.settings.mPrioritySetting[iPriority - 1] = mSetting;
			G.settings.save();
			break;
		case R.id.priority_notification_center_layout:
			if (mSetting.bNotification) {
				mSetting.bNotification = false;
				ImageView Switch = (ImageView) findViewById(R.id.priority_notification_center_switch);
				Switch.setImageResource(R.drawable.switch_0);
			} else {
				mSetting.bNotification = true;
				ImageView Switch = (ImageView) findViewById(R.id.priority_notification_center_switch);
				Switch.setImageResource(R.drawable.switch_1);
			}
			G.settings.mPrioritySetting[iPriority - 1] = mSetting;
			G.settings.save();
			break;
		case R.id.priority_wake_screen_layout:
			if (mSetting.bWakeScreen) {
				mSetting.bWakeScreen = false;
				ImageView Switch = (ImageView) findViewById(R.id.priority_wake_screen_switch);
				Switch.setImageResource(R.drawable.switch_0);
			} else {
				mSetting.bWakeScreen = true;
				ImageView Switch = (ImageView) findViewById(R.id.priority_wake_screen_switch);
				Switch.setImageResource(R.drawable.switch_1);
			}
			G.settings.mPrioritySetting[iPriority - 1] = mSetting;
			G.settings.save();
			break;
		case R.id.priority_vibrate_layout:
			if (mSetting.bVibrate) {
				mSetting.bVibrate = false;
				ImageView Switch = (ImageView) findViewById(R.id.priority_vibrate_switch);
				Switch.setImageResource(R.drawable.switch_0);
			} else {
				mSetting.bVibrate = true;
				ImageView Switch = (ImageView) findViewById(R.id.priority_vibrate_switch);
				Switch.setImageResource(R.drawable.switch_1);
			}
			G.settings.mPrioritySetting[iPriority - 1] = mSetting;
			G.settings.save();
			break;
		case R.id.priority_sound_layout:
			if (mSetting.bSound) {
				mSetting.bSound = false;
				ImageView Switch = (ImageView) findViewById(R.id.priority_sound_switch);
				Switch.setImageResource(R.drawable.switch_0);
			} else {
				mSetting.bSound = true;
				ImageView Switch = (ImageView) findViewById(R.id.priority_sound_switch);
				Switch.setImageResource(R.drawable.switch_1);
			}
			G.settings.mPrioritySetting[iPriority - 1] = mSetting;
			G.settings.save();
			break;
		}

	}
}
