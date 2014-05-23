package com.pujoy.charminder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class Settings extends Activity implements OnClickListener {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
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
	
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.setting_autodelete_layout:
			break;
		case R.id.setting_bubbletime_layout:
			break;
		case R.id.setting_level1_layout:
			startActivity(new Intent(getApplicationContext(), RemindingLevel.class));
			break;
		case R.id.setting_level2_layout:
			startActivity(new Intent(this, RemindingLevel.class));
			break;
		case R.id.setting_level3_layout:
			startActivity(new Intent(this, RemindingLevel.class));
			break;
		case R.id.setting_level4_layout:
			startActivity(new Intent(this, RemindingLevel.class));
			break;
		case R.id.setting_level5_layout:
			startActivity(new Intent(this, RemindingLevel.class));
			break;
		}
		
	}

}
