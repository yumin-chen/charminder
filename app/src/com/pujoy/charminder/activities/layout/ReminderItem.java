package com.pujoy.charminder.activities.layout;

import java.util.Calendar;

import com.pujoy.charminder.R;
import com.pujoy.charminder.data.Reminder;
import com.pujoy.charminder.other.C;
import com.pujoy.charminder.other.G;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReminderItem extends RelativeLayout {
	int iIndex;
	boolean bExpanded;
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
					parent.removeViewAt(i + 1);
					expandButton.setImageResource(R.drawable.down_arrow);
				}else{
					ReminderItemAttr itemAttr = new ReminderItemAttr(G.context);
					parent.addView(itemAttr, i + 1);
					itemAttr.setReminder(iIndex);
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
	
	private void update() {
		if(iIndex == 0){
			removeView((View) findViewById(R.id.reminder_item_divider));
		}
		TypedArray drawble = getResources().obtainTypedArray(
				R.array.reminder_list_icons);
		String[] names = G.context.getResources().getStringArray(
				R.array.main_menu_names);
		Reminder r = G.reminders.get(iIndex);
		ImageView reminderTypeIcon = ((ImageView) findViewById(R.id.reminder_type_icon));
		reminderTypeIcon.setImageDrawable(drawble.getDrawable(r.iType - 1));
		
		TextView reminderText = ((TextView) findViewById(R.id.reminder_type));
		reminderText.setText((r.sTitle == null || r.sTitle.isEmpty())?
				names[r.iType - 1]: r.sTitle);
		
		long diffs = r.mTimeToRemind.getTimeInMillis() - 
				Calendar.getInstance().getTimeInMillis();
		TextView countdownText = ((TextView) findViewById(R.id.countdown_text));
		countdownText.setText(diffs <= 0? G.context.getResources()
				.getString(R.string.reminder_list_item_expired):
				Reminder.formatCountdownText(diffs));
		
		drawble.recycle();
	}
}
