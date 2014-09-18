package com.pujoy.charminder.activities.layout;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.ViewBase;
import com.pujoy.charminder.data.Reminder;
import com.pujoy.charminder.other.C;
import com.pujoy.charminder.other.G;

import android.content.Context;
import android.content.res.TypedArray;
import android.widget.LinearLayout;

public class ReminderItemAttr extends LinearLayout {
	int iIndex;
	public ReminderItemAttr(Context context) {
		super(context);
		setOrientation(VERTICAL);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		setBackgroundColor(C.COLOR_LIGHTBLUE);
	}
	
	public void setReminder(int index){
		iIndex = index;
		update();
	}

	protected void update() {
		this.removeAllViews();
		TypedArray drawable = getResources().obtainTypedArray(
				R.array.reminder_item_attr_icon);
		String[] names = G.context.getResources().getStringArray(
				R.array.reminder_item_attr);
		for(int i = 0; i < drawable.length(); i++){
			ReminderItemAttrItem item = new ReminderItemAttrItem(G.context);
			item.setIcon(drawable.getDrawable(i));
			switch(i){
			case 0://Date created
				item.setText(names[i] + Reminder.formatTime(G.reminders.get(iIndex).mTimeCreated));
				break;
			case 1://Date to remind
				item.setText(names[i] + Reminder.formatTime(G.reminders.get(iIndex).mTimeToRemind));
				break;
			case 2://Priority
			{
				item.setText(names[i]);
				FiveStars stars = new FiveStars(G.context);
				stars.setPriority(G.reminders.get(iIndex).iPriority);
				stars.setTop((int) ViewBase.dpToPx(4));
				item.addView(stars);
				break;
			}
			case 3://Repeat
				item.setText(names[i]);
				break;
			case 4://Location
				if(G.reminders.get(iIndex).sLocation == null || G.reminders.get(iIndex).sLocation.isEmpty()){
					continue;
				}
				item.setText(names[i] + G.reminders.get(iIndex).sLocation);
				break;
			case 5://Note
				if(G.reminders.get(iIndex).sNote == null || G.reminders.get(iIndex).sNote.isEmpty()){
					continue;
				}
				item.setText(names[i] + G.reminders.get(iIndex).sNote);
				break;
			default:
				item.setText(names[i]);
				break;
			}
			this.addView(item);
		}
		drawable.recycle();
		
	}
}
