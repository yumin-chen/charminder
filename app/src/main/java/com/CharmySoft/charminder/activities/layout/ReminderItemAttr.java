/*
**  Class ReminderItemAttr
**  src/com/CharmySoft/charminder/activities/layout/ReminderItemAttr.java
*/
package com.CharmySoft.charminder.activities.layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.activities.ReminderEditorActivity;
import com.CharmySoft.charminder.activities.ReminderListActivity;
import com.CharmySoft.charminder.base.ViewBase;
import com.CharmySoft.charminder.data.Reminder;
import com.CharmySoft.charminder.helper.FunctionWrapper;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public class ReminderItemAttr extends LinearLayout {
	int iIndex;
	public ReminderItemAttr(Context context) {
		super(context);
		setOrientation(VERTICAL);
		setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		setBackgroundColor(C.COLOR_BLUE);
	}
	
	public void setReminder(int index){
		iIndex = index;
		update();
	}

	protected void update() {
		this.removeAllViews();
		LayoutInflater.from(getContext()).inflate(R.layout.fragment_reminder_item_expansion,
				this, true);
		LinearLayout editButton = ((LinearLayout) findViewById(R.id.expansion_item_edit_button));
		editButton.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView image = (ImageView) findViewById(R.id.expansion_item_edit_image);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(C.COLOR_GREENBLUE);
					image.setImageResource(R.drawable.sr_edit_button_a);
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(C.COLOR_BLUE);
					image.setImageResource(R.drawable.sr_edit_button);
					break;
				}
				return false;
			}
			
		});
		editButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				ReminderEditorActivity.iIndex = iIndex;
				ReminderEditorActivity.mOnOkLisntener = new FunctionWrapper(){

					@Override
					public void function() {
			            ReminderListActivity.updateItem(iIndex);
					}
					
				};
				ReminderEditorActivity.mOnCancelLisntener = null;
				G.context.startActivity(new Intent(G.context, ReminderEditorActivity.class));
			}
			
		});

		LinearLayout deleteButton = ((LinearLayout) findViewById(R.id.expansion_item_delete_button));
		deleteButton.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView image = (ImageView) findViewById(R.id.expansion_item_delete_image);
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(C.COLOR_GREENBLUE);
					image.setImageResource(R.drawable.delete_button_a);
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(C.COLOR_BLUE);
					image.setImageResource(R.drawable.delete_button);
					break;
				}
				return false;
			}
			
		});
		deleteButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AlertDialog.Builder diaglog = new AlertDialog.Builder(v.getContext());
				diaglog.setTitle(R.string.reminder_list_item_delete_dialog_title);
			    diaglog.setMessage(R.string.reminder_list_item_delete_dialog_content);
			    diaglog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        @Override
					public void onClick(DialogInterface dialog, int which) { 
			            G.reminders.remove(iIndex);
			            ReminderListActivity.update((Activity) G.context);
			        }
			     });
				diaglog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        @Override
					public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     });
			    diaglog.setIcon(android.R.drawable.ic_delete);
			    diaglog.show();
				
			}
			
		});
		
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
				if(G.reminders.get(iIndex).iRepeat == 0){
					continue;
				}
				String[] repeat = G.context.getResources().getStringArray(
						R.array.repeat);
				item.setText(names[i] + repeat[G.reminders.get(iIndex).iRepeat]);
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
