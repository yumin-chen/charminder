/*
**  Class ReminderEditorActivity
**  src/com/CharmySoft/charminder/activities/ReminderEditorActivity.java
*/
package com.CharmySoft.charminder.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TimePicker;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.activities.layout.FiveStars;
import com.CharmySoft.charminder.base.ActivityBase;
import com.CharmySoft.charminder.base.ViewBase;
import com.CharmySoft.charminder.data.Reminder;
import com.CharmySoft.charminder.helper.FunctionWrapper;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

import java.util.Calendar;

public class ReminderEditorActivity extends ActivityBase implements OnClickListener {
	
	public static int iIndex;//Index of the reminder
	public static FunctionWrapper mOnOkLisntener;
	public static FunctionWrapper mOnCancelLisntener;
	static Dialog mTimePickerDialog;
	static AlertDialog mRepeatPickerDialog;
	static int iRepeat;
	static FiveStars mFiveStars;
	private Calendar mTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(new ColorDrawable(C.COLOR_TRANSPARENT));
		float width = ViewBase.getScreenWidth();
		if(width > ViewBase.dpToPx(400))
			width = ViewBase.dpToPx(400);
		width *= 0.8;
		setContentView(R.layout.activity_edit);
		((ScrollView) findViewById(R.id.acvitity_scroll_view)).getLayoutParams().width = (int) width;
		((EditText) findViewById(R.id.edit_title)).setText(G.reminders.get(iIndex).sTitle);
		EditText edittime = ((EditText) findViewById(R.id.edit_time));
		mTime = G.reminders.get(iIndex).mTimeToRemind;
		edittime.setText(Reminder.formatTime(mTime));
		edittime.setKeyListener(null);
		edittime.setOnFocusChangeListener(new OnFocusChangeListener() {

		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if (hasFocus) {
		        	popTimeDialog();
		        } else {
		        	if(mTimePickerDialog != null){
						mTimePickerDialog.cancel();
		        	}
		        }
		    }
		});
		edittime.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				popTimeDialog();
			}

			
		});
		iRepeat = G.reminders.get(iIndex).iRepeat;
		String[] repeat = G.context.getResources().getStringArray(
				R.array.repeat);
		EditText editrepeat = ((EditText) findViewById(R.id.edit_repeat));
		editrepeat.setText(repeat[iRepeat]);
		editrepeat.setKeyListener(null);
		editrepeat.setOnFocusChangeListener(new OnFocusChangeListener() {

		    @Override
		    public void onFocusChange(View v, boolean hasFocus) {
		        if (hasFocus) {
		        	popRepeatDialog();
		        } else {
		        	if(mRepeatPickerDialog != null){
		        		mRepeatPickerDialog.cancel();
		        	}
		        }
		    }
		});
		editrepeat.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				popRepeatDialog();
			}
			
		});
		((EditText) findViewById(R.id.edit_location)).setText(G.reminders.get(iIndex).sLocation);
		((EditText) findViewById(R.id.edit_note)).setText(G.reminders.get(iIndex).sNote);
		LinearLayout priorityLayout = ((LinearLayout) findViewById(R.id.edit_priority_layout));
		mFiveStars = new FiveStars(G.context);
		priorityLayout.addView(mFiveStars);
		mFiveStars.setPriority(G.reminders.get(iIndex).iPriority);
		mFiveStars.setEditable(true);

	}

	private void popTimeDialog() {
		if(mTimePickerDialog != null && mTimePickerDialog.isShowing()){
			return;
		}
		mTimePickerDialog = new Dialog(G.context);
		mTimePickerDialog.setContentView(R.layout.dialog_date_and_time_picker);
		mTimePickerDialog.setTitle(G.context.getString(R.string.edit_time_picker_prompt));
		final TimePicker tp = (TimePicker)mTimePickerDialog.findViewById(R.id.dialog_time_picker);
		tp.setIs24HourView(true);
		tp.setCurrentHour(mTime.get(Calendar.HOUR_OF_DAY));
		tp.setCurrentMinute(mTime.get(Calendar.MINUTE));
		final DatePicker dp = (DatePicker)mTimePickerDialog.findViewById(R.id.dialog_date_picker);
		dp.updateDate(
				mTime.get(Calendar.YEAR),
				mTime.get(Calendar.MONTH),
				mTime.get(Calendar.DAY_OF_MONTH));
		((Button) mTimePickerDialog.findViewById(R.id.dialog_cancel_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mTimePickerDialog.cancel();
			}
			
		});
		((Button) mTimePickerDialog.findViewById(R.id.dialog_ok_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mTime.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
				mTime.set(Calendar.MINUTE, tp.getCurrentMinute());
				mTime.set(Calendar.YEAR, dp.getYear());
				mTime.set(Calendar.MONTH, dp.getMonth());
				mTime.set(Calendar.DAY_OF_MONTH, dp.getDayOfMonth());
				((EditText) findViewById(R.id.edit_time)).setText(Reminder.formatTime(mTime));
				mTimePickerDialog.dismiss();
			}
			
		});
		mTimePickerDialog.show();
	}
	
	private void popRepeatDialog(){
		if(mRepeatPickerDialog != null && mRepeatPickerDialog.isShowing()){
			return;
		}
		final String[] repeat = G.context.getResources().getStringArray(
				R.array.repeat);
		AlertDialog.Builder repeatPickerDialogBuilder = new AlertDialog.Builder(this)
        .setTitle(R.string.edit_repeat_picker_prompt)
        .setSingleChoiceItems( new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, 
        		repeat), iRepeat, new DialogInterface.OnClickListener() {
            @Override
			public void onClick(DialogInterface dialog, int which) {
                (((EditText) findViewById(R.id.edit_repeat))).setText(repeat[which]);
                iRepeat = which;
                dialog.cancel();
            }
        });
    mRepeatPickerDialog = repeatPickerDialogBuilder.create();
    mRepeatPickerDialog.show();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_ok:
			G.reminders.get(iIndex).sTitle = String.valueOf(((EditText) 
					findViewById(R.id.edit_title)).getText());
			G.reminders.get(iIndex).sLocation = String.valueOf(((EditText) 
					findViewById(R.id.edit_location)).getText());
			G.reminders.get(iIndex).sNote = String.valueOf(((EditText)
					findViewById(R.id.edit_note)).getText());
			G.reminders.get(iIndex).iPriority = mFiveStars.getPriority();
			G.reminders.get(iIndex).iRepeat = iRepeat;
			G.reminders.save();
			mOnCancelLisntener = null;
			finish();
			if(mOnOkLisntener != null){
				mOnOkLisntener.function();
			}
			break;
		case R.id.edit_cancel:
			finish();
			break;
		}
	}

	
	@Override
	public void onStop () {
		super.onStop();
		if(mOnCancelLisntener != null){
			mOnCancelLisntener.function();
			mOnCancelLisntener = null;
		}
		mOnOkLisntener = null;
	}
}
