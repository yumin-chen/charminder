/*
**  Class ReminderItem
**  src/com/CharmySoft/charminder/activities/layout/ReminderItem.java
*/
package com.CharmySoft.charminder.activities.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.data.Reminder;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

import java.util.Calendar;

public class ReminderItem extends RelativeLayout {
	int iIndex;
	boolean bExpanded;
	ReminderItemAttr mItemAttr;
	public ReminderItem(Context context) {
		super(context);
		setBackgroundColor(C.COLOR_LIGHTBLUE);
		LayoutInflater.from(getContext()).inflate(R.layout.fragment_reminder_item,
				this, true);
		setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(C.COLOR_LIGHTBLUE_WD);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundColor(C.COLOR_LIGHTBLUE);
					break;
				}
				return false;
			}
		});
		setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				onTap();
			}
			
		});
		bExpanded = false;
	}
	
	protected void onTap(){
		LinearLayout parent = (LinearLayout)this.getParent();
		for(int i = 0; i < parent.getChildCount(); i++){
			if(parent.getChildAt(i) == this){
				ImageView expandButton = ((ImageView) findViewById(R.id.reminder_item_expand));
				if(bExpanded){
					parent.removeView(mItemAttr);
					expandButton.setImageResource(R.drawable.down_arrow);
				}else{
					mItemAttr = new ReminderItemAttr(G.context);
					parent.addView(mItemAttr, i + 1);
					mItemAttr.setReminder(iIndex);
					expandButton.setImageResource(R.drawable.up_arrow);
				}
				bExpanded = !bExpanded;
				break;
			}
		}
	}
	
	public void setReminder(int index){
		iIndex = index;
		update();
	}
	
	public void update() {
		if(iIndex == 0){
			removeView(findViewById(R.id.reminder_item_divider));
		}
		TypedArray drawable = getResources().obtainTypedArray(
				R.array.reminder_list_icons);
		String[] names = G.context.getResources().getStringArray(
				R.array.main_menu_names);
		Reminder r = G.reminders.get(iIndex);
		ImageView reminderTypeIcon = ((ImageView) findViewById(R.id.reminder_type_icon));
		reminderTypeIcon.setImageDrawable(drawable.getDrawable(r.iType - 1));
		drawable.recycle();
		
		TextView reminderText = ((TextView) findViewById(R.id.reminder_type));
		reminderText.setText((r.sTitle == null || r.sTitle.isEmpty())?
				names[r.iType - 1]: r.sTitle);
		updateTime();
		if(mItemAttr != null){
			mItemAttr.update();
		}
	}
	public void updateTime(){
		long diffs = G.reminders.get(iIndex).mTimeToRemind.getTimeInMillis() - 
				Calendar.getInstance().getTimeInMillis();
		TextView countdownText = ((TextView) findViewById(R.id.countdown_text));
		countdownText.setText(diffs <= 0? G.context.getResources()
				.getString(R.string.reminder_list_item_expired):
				Reminder.formatCountdownText(diffs));
	}
}
