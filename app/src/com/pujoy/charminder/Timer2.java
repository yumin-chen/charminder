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

public class Timer2 extends Activity {
	ArrayList<Integer> TimeDigits = new ArrayList<Integer>();
	static TextView[] TimerDisplay = new TextView[9];
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer2);
        
        
        View.OnClickListener numKeyListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView tvKey = (TextView)v;
				int intToBeAdded = Integer.parseInt(tvKey.getText().toString());
				if(TimeDigits.size()< 9)
				{
					switch (TimeDigits.size()){
					case 1:
						if(intToBeAdded>1)
							TimeDigits.add(0);
						TimeDigits.add(intToBeAdded);
						break;
					case 2:
						if(intToBeAdded == 0 && TimeDigits.get(1) == 0)
							break;
						if(intToBeAdded >=3 && TimeDigits.get(1) == 1)
							break;
						TimeDigits.add(intToBeAdded);
						break;
					case 3:
						if(intToBeAdded>3)
							TimeDigits.add(0);
						TimeDigits.add(intToBeAdded);
						break;
					case 4:
						if(intToBeAdded == 0 && TimeDigits.get(3) == 0)
							break;
						if(!((TimeDigits.get(3) >=3) && intToBeAdded>1))
							TimeDigits.add(intToBeAdded);
						break;
					case 5:
						if(intToBeAdded>2){
							TimeDigits.add(0);
							TimeDigits.add(intToBeAdded);
							TextView amOrPm = (TextView)findViewById(R.id.timer2_amorpm);
							amOrPm.setText(R.string.am);
						}else{
							TimeDigits.add(intToBeAdded);
						}

						break;
					case 6:
						if(TimeDigits.get(5) >=2 && intToBeAdded == 4){
							TimeDigits.set(5, 0);
							TimeDigits.add(0);
						}else if(!((TimeDigits.get(5) >=2) && intToBeAdded>3)){
							TimeDigits.add(intToBeAdded);
						}else{
							break;
						}
						TextView amOrPm = (TextView)findViewById(R.id.timer2_amorpm);
						if((TimeDigits.get(5) * 10 + intToBeAdded) < 12){
							amOrPm.setText(R.string.am);
						}else{
							amOrPm.setText(R.string.pm);
						}
						
						break;
					case 7:
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

		TimerDisplay[8] = (TextView)findViewById(R.id.timer2_display_minute2);
		TimerDisplay[7] = (TextView)findViewById(R.id.timer2_display_minute1);
		TimerDisplay[6] = (TextView)findViewById(R.id.timer2_display_hour2);
		TimerDisplay[5] = (TextView)findViewById(R.id.timer2_display_hour1);
		TimerDisplay[4] = (TextView)findViewById(R.id.timer2_display_day2);
		TimerDisplay[3] = (TextView)findViewById(R.id.timer2_display_day1);
		TimerDisplay[2] = (TextView)findViewById(R.id.timer2_display_month2);
		TimerDisplay[1] = (TextView)findViewById(R.id.timer2_display_month1);
		TimerDisplay[0] = (TextView)findViewById(R.id.timer2_display_year);
		
        TextView tvKey1 = (TextView)findViewById(R.id.timer2_key1);
        tvKey1.setOnClickListener(numKeyListener);
        tvKey1.setOnTouchListener(numKeyTouchListener);
        TextView tvKey2 = (TextView)findViewById(R.id.timer2_key2);
        tvKey2.setOnClickListener(numKeyListener);
        tvKey2.setOnTouchListener(numKeyTouchListener);
        TextView tvKey3 = (TextView)findViewById(R.id.timer2_key3);
        tvKey3.setOnClickListener(numKeyListener);
        tvKey3.setOnTouchListener(numKeyTouchListener);
        TextView tvKey4 = (TextView)findViewById(R.id.timer2_key4);
        tvKey4.setOnClickListener(numKeyListener);
        tvKey4.setOnTouchListener(numKeyTouchListener);
        TextView tvKey5 = (TextView)findViewById(R.id.timer2_key5);
        tvKey5.setOnClickListener(numKeyListener);
        tvKey5.setOnTouchListener(numKeyTouchListener);
        TextView tvKey6 = (TextView)findViewById(R.id.timer2_key6);
        tvKey6.setOnClickListener(numKeyListener);
        tvKey6.setOnTouchListener(numKeyTouchListener);
        TextView tvKey7 = (TextView)findViewById(R.id.timer2_key7);
        tvKey7.setOnClickListener(numKeyListener);
        tvKey7.setOnTouchListener(numKeyTouchListener);
        TextView tvKey8 = (TextView)findViewById(R.id.timer2_key8);
        tvKey8.setOnClickListener(numKeyListener);
        tvKey8.setOnTouchListener(numKeyTouchListener);
        TextView tvKey9 = (TextView)findViewById(R.id.timer2_key9);
        tvKey9.setOnClickListener(numKeyListener);
        tvKey9.setOnTouchListener(numKeyTouchListener);
        TextView tvKey0 = (TextView)findViewById(R.id.timer2_key0);
        tvKey0.setOnClickListener(numKeyListener);
        tvKey0.setOnTouchListener(numKeyTouchListener);
        
        final ImageView ivBackSpace = (ImageView)findViewById(R.id.timer2_backspace);
        ivBackSpace.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(TimeDigits.size()>=1)
				{
				TimeDigits.remove(TimeDigits.size()-1);
				UpdateTimerDisplay();
				if (TimeDigits.size() == 6){
					TextView amOrPm = (TextView)findViewById(R.id.timer2_amorpm);
					amOrPm.setText("");
				}
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
        
        TextView tvKeyStart = (TextView)findViewById(R.id.timer2_start);
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
				if (TimeDigits.size()<9)
				{
					MainActivity.PushFloatingBubble(getString(R.string.bubble_info_needed));
				}
				else{
					try {
						Reminder newReminder = new Reminder(2);
						String sTimeToRemind = "1"+ TimeDigits.get(0).toString() + ":" + 
						TimeDigits.get(1).toString() + TimeDigits.get(2).toString() + ":" +
						TimeDigits.get(3).toString() + TimeDigits.get(4).toString() + ":" +
						TimeDigits.get(5).toString() + TimeDigits.get(6).toString() + ":" +
						TimeDigits.get(7).toString() + TimeDigits.get(8).toString();
						System.out.println(sTimeToRemind);
						SimpleDateFormat df = new SimpleDateFormat("yy:MM:dd:HH:mm");
						Date d = df.parse(sTimeToRemind);
						newReminder.time_to_remind = Calendar.getInstance();
						newReminder.time_to_remind.setTime(d);
						newReminder.note = FormatTimeText();
						MainActivity.AddReminder(newReminder);
						MainActivity.PushFloatingBubble(getString(R.string.bubble_add_reminder) +
								newReminder.note + getString(R.string.bubble_timer2));
					} catch (ParseException e) {
						e.printStackTrace();
						MainActivity.PushFloatingBubble(getString(R.string.bubble_error));
					} 
					
				}
			}
		});
	}
	
	private String FormatTimeText(){
		String ret = "";
		if(TimeDigits.get(1)!=0){
			ret += TimeDigits.get(1).toString();
		}
		ret += TimeDigits.get(2).toString() + getString(R.string.month);
		if(TimeDigits.get(3)!=0){
			ret += TimeDigits.get(3).toString();
		}
		ret += TimeDigits.get(4).toString() + getString(R.string.day);
		if(TimeDigits.get(5)!=0){
			ret += TimeDigits.get(5).toString();
		}
		ret += TimeDigits.get(6).toString() + getString(R.string.hour);
		if(!(TimeDigits.get(7)==0 && TimeDigits.get(8)==0) || ret == ""){
			ret += TimeDigits.get(7).toString() + TimeDigits.get(8).toString() + getString(R.string.minute);
		}
		return ret;
		
	}
	
	private void UpdateTimerDisplay(){
		for(int i=0; i<9; i++){
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
