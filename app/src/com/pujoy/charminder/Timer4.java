package com.pujoy.charminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

public class Timer4 extends Activity {

	static ImageView[] ivLevelIcon = new ImageView[5];
	static Calendar remindingTime = Calendar.getInstance();
	static Calendar countdownTime = Calendar.getInstance();
	static TextView tvDate;
	static TextView tvCountdownTime;
	static TextView tvTime;
	static TextView tvRepeat;
	static EditText etTitle;
	static EditText etLocation;
	static EditText etContent;
	static RelativeLayout rlTimeView;
	static RelativeLayout rlTab1Content;
	static LinearLayout llTab2Content;
	static int currentTab = 2;
	static int repeat = 0;
	static int level = 3;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mainView = getLayoutInflater().inflate(R.layout.timer4, null);
        mainView.post(new Runnable() 
        {
            @Override
            public void run() 
            {     
            	LinearLayout ll = (LinearLayout)findViewById(R.id.timer4_layout);
            	ScrollView sv = (ScrollView)findViewById(R.id.timer4_scroll);
            	if(ll.getHeight() < sv.getHeight())
                {
                	TextView tvContent = (TextView)findViewById(R.id.text_content);
                	tvContent.setHeight((int) (MainActivity.dpToPx(40) + sv.getHeight() - ll.getHeight()));
                } 
            }
        } );

        setContentView(mainView);   
        
        remindingTime = Calendar.getInstance();
        countdownTime.set(Calendar.HOUR_OF_DAY, 0);
        countdownTime.set(Calendar.MINUTE, 0);
        
        tvDate = (TextView)findViewById(R.id.text_date);
        tvDate.setText(getString(R.string.date_) + FormatDate(remindingTime));
        tvTime = (TextView)findViewById(R.id.text_time);
        tvTime.setText(getString(R.string.time_) + FormatTime(remindingTime));
        tvRepeat = (TextView)findViewById(R.id.text_repeat);
        tvRepeat.requestFocus();
        
        
        ivLevelIcon[0] = (ImageView)findViewById(R.id.icon_level_star1);
		ivLevelIcon[1] = (ImageView)findViewById(R.id.icon_level_star2);
		ivLevelIcon[2] = (ImageView)findViewById(R.id.icon_level_star3);
		ivLevelIcon[3] = (ImageView)findViewById(R.id.icon_level_star4);
		ivLevelIcon[4] = (ImageView)findViewById(R.id.icon_level_star5);
		
		etTitle = (EditText)findViewById(R.id.edit_title);
		etLocation = (EditText)findViewById(R.id.edit_location);
		etContent = (EditText)findViewById(R.id.edit_content);
		
		
		
		
		
		RelativeLayout rlDate = (RelativeLayout)findViewById(R.id.date_layout);
		RelativeLayout rlTime = (RelativeLayout)findViewById(R.id.time_layout);
		RelativeLayout rlRepeat = (RelativeLayout)findViewById(R.id.repeat_layout);
		
		
		View.OnTouchListener rlListener = (new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					if(v.isClickable())
					v.setBackgroundColor(android.graphics.Color.rgb(60, 148, 148));
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(android.graphics.Color.rgb(24, 128, 128));
					break;
				}
				return false;
    	    }
		});
		
		rlDate.setOnTouchListener(rlListener);
		rlTime.setOnTouchListener(rlListener);
		rlRepeat.setOnTouchListener(rlListener);
		
		
		rlDate.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
					    @Override
					    public void onDateSet(DatePicker view, int year, int monthOfYear,
					            int dayOfMonth) {
					        // TODO Auto-generated method stub
					        remindingTime.set(Calendar.YEAR, year);
					        remindingTime.set(Calendar.MONTH, monthOfYear);
					        remindingTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
					        tvDate.setText(getString(R.string.date_) + FormatDate(remindingTime));
					    }

					};

				    new DatePickerDialog(Timer4.this, dateListener, remindingTime
		                    .get(Calendar.YEAR), remindingTime.get(Calendar.MONTH),
		                    remindingTime.get(Calendar.DAY_OF_MONTH)).show();
				}
		});
		rlTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TimePickerDialog.OnTimeSetListener timePickerListener = 
			            new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker view, int selectedHour,
							int selectedMinute) {
						remindingTime.set(Calendar.HOUR_OF_DAY, selectedHour);
						remindingTime.set(Calendar.MINUTE, selectedMinute);
				        tvTime.setText(getString(R.string.time_) + FormatTime(remindingTime));
					}
				};

			    new TimePickerDialog(Timer4.this, timePickerListener, remindingTime
	                    .get(Calendar.HOUR_OF_DAY), remindingTime.get(Calendar.MINUTE), true).show();
			}
		});
		rlRepeat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			    AlertDialog.Builder alert = new AlertDialog.Builder(Timer4.this);
			    alert.setTitle(getString(R.string.select_repeat));
				final NumberPicker picker = new NumberPicker(Timer4.this);
				picker.setMinValue(0);
				picker.setMaxValue(4);
				picker.setDisplayedValues( new String[] { getString(R.string.repeat_once),
						getString(R.string.repeat_dayly), getString(R.string.repeat_weekly),
						getString(R.string.repeat_monthly), getString(R.string.repeat_yearly)} );

		        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int whichButton) {
		              UpdateRepeat(picker.getValue());
		              }
		            });
		        
		            alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
		              public void onClick(DialogInterface dialog, int whichButton) {
		                // Cancel.
		              }
		            });
		            alert.setView(picker);
		            alert.show();
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
		
		
		final LinearLayout llTab1 = (LinearLayout)findViewById(R.id.timer4_countdown_tab);
		final LinearLayout llTab2 = (LinearLayout)findViewById(R.id.timer4_specified_time_tab);
		

		rlTimeView = (RelativeLayout)findViewById(R.id.timer4_time);
		rlTab1Content = (RelativeLayout)getLayoutInflater().inflate(R.layout.countdown_event, rlTimeView, false);
		llTab2Content = (LinearLayout)findViewById(R.id.timer4_tab2);
		
		llTab2.setOnTouchListener(rlListener);
		llTab2.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				currentTab = 2;
				llTab1.setClickable(true);
				llTab2.setClickable(false);
				rlTimeView.removeView(rlTab1Content);
				rlTimeView.addView(llTab2Content);
			}
		});
		llTab2.setClickable(false);
		View.OnTouchListener BlueListener = new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					if(v.isClickable())
					v.setBackgroundColor(android.graphics.Color.rgb(80, 106, 124));
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(android.graphics.Color.rgb(48, 78, 98));
					break;
				}
				return false;
    	    }
		};
		llTab1.setOnTouchListener(BlueListener);
		llTab1.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View v) {
				currentTab = 1;
				llTab1.setClickable(false);
				llTab2.setClickable(true);
				rlTimeView.removeView(llTab2Content);
				rlTimeView.addView(rlTab1Content);
				tvCountdownTime = (TextView)findViewById(R.id.text_countdowntime);
		        tvCountdownTime.setText(getString(R.string.countdowntime_) + FormatTime(countdownTime));
			}
		});
		llTab1.setClickable(true);
		currentTab = 2;
		rlTab1Content.setOnTouchListener(BlueListener);
		rlTab1Content.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TimePickerDialog.OnTimeSetListener timePickerListener = 
			            new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker view, int selectedHour,
							int selectedMinute) {
						countdownTime.set(Calendar.HOUR_OF_DAY, selectedHour);
						countdownTime.set(Calendar.MINUTE, selectedMinute);
				        tvCountdownTime.setText(getString(R.string.countdowntime_) + FormatTime(countdownTime));
					}
				};

			    new TimePickerDialog(Timer4.this, timePickerListener, countdownTime
	                    .get(Calendar.HOUR_OF_DAY), countdownTime.get(Calendar.MINUTE), true).show();
			}
		});
		
		TextView tvKeyStart = (TextView)findViewById(R.id.timer4_start);
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
						Reminder newReminder = new Reminder(4);
						EditText etTitle = (EditText)findViewById(R.id.edit_title);
						newReminder.title = etTitle.getText().toString();
						newReminder.title = newReminder.title == ""? getString(R.string.title_timer4): newReminder.title;
						EditText etLocation = (EditText)findViewById(R.id.edit_location);
						newReminder.location = etLocation.getText().toString();
						EditText etContent = (EditText)findViewById(R.id.edit_content);
						newReminder.note = etContent.getText().toString();
						newReminder.level = level;
						if(currentTab == 1){
							newReminder.time_to_remind = Calendar.getInstance();
							newReminder.repeat = -1;
							newReminder.time_to_remind.add(Calendar.MINUTE, countdownTime.get(Calendar.MINUTE));
							newReminder.time_to_remind.add(Calendar.HOUR_OF_DAY, countdownTime.get(Calendar.HOUR_OF_DAY));
							MainActivity.PushFloatingBubble(getString(R.string.bubble_add_reminder) +
									FormatTime(countdownTime) + getString(R.string.bubble_timer4_tab1)
									+ newReminder.title + getString(R.string.bubble_timer4_end));
						}else{
							newReminder.time_to_remind = remindingTime;
							newReminder.repeat = repeat;
							MainActivity.PushFloatingBubble(getString(R.string.bubble_add_reminder) +
									FormatDate(remindingTime) + FormatTime(remindingTime) + getString(R.string.bubble_timer4_tab2)
									+ newReminder.title + getString(R.string.bubble_timer4_end));
						}
						MainActivity.AddReminder(newReminder);
						
			}
		});
        
		
		
		UpdateLevel(3);
		UpdateRepeat(0);
		
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
	
	private void UpdateRepeat(int newRepeat){
		repeat = newRepeat;
		String s="";
		switch(repeat){
		case 0:
			s = getString(R.string.repeat_once);
			break;
		case 1:
			s = getString(R.string.repeat_dayly);
			break;
		case 2:
			s = getString(R.string.repeat_weekly);
			break;
		case 3:
			s = getString(R.string.repeat_monthly);
			break;
		case 4:
			s = getString(R.string.repeat_yearly);
			break;
		}
		tvRepeat.setText(getString(R.string.repeat_) + s);
	}

	 private String FormatDate(Calendar t){
		 String s="";
			 s=s+t.get(Calendar.YEAR)+getString(R.string.year);
			 s=s+(t.get(Calendar.MONTH)+1)+getString(R.string.month);
			 s=s+t.get(Calendar.DAY_OF_MONTH)+getString(R.string.day);
		 return s;
	 } 
	 private String FormatTime(Calendar t){
		 String s="";
			 s=s+t.get(Calendar.HOUR_OF_DAY)+getString(R.string.hour);
			 s=s+t.get(Calendar.MINUTE)+getString(R.string.minute);
		 return s;
	 } 
	 
}
