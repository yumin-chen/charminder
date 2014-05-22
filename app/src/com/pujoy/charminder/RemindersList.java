package com.pujoy.charminder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

	private ArrayList<View> itemsList = new ArrayList<View>();
	private ArrayList<Boolean> itemsListExpanded = new ArrayList<Boolean>();
	private static boolean isInFront = false;
	
	
	private static final int UPDATE_REMINDING_TIME = 24;
	
	   private Handler mHandler = new Handler() {
	        @Override
			public void handleMessage(Message msg) {
	            if (msg.what == UPDATE_REMINDING_TIME && isInFront){
     			for(int i=0; i<MainActivity.reminderList.size(); i++){
     	        	TextView tvCountDown = (TextView)findViewById(720+i);
     	        	if(tvCountDown != null){
         	        	Calendar t = Calendar.getInstance();
         	        	t.setTimeInMillis(MainActivity.reminderList.get(i).time_to_remind.getTimeInMillis() - t.getTimeInMillis());
         	        	tvCountDown.setText(FormatRemindingTime(t));
     	        	}
         			}
     				if(isInFront)
     					mHandler.sendEmptyMessageDelayed(UPDATE_REMINDING_TIME, 1000);
	            }
	        }
	 };
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminderslist);
        RecreateMain();
	}
	public void onClick(View v) { 
		for(int i=0; i<MainActivity.reminderList.size(); i++)
        {
        	if(v.getId() == 520+i)
        	{
        		if(itemsListExpanded.get(i)){
                	LinearLayout loView = (LinearLayout)itemsList.get(i);
            		View vItem = (View)findViewById(620+i);
                	loView.removeView(vItem);
                	ImageView ivExpand = (ImageView)findViewById(820+i);
                	ivExpand.setImageResource(R.drawable.down_arrow);
        			itemsListExpanded.set(i,false);
        		}else{
                	LinearLayout loView = (LinearLayout)itemsList.get(i);
            		View vItem = getLayoutInflater().inflate(R.layout.reminder_item_expandable, loView, false);
                	vItem.setId(620+i);
                	loView.addView(vItem);
                	ImageView ivExpand = (ImageView)findViewById(820+i);
                	ivExpand.setImageResource(R.drawable.up_arrow);
                	TextView tvDate = (TextView)findViewById(R.id.text_date);
                	tvDate.setId(1120+i);
                	tvDate.setText(getString(R.string.date_added)+
                			FormatDate(MainActivity.reminderList.get(i).time_when_created));
                	TextView tvTime = (TextView)findViewById(R.id.text_time);
                	tvTime.setId(1220+i);
                	tvTime.setText(getString(R.string.time_added)+
                			FormatTime(MainActivity.reminderList.get(i).time_when_created));
                	TextView tvContent = (TextView)findViewById(R.id.text_content);
                	tvContent.setId(1320+i);
                	tvContent.setText(getString(R.string.content)+ GetDescriptionHead(MainActivity.reminderList.get(i).type) +
                			MainActivity.reminderList.get(i).note+ GetDescriptionEnd(MainActivity.reminderList.get(i).type));
                	ImageView ivStar1 = (ImageView)findViewById(R.id.reminderlist_level_star1);
                	ivStar1.setId(generateViewId());
                	ImageView ivStar2 = (ImageView)findViewById(R.id.reminderlist_level_star2);
                	ivStar2.setId(generateViewId());
                	ImageView ivStar3 = (ImageView)findViewById(R.id.reminderlist_level_star3);
                	ivStar3.setId(generateViewId());
                	ImageView ivStar4 = (ImageView)findViewById(R.id.reminderlist_level_star4);
                	ivStar4.setId(generateViewId());
                	ImageView ivStar5 = (ImageView)findViewById(R.id.reminderlist_level_star5);
                	ivStar5.setId(generateViewId());
                	if(MainActivity.reminderList.get(i).level<2)
                		ivStar2.setVisibility(View.INVISIBLE);
                	if(MainActivity.reminderList.get(i).level<3)
                		ivStar3.setVisibility(View.INVISIBLE);
                	if(MainActivity.reminderList.get(i).level<4)
                		ivStar4.setVisibility(View.INVISIBLE);
                	if(MainActivity.reminderList.get(i).level<5)
                		ivStar5.setVisibility(View.INVISIBLE);
                	itemsListExpanded.set(i,true);
        		}
        	}
        }
	}
	
	 public boolean onTouch(View v, MotionEvent motionEvent) {
 		RelativeLayout rView;
		 for(int i=0; i<MainActivity.reminderList.size(); i++)
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
				 for(int i=0; i<MainActivity.reminderList.size(); i++)
			        {
					 rView = (RelativeLayout)findViewById(520+i);
					 rView.setBackgroundColor(android.graphics.Color.rgb(228, 242, 254));
			        }
			 }
		 
		 }
		 return false;
	}
	 private String FormatRemindingTime(Calendar t){
		 String s="";
		 if(t.getTimeInMillis()<0){
			 return getString(R.string.expired);
		 }
		 if((t.get(Calendar.YEAR)-1970)!=0){
			 s=s+(t.get(Calendar.YEAR)-1970)+getString(R.string.year);
		 }
		 if(t.get(Calendar.MONTH)!=0){
			 s=s+t.get(Calendar.MONTH)+getString(R.string.month);
		 }
		 if((t.get(Calendar.DAY_OF_MONTH)-1)!=0){
			 s=s+(t.get(Calendar.DAY_OF_MONTH)-1)+getString(R.string.day);
		 }
		 if((t.get(Calendar.HOUR_OF_DAY)-8)!=0){
			 s=s+(t.get(Calendar.HOUR_OF_DAY)-8)+getString(R.string.hour);
		 }
		 if(t.get(Calendar.MINUTE)!=0){
			 s=s+t.get(Calendar.MINUTE)+getString(R.string.minute);
		 }
		 if(t.get(Calendar.SECOND)!=0){
			 s=s+t.get(Calendar.SECOND)+getString(R.string.second);
		 }
		 return s;
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
			 s=s+t.get(Calendar.SECOND)+getString(R.string.second);
		 return s;
	 } 
	 
	 private CharSequence GetDescriptionHead(int Index){
		 switch(Index){
		 case 1:
			 return "倒计时";
		 case 2:
			 return "定时到";
		 case 3:
			 return "每个小时的";
		 }
		 return "";
	 }
	 
	 private CharSequence GetDescriptionEnd(int Index){
		 switch(Index){
		 case 1:
			 return "";
		 case 2:
			 return "";
		 case 3:
			 return "分";
		 }
		 return "";
	 }
	
	 @Override
	 public void onResume() {
	     super.onResume();
	     RecreateMain();
	 }

	 @Override
	 public void onPause() {
	     super.onPause();
	     isInFront = false;
	 }
	 
	 private void RecreateMain(){
	        //520+ reminder_item_layout
	        //620+ reminder_item_expandable
	        //720+ countdown_text
	        //820+ ri_expand
	        //920+ reminder_type_icon
	        //1020+ reminder_type
	        //1120+ text_date
	        //1220+ text_time
	        //1320+ text_content
	        LinearLayout lView = (LinearLayout)findViewById(R.id.reminderList_layout);
	        lView.removeAllViews();
	        itemsListExpanded.clear();
	        itemsList.clear();
	        for(int i=0; i<MainActivity.reminderList.size(); i++)
	        {
	        	itemsList.add(getLayoutInflater().inflate(R.layout.reminder_item, lView, false));
	        	itemsListExpanded.add(false);
	        	itemsList.get(i).setId(420+i);
	            lView.addView(itemsList.get(i));
	        	RelativeLayout rView = (RelativeLayout)findViewById(R.id.reminder_item_layout);
	        	rView.setId(520+i);
	        	rView.setOnTouchListener(this);
	        	rView.setOnClickListener(this);
	        	TextView tvCountDown = (TextView)findViewById(R.id.countdown_text);
	        	tvCountDown.setId(720+i);
	        	ImageView ivExpand = (ImageView)findViewById(R.id.ri_expand);
	        	ivExpand.setId(820+i);
	        	ImageView ivType = (ImageView)findViewById(R.id.reminder_type_icon);
	        	ivType.setId(920+i);
	        	TextView tvType = (TextView)findViewById(R.id.reminder_type);
	        	tvType.setId(1020+i);
	        	switch(MainActivity.reminderList.get(i).type){
	        	case 1:
	            	ivType.setImageResource(R.drawable.timer1_icon);
	            	tvType.setText(getString(R.string.title_timer1));
	            	break;
	        	case 2:
	            	ivType.setImageResource(R.drawable.timer2_icon);
	            	tvType.setText(getString(R.string.title_timer2));
	            	break;
	        	case 3:
	            	ivType.setImageResource(R.drawable.timer3_icon);
	            	tvType.setText(getString(R.string.title_timer3));
	            	break;
	        	case 4:
	            	ivType.setImageResource(R.drawable.timer4_icon);
	            	tvType.setText(getString(R.string.title_timer4));
	            	break;
	        	}
	        }
	        ScrollView sView = (ScrollView)findViewById(R.id.reminderList_scroll);
	        sView.setOnTouchListener(this);
	        isInFront=true;
	        mHandler.sendEmptyMessage(UPDATE_REMINDING_TIME);
	 }
	 
	 private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

	 /**
	  * Generate a value suitable for use in {@link #setId(int)}.
	  * This value will not collide with ID values generated at build time by aapt for R.id.
	  *
	  * @return a generated ID value
	  */
	 public static int generateViewId() {
	     for (;;) {
	         final int result = sNextGeneratedId.get();
	         // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
	         int newValue = result + 1;
	         if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
	         if (sNextGeneratedId.compareAndSet(result, newValue)) {
	             return result;
	         }
	     }
	 }
}
