package com.pujoy.charminder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Settings extends Activity implements OnClickListener {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

    	TextView tvBubbleTime = (TextView)findViewById(R.id.setting_bubbletime_num);
    	tvBubbleTime.setText(MainActivity.settings.bubbleTimeOut+getString(R.string.second));
    	if(MainActivity.settings.autoDeleteExpiredReminder){
    		ImageView ivAutoDelete = (ImageView)findViewById(R.id.setting_autodelete_tick);
    		ivAutoDelete.setImageResource(R.drawable.switch_1);
    	}
    	
		View.OnTouchListener ItemTouchListener = new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(android.graphics.Color.rgb(32, 170, 170));
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(android.graphics.Color.rgb(228, 242, 254));
					break;
				}
				return false;
    	    }
		};
		
		RelativeLayout rlAutoDelete = (RelativeLayout)findViewById(R.id.setting_autodelete_layout);
		RelativeLayout rlBubbleTime = (RelativeLayout)findViewById(R.id.setting_bubbletime_layout);
		RelativeLayout rlLevel1 = (RelativeLayout)findViewById(R.id.setting_level1_layout);
		RelativeLayout rlLevel2 = (RelativeLayout)findViewById(R.id.setting_level2_layout);
		RelativeLayout rlLevel3 = (RelativeLayout)findViewById(R.id.setting_level3_layout);
		RelativeLayout rlLevel4 = (RelativeLayout)findViewById(R.id.setting_level4_layout);
		RelativeLayout rlLevel5 = (RelativeLayout)findViewById(R.id.setting_level5_layout);
		
		rlAutoDelete.setOnTouchListener(ItemTouchListener);
		rlBubbleTime.setOnTouchListener(ItemTouchListener);
		rlLevel1.setOnTouchListener(ItemTouchListener);
		rlLevel2.setOnTouchListener(ItemTouchListener);
		rlLevel3.setOnTouchListener(ItemTouchListener);
		rlLevel4.setOnTouchListener(ItemTouchListener);
		rlLevel5.setOnTouchListener(ItemTouchListener);
		rlAutoDelete.setOnClickListener(this);
		rlBubbleTime.setOnClickListener(this);
		rlLevel1.setOnClickListener(this);
		rlLevel2.setOnClickListener(this);
		rlLevel3.setOnClickListener(this);
		rlLevel4.setOnClickListener(this);
		rlLevel5.setOnClickListener(this);
		
	}
	
    @Override
    protected void onStop(){
       super.onStop();
       MainActivity.SaveReminders();
    }
	
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.setting_autodelete_layout:
			if(MainActivity.settings.autoDeleteExpiredReminder){
				MainActivity.settings.autoDeleteExpiredReminder = false;
				ImageView ivAutoDelete = (ImageView)findViewById(R.id.setting_autodelete_tick);
				ivAutoDelete.setImageResource(R.drawable.switch_0);
			}else{
				MainActivity.settings.autoDeleteExpiredReminder = true;
				ImageView ivAutoDelete = (ImageView)findViewById(R.id.setting_autodelete_tick);
				ivAutoDelete.setImageResource(R.drawable.switch_1);
			}
			break;
		case R.id.setting_bubbletime_layout:
		    AlertDialog.Builder alert = new AlertDialog.Builder(Settings.this);
		    alert.setTitle(getString(R.string.select_bubbletimeout));
			final NumberPicker picker = new NumberPicker(Settings.this);
			picker.setMinValue(1);
			picker.setMaxValue(30);
			picker.setValue(MainActivity.settings.bubbleTimeOut);
	        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int whichButton) {
	            	MainActivity.settings.bubbleTimeOut = picker.getValue();
	            	TextView tvBubbleTime = (TextView)findViewById(R.id.setting_bubbletime_num);
	            	tvBubbleTime.setText(MainActivity.settings.bubbleTimeOut+getString(R.string.second));

	              }
	            });
	        
	            alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
	              public void onClick(DialogInterface dialog, int whichButton) {
	                // Cancel.
	              }
	            });
	            alert.setView(picker);
	            alert.show();
			break;
		case R.id.setting_level1_layout:
			RemindingLevel.setSettings(MainActivity.settings.levelSetting[0], 1);
			startActivity(new Intent(this, RemindingLevel.class));
			break;
		case R.id.setting_level2_layout:
			RemindingLevel.setSettings(MainActivity.settings.levelSetting[1], 2);
			startActivity(new Intent(this, RemindingLevel.class));
			break;
		case R.id.setting_level3_layout:
			RemindingLevel.setSettings(MainActivity.settings.levelSetting[2], 3);
			startActivity(new Intent(this, RemindingLevel.class));
			break;
		case R.id.setting_level4_layout:
			RemindingLevel.setSettings(MainActivity.settings.levelSetting[3], 4);
			startActivity(new Intent(this, RemindingLevel.class));
			break;
		case R.id.setting_level5_layout:
			RemindingLevel.setSettings(MainActivity.settings.levelSetting[4], 5);
			startActivity(new Intent(this, RemindingLevel.class));
			break;
		}
		
	}

}
