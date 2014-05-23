package com.pujoy.charminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RemindingLevel extends Activity implements OnClickListener {
	static LevelSetting lSetting;
	static int level;
	
	public static void setSettings(LevelSetting s, int l){
		lSetting = s;
		level = l;
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminding_level);
        
        ImageView ivStar2 = (ImageView)findViewById(R.id.s2);
        ImageView ivStar3 = (ImageView)findViewById(R.id.s3);
        ImageView ivStar4 = (ImageView)findViewById(R.id.s4);
        ImageView ivStar5 = (ImageView)findViewById(R.id.s5);
        if(level < 2)
        	ivStar2.setImageResource(R.drawable.star0);
        if(level < 3)
        	ivStar3.setImageResource(R.drawable.star0);
        if(level < 4)
        	ivStar4.setImageResource(R.drawable.star0);
        if(level < 5)
        	ivStar5.setImageResource(R.drawable.star0);
        
        
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
		
		RelativeLayout rlBubble = (RelativeLayout)findViewById(R.id.level_bubble_layout);
		RelativeLayout rlNotification = (RelativeLayout)findViewById(R.id.level_notification_center_layout);
		RelativeLayout rlWakeScreen = (RelativeLayout)findViewById(R.id.level_wake_screen_layout);
		RelativeLayout rlVibe = (RelativeLayout)findViewById(R.id.level_vibrate_layout);
		RelativeLayout rlSound = (RelativeLayout)findViewById(R.id.level_sound_layout);
		
		rlBubble.setOnTouchListener(ItemTouchListener);
		rlNotification.setOnTouchListener(ItemTouchListener);
		rlWakeScreen.setOnTouchListener(ItemTouchListener);
		rlVibe.setOnTouchListener(ItemTouchListener);
		rlSound.setOnTouchListener(ItemTouchListener);
		rlBubble.setOnClickListener(this);
		rlNotification.setOnClickListener(this);
		rlWakeScreen.setOnClickListener(this);
		rlVibe.setOnClickListener(this);
		rlSound.setOnClickListener(this);
		if(lSetting.bubble){
			ImageView Switch = (ImageView)findViewById(R.id.level_bubble_tick);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if(lSetting.notification){
			ImageView Switch = (ImageView)findViewById(R.id.level_notification_center_tick);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if(lSetting.wakeScreen){
			ImageView Switch = (ImageView)findViewById(R.id.level_wake_screen_tick);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if(lSetting.vibrate){
			ImageView Switch = (ImageView)findViewById(R.id.level_vibrate_tick);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if(lSetting.sound){
			ImageView Switch = (ImageView)findViewById(R.id.level_sound_tick);
			Switch.setImageResource(R.drawable.switch_1);
		}
		
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.level_bubble_layout:
			if(lSetting.bubble){
				lSetting.bubble = false;
				ImageView Switch = (ImageView)findViewById(R.id.level_bubble_tick);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				lSetting.bubble = true;
				ImageView Switch = (ImageView)findViewById(R.id.level_bubble_tick);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		case R.id.level_notification_center_layout:
			if(lSetting.notification){
				lSetting.notification = false;
				ImageView Switch = (ImageView)findViewById(R.id.level_notification_center_tick);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				lSetting.notification = true;
				ImageView Switch = (ImageView)findViewById(R.id.level_notification_center_tick);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		case R.id.level_wake_screen_layout:
			if(lSetting.wakeScreen){
				lSetting.wakeScreen = false;
				ImageView Switch = (ImageView)findViewById(R.id.level_wake_screen_tick);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				lSetting.wakeScreen = true;
				ImageView Switch = (ImageView)findViewById(R.id.level_wake_screen_tick);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		case R.id.level_vibrate_layout:
			if(lSetting.vibrate){
				lSetting.vibrate = false;
				ImageView Switch = (ImageView)findViewById(R.id.level_vibrate_tick);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				lSetting.vibrate = true;
				ImageView Switch = (ImageView)findViewById(R.id.level_vibrate_tick);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		case R.id.level_sound_layout:
			if(lSetting.sound){
				lSetting.sound = false;
				ImageView Switch = (ImageView)findViewById(R.id.level_sound_tick);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				lSetting.sound = true;
				ImageView Switch = (ImageView)findViewById(R.id.level_sound_tick);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		}
		
	}
}
