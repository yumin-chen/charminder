package com.pujoy.charminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Timer3 extends Activity {
	ArrayList<Integer> TimeDigits = new ArrayList<Integer>();
	static TextView[] TimerDisplay = new TextView[2];
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer3);
        
        View.OnClickListener numKeyListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tvKey = (TextView)v;
				if(TimeDigits.size()>=2)
					TimeDigits.clear();
				int i = Integer.parseInt(tvKey.getText().toString());
				if(TimeDigits.size() == 0 && i>5)
					TimeDigits.add(0);
				TimeDigits.add(i);
				UpdateTimerDisplay();
			}
		};
		
		View.OnTouchListener numKeyTouchListener = new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(android.graphics.Color.rgb(228, 242, 254));
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(android.graphics.Color.rgb(168, 212, 242));
					break;
				}
				return false;
    	    }
		};

		TimerDisplay[1] = (TextView)findViewById(R.id.timer3_display_minute2);
		TimerDisplay[0] = (TextView)findViewById(R.id.timer3_display_minute1);

		
        TextView tvKey1 = (TextView)findViewById(R.id.timer3_key1);
        tvKey1.setOnClickListener(numKeyListener);
        tvKey1.setOnTouchListener(numKeyTouchListener);
        TextView tvKey2 = (TextView)findViewById(R.id.timer3_key2);
        tvKey2.setOnClickListener(numKeyListener);
        tvKey2.setOnTouchListener(numKeyTouchListener);
        TextView tvKey3 = (TextView)findViewById(R.id.timer3_key3);
        tvKey3.setOnClickListener(numKeyListener);
        tvKey3.setOnTouchListener(numKeyTouchListener);
        TextView tvKey4 = (TextView)findViewById(R.id.timer3_key4);
        tvKey4.setOnClickListener(numKeyListener);
        tvKey4.setOnTouchListener(numKeyTouchListener);
        TextView tvKey5 = (TextView)findViewById(R.id.timer3_key5);
        tvKey5.setOnClickListener(numKeyListener);
        tvKey5.setOnTouchListener(numKeyTouchListener);
        TextView tvKey6 = (TextView)findViewById(R.id.timer3_key6);
        tvKey6.setOnClickListener(numKeyListener);
        tvKey6.setOnTouchListener(numKeyTouchListener);
        TextView tvKey7 = (TextView)findViewById(R.id.timer3_key7);
        tvKey7.setOnClickListener(numKeyListener);
        tvKey7.setOnTouchListener(numKeyTouchListener);
        TextView tvKey8 = (TextView)findViewById(R.id.timer3_key8);
        tvKey8.setOnClickListener(numKeyListener);
        tvKey8.setOnTouchListener(numKeyTouchListener);
        TextView tvKey9 = (TextView)findViewById(R.id.timer3_key9);
        tvKey9.setOnClickListener(numKeyListener);
        tvKey9.setOnTouchListener(numKeyTouchListener);
        TextView tvKey0 = (TextView)findViewById(R.id.timer3_key0);
        tvKey0.setOnClickListener(numKeyListener);
        tvKey0.setOnTouchListener(numKeyTouchListener);
        
        final ImageView ivBackSpace = (ImageView)findViewById(R.id.timer3_backspace);
        ivBackSpace.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(TimeDigits.size()>=1)
				{
				TimeDigits.remove(TimeDigits.size()-1);
				UpdateTimerDisplay();
				}
			}
		});
        
        ivBackSpace.setOnTouchListener(new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					ivBackSpace.setImageResource(R.drawable.backspace_d);
					break;
				case MotionEvent.ACTION_UP:
					ivBackSpace.setImageResource(R.drawable.backspace);
					break;
				}
				return false;
    	    }
		});
        
        TextView tvKeyStart = (TextView)findViewById(R.id.timer3_start);
        tvKeyStart.setOnTouchListener(new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(android.graphics.Color.rgb(168, 212, 242));
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(android.graphics.Color.rgb(48, 78, 98));
					break;
				}
				return false;
    	    }
		});
        
        tvKeyStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (TimeDigits.size()<2)
				{
					MainActivity.PushFloatingBubble(getString(R.string.bubble_info_needed));
				}
				else{
						Reminder newReminder = new Reminder(3);
						newReminder.time_to_remind = Calendar.getInstance();
						newReminder.time_to_remind.set(Calendar.MINUTE, TimeDigits.get(0)*10 + TimeDigits.get(1));
						newReminder.time_to_remind.set(Calendar.SECOND, 0);
						newReminder.note=TimeDigits.get(0).toString()+TimeDigits.get(1).toString();
						if(newReminder.time_to_remind.compareTo(Calendar.getInstance()) <=0) 
							newReminder.time_to_remind.add(Calendar.HOUR, 1);
						MainActivity.AddReminder(newReminder);
						MainActivity.PushFloatingBubble(getString(R.string.bubble_add_reminder) +
								getString(R.string.bubble_timer3) + TimeDigits.get(0)
								+TimeDigits.get(1) + getString(R.string.minute) + getString(R.string.bubble_timer2));
						
					
				}
			}
		});
        

        TimeDigits.add(0);
        TimeDigits.add(0);
        UpdateTimerDisplay();
	}
	
	private void UpdateTimerDisplay(){
		for(int i=0; i<2; i++){
            if(i>=TimeDigits.size())
            {
            	TimerDisplay[i].setText(R.string.unknown_digit);
            }
            else{
            	TimerDisplay[i].setText("" + TimeDigits.get(i));
            }
       }
	}

}
