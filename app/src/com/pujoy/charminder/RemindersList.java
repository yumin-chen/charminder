package com.pujoy.charminder;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RemindersList extends Activity implements OnClickListener, OnTouchListener{
	
	public static ArrayList<Reminder> reminderList = new ArrayList<Reminder>();
	private ArrayList<View> itemsList = new ArrayList<View>();
	private ArrayList<Boolean> itemsListExpanded = new ArrayList<Boolean>();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminderslist);
        //Reminder temp = new Reminder(1);
        reminderList.add(new Reminder(1));
        reminderList.add(new Reminder(2));
        reminderList.add(new Reminder(3));
        reminderList.add(new Reminder(2));
        reminderList.add(new Reminder(1));
        LinearLayout lView = (LinearLayout)findViewById(R.id.reminderList_layout);
        for(int i=0; i<reminderList.size(); i++)
        {
        	itemsList.add(getLayoutInflater().inflate(R.layout.reminder_item, lView, false));
        	itemsList.get(i).setId(420+i);
            lView.addView(itemsList.get(i));
        	RelativeLayout rView = (RelativeLayout)findViewById(R.id.reminder_item_layout);
        	rView.setId(520+i);
        	rView.setOnTouchListener(this);
        	rView.setOnClickListener(this);
        	TextView tvCountDown = (TextView)findViewById(R.id.countdown_text);
        	tvCountDown.setText("2014Äê8ÔÂ8ÈÕ");
        }
        ScrollView sView = (ScrollView)findViewById(R.id.reminderList_scroll);
        sView.setOnTouchListener(this);
        sView.setOnTouchListener(this);
	}
	public void onClick(View v) {
		for(int i=0; i<reminderList.size(); i++)
        {
        	if(v.getId() == 520+i)
        	{
        		LinearLayout sView = (LinearLayout)findViewById(420+i);
        		View vItem = getLayoutInflater().inflate(R.layout.reminder_item_expandable, sView, false);
            	vItem.setId(620+i);
                sView.addView(vItem);
        	}
        }
	}
	
	 public boolean onTouch(View v, MotionEvent motionEvent) {
 		RelativeLayout rView;
		 for(int i=0; i<reminderList.size(); i++)
	        {
	        	if(v.getId() == 520+i)
	        	{
	        		switch (motionEvent.getAction())
	    			{
	    			case MotionEvent.ACTION_DOWN:
	    				v.setBackgroundColor(android.graphics.Color.rgb(32, 170, 170));
	    				break;
	    			case MotionEvent.ACTION_UP:
	    				v.setBackgroundColor(android.graphics.Color.rgb(228, 242, 254));
	    				break;
	    			}
	        		System.out.println(motionEvent.getAction());
	    			return false;
	        	}
	        }
		 switch(v.getId()){
		 case R.id.reminderList_scroll:
			 if(motionEvent.getAction() == MotionEvent.ACTION_UP){
				 for(int i=0; i<reminderList.size(); i++)
			        {
					 rView = (RelativeLayout)findViewById(520+i);
					 rView.setBackgroundColor(android.graphics.Color.rgb(228, 242, 254));
			        }
			 }
		 
		 }
		 return false;
	}
	
}
