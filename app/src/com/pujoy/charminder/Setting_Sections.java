package com.pujoy.charminder;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Setting_Sections extends Activity implements OnClickListener {
	public static boolean[] sectionSetting;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_setting);
        
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
		
		RelativeLayout[] rlList = new RelativeLayout[6];
		rlList[0] = (RelativeLayout)findViewById(R.id.setting_reminderslist_layout);
		rlList[1] = (RelativeLayout)findViewById(R.id.setting_settings_layout);
		rlList[2] = (RelativeLayout)findViewById(R.id.setting_timer1_layout);
		rlList[3] = (RelativeLayout)findViewById(R.id.setting_timer2_layout);
		rlList[4] = (RelativeLayout)findViewById(R.id.setting_timer3_layout);
		rlList[5] = (RelativeLayout)findViewById(R.id.setting_timer4_layout);
	
		for(int i=0; i<6; i++){
			rlList[i].setOnTouchListener(ItemTouchListener);
			rlList[i].setOnClickListener(this);
		}
		

		if(sectionSetting[0]){
			ImageView Switch = (ImageView)findViewById(R.id.switch_reminderlist);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if(sectionSetting[1]){
			ImageView Switch = (ImageView)findViewById(R.id.switch_settings);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if(sectionSetting[2]){
			ImageView Switch = (ImageView)findViewById(R.id.switch_timer1);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if(sectionSetting[3]){
			ImageView Switch = (ImageView)findViewById(R.id.switch_timer2);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if(sectionSetting[4]){
			ImageView Switch = (ImageView)findViewById(R.id.switch_timer3);
			Switch.setImageResource(R.drawable.switch_1);
		}
		if(sectionSetting[5]){
			ImageView Switch = (ImageView)findViewById(R.id.switch_timer4);
			Switch.setImageResource(R.drawable.switch_1);
		}
		
	}
	
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.setting_reminderslist_layout:
			if(sectionSetting[0]){
				sectionSetting[0] = false;
				ImageView Switch = (ImageView)findViewById(R.id.switch_reminderlist);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				sectionSetting[0] = true;
				ImageView Switch = (ImageView)findViewById(R.id.switch_reminderlist);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		case R.id.setting_settings_layout:
			if(sectionSetting[1]){
				sectionSetting[1] = false;
				ImageView Switch = (ImageView)findViewById(R.id.switch_settings);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				sectionSetting[1] = true;
				ImageView Switch = (ImageView)findViewById(R.id.switch_settings);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		case R.id.setting_timer1_layout:
			if(sectionSetting[2]){
				sectionSetting[2] = false;
				ImageView Switch = (ImageView)findViewById(R.id.switch_timer1);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				sectionSetting[2] = true;
				ImageView Switch = (ImageView)findViewById(R.id.switch_timer1);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		case R.id.setting_timer2_layout:
			if(sectionSetting[3]){
				sectionSetting[3] = false;
				ImageView Switch = (ImageView)findViewById(R.id.switch_timer2);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				sectionSetting[3] = true;
				ImageView Switch = (ImageView)findViewById(R.id.switch_timer2);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		case R.id.setting_timer3_layout:
			if(sectionSetting[4]){
				sectionSetting[4] = false;
				ImageView Switch = (ImageView)findViewById(R.id.switch_timer3);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				sectionSetting[4] = true;
				ImageView Switch = (ImageView)findViewById(R.id.switch_timer3);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		case R.id.setting_timer4_layout:
			if(sectionSetting[5]){
				sectionSetting[5] = false;
				ImageView Switch = (ImageView)findViewById(R.id.switch_timer4);
				Switch.setImageResource(R.drawable.switch_0);
			}else{
				sectionSetting[5] = true;
				ImageView Switch = (ImageView)findViewById(R.id.switch_timer4);
				Switch.setImageResource(R.drawable.switch_1);
			}
			break;
		
		}
		
	}
	
}
