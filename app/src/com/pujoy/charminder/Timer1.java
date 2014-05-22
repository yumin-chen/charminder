package com.pujoy.charminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Timer1 extends Activity {
	ArrayList<Integer> TimeDigits = new ArrayList<Integer>();
	static TextView[] TimerDisplay = new TextView[5];
	static ImageView[] ivLevelIcon = new ImageView[5];
	static int level = 1;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer1);
        
        
        View.OnClickListener numKeyListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tvKey = (TextView)v;
				int intToBeAdded = Integer.parseInt(tvKey.getText().toString());
				if(TimeDigits.size()< 5)
				{
					switch (TimeDigits.size()){
					case 1:
						if(intToBeAdded>5)
							TimeDigits.add(0);
						TimeDigits.add(intToBeAdded);
						break;
					case 3:
						if(intToBeAdded>5)
							TimeDigits.add(0);
						TimeDigits.add(intToBeAdded);
						break;
					default:
						TimeDigits.add(intToBeAdded);
						break;
					}
				UpdateTimerDisplay();
				}
				
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
		TimerDisplay[4] = (TextView)findViewById(R.id.timer1_display_second2);
		TimerDisplay[3] = (TextView)findViewById(R.id.timer1_display_second1);
		TimerDisplay[2] = (TextView)findViewById(R.id.timer1_display_minute2);
		TimerDisplay[1] = (TextView)findViewById(R.id.timer1_display_minute1);
		TimerDisplay[0] = (TextView)findViewById(R.id.timer1_display_hour);
		
		ivLevelIcon[0] = (ImageView)findViewById(R.id.icon_level_star1);
		ivLevelIcon[1] = (ImageView)findViewById(R.id.icon_level_star2);
		ivLevelIcon[2] = (ImageView)findViewById(R.id.icon_level_star3);
		ivLevelIcon[3] = (ImageView)findViewById(R.id.icon_level_star4);
		ivLevelIcon[4] = (ImageView)findViewById(R.id.icon_level_star5);
		
        TextView tvKey1 = (TextView)findViewById(R.id.timer1_key1);
        tvKey1.setOnClickListener(numKeyListener);
        tvKey1.setOnTouchListener(numKeyTouchListener);
        TextView tvKey2 = (TextView)findViewById(R.id.timer1_key2);
        tvKey2.setOnClickListener(numKeyListener);
        tvKey2.setOnTouchListener(numKeyTouchListener);
        TextView tvKey3 = (TextView)findViewById(R.id.timer1_key3);
        tvKey3.setOnClickListener(numKeyListener);
        tvKey3.setOnTouchListener(numKeyTouchListener);
        TextView tvKey4 = (TextView)findViewById(R.id.timer1_key4);
        tvKey4.setOnClickListener(numKeyListener);
        tvKey4.setOnTouchListener(numKeyTouchListener);
        TextView tvKey5 = (TextView)findViewById(R.id.timer1_key5);
        tvKey5.setOnClickListener(numKeyListener);
        tvKey5.setOnTouchListener(numKeyTouchListener);
        TextView tvKey6 = (TextView)findViewById(R.id.timer1_key6);
        tvKey6.setOnClickListener(numKeyListener);
        tvKey6.setOnTouchListener(numKeyTouchListener);
        TextView tvKey7 = (TextView)findViewById(R.id.timer1_key7);
        tvKey7.setOnClickListener(numKeyListener);
        tvKey7.setOnTouchListener(numKeyTouchListener);
        TextView tvKey8 = (TextView)findViewById(R.id.timer1_key8);
        tvKey8.setOnClickListener(numKeyListener);
        tvKey8.setOnTouchListener(numKeyTouchListener);
        TextView tvKey9 = (TextView)findViewById(R.id.timer1_key9);
        tvKey9.setOnClickListener(numKeyListener);
        tvKey9.setOnTouchListener(numKeyTouchListener);
        TextView tvKey0 = (TextView)findViewById(R.id.timer1_key0);
        tvKey0.setOnClickListener(numKeyListener);
        tvKey0.setOnTouchListener(numKeyTouchListener);
        
        final ImageView ivBackSpace = (ImageView)findViewById(R.id.timer1_backspace);
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
        
        TextView tvKeyStart = (TextView)findViewById(R.id.timer1_start);
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
				if (TimeDigits.size()<5)
				{
					MainActivity.PushFloatingBubble(getString(R.string.bubble_info_needed));
				}
				else{
					try {
						Reminder newReminder = new Reminder(1);
						String sTimeToRemind = TimeDigits.get(0).toString() + ":" + 
						TimeDigits.get(1).toString() + TimeDigits.get(2).toString() + ":" +
						TimeDigits.get(3).toString() + TimeDigits.get(4).toString();
						SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
						Date d = df.parse(sTimeToRemind);
						Calendar cal =Calendar.getInstance();
						cal.setTime(d);
						newReminder.time_to_remind = Calendar.getInstance();
						newReminder.time_to_remind.add(Calendar.SECOND, cal.get(Calendar.SECOND));
						newReminder.time_to_remind.add(Calendar.MINUTE, cal.get(Calendar.MINUTE));
						newReminder.time_to_remind.add(Calendar.HOUR, cal.get(Calendar.HOUR));
						newReminder.level = level;
						newReminder.note = FormatTimeText();
						MainActivity.AddReminder(newReminder);
						MainActivity.PushFloatingBubble(getString(R.string.bubble_add_reminder) +
						newReminder.note + getString(R.string.bubble_timer1));
					} catch (ParseException e) {
						e.printStackTrace();
						MainActivity.PushFloatingBubble(getString(R.string.bubble_error));
					} 
					
				}
			}
		});
        
        
        
        View.OnClickListener levelListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch (v.getId()){
				case R.id.icon_level_star1:
					UpdateLevel(1);
					break;
				case R.id.icon_level_star2:
					UpdateLevel(2);
					break;
				case R.id.icon_level_star3:
					UpdateLevel(3);
					break;
				case R.id.icon_level_star4:
					UpdateLevel(4);
					break;
				case R.id.icon_level_star5:
					UpdateLevel(5);
					break;
				}
				
			}
		};
		
		for(int i=0; i<5; i++){
			ivLevelIcon[i].setOnClickListener(levelListener);
		}
		
		UpdateLevel(1);
		
	}
	private String FormatTimeText(){
		String ret = "";
		if(TimeDigits.get(0)!=0){
			ret += TimeDigits.get(0).toString() + getString(R.string.hour);
		}
		if(!(TimeDigits.get(1)==0 && TimeDigits.get(2)==0)){
			ret += TimeDigits.get(1).toString() + TimeDigits.get(2).toString() + getString(R.string.minute);
		}
		if(!(TimeDigits.get(3)==0 && TimeDigits.get(4)==0) || ret == ""){
			ret += TimeDigits.get(3).toString() + TimeDigits.get(4).toString() + getString(R.string.second);
		}
		return ret;
		
	}
	private void UpdateTimerDisplay(){
		for(int i=0; i<5; i++){
            if(i>=TimeDigits.size())
            {
            	TimerDisplay[i].setText(R.string.unknown_digit);
            }
            else{
            	TimerDisplay[i].setText("" + TimeDigits.get(i));
            }
       }
	}
	private void UpdateLevel(int newlevel){
		level = newlevel;
		for(int i=0; i<5; i++){
			if(i<level){
				ivLevelIcon[i].setImageResource(R.drawable.star1);
			}else{
				ivLevelIcon[i].setImageResource(R.drawable.star0);
			}
			
		}
	}

}
