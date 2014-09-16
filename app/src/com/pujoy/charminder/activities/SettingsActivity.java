package com.pujoy.charminder.activities;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.ActivityBase;
import com.pujoy.charminder.other.G;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

public class SettingsActivity extends ActivityBase implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		TextView tvBubbleTime = (TextView) findViewById(R.id.setting_bubbletime_num);
		tvBubbleTime.setText(G.settings.iBubbleTimeOut
				+ getString(R.string.unit_second));
		if (G.settings.bAutoDeleteExpiredReminder) {
			ImageView ivAutoDelete = (ImageView) findViewById(R.id.setting_autodelete_switch);
			ivAutoDelete.setImageResource(R.drawable.switch_1);
		}

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_circle_interface_layout:
			startActivity(new Intent(this, CircleLayoutSettingActivity.class));
			break;
		case R.id.setting_autodelete_layout:
			if (G.settings.bAutoDeleteExpiredReminder) {
				G.settings.bAutoDeleteExpiredReminder = false;
				ImageView ivAutoDelete = (ImageView) findViewById(R.id.setting_autodelete_switch);
				ivAutoDelete.setImageResource(R.drawable.switch_0);
			} else {
				G.settings.bAutoDeleteExpiredReminder = true;
				ImageView ivAutoDelete = (ImageView) findViewById(R.id.setting_autodelete_switch);
				ivAutoDelete.setImageResource(R.drawable.switch_1);
			}
			G.settings.save();
			break;
		case R.id.setting_bubbletime_layout:
			AlertDialog.Builder alert = new AlertDialog.Builder(
					SettingsActivity.this);
			alert.setTitle(getString(R.string.select_bubbletimeout));
			final NumberPicker picker = new NumberPicker(SettingsActivity.this);
			picker.setMinValue(1);
			picker.setMaxValue(30);
			picker.setValue(G.settings.iBubbleTimeOut);
			alert.setPositiveButton(getString(R.string.ok),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							G.settings.iBubbleTimeOut = picker.getValue();
							TextView tvBubbleTime = (TextView) findViewById(R.id.setting_bubbletime_num);
							tvBubbleTime.setText(G.settings.iBubbleTimeOut
									+ getString(R.string.unit_second));
							G.settings.save();
						}
					});

			alert.setNegativeButton(getString(R.string.cancel),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Cancel.
						}
					});
			alert.setView(picker);
			alert.show();

			break;
		case R.id.setting_level1_layout:
			PrioritySettingActivity.setSettings(G.settings.mPrioritySetting[0],
					1);
			startActivity(new Intent(this, PrioritySettingActivity.class));
			break;
		case R.id.setting_level2_layout:
			PrioritySettingActivity.setSettings(G.settings.mPrioritySetting[1],
					2);
			startActivity(new Intent(this, PrioritySettingActivity.class));
			break;
		case R.id.setting_level3_layout:
			PrioritySettingActivity.setSettings(G.settings.mPrioritySetting[2],
					3);
			startActivity(new Intent(this, PrioritySettingActivity.class));
			break;
		case R.id.setting_level4_layout:
			PrioritySettingActivity.setSettings(G.settings.mPrioritySetting[3],
					4);
			startActivity(new Intent(this, PrioritySettingActivity.class));
			break;
		case R.id.setting_level5_layout:
			PrioritySettingActivity.setSettings(G.settings.mPrioritySetting[4],
					5);
			startActivity(new Intent(this, PrioritySettingActivity.class));
			break;
		case R.id.setting_about_layout:
			break;
		case R.id.setting_exit_layout:
			G.exit();
			break;
		}

	}
}
