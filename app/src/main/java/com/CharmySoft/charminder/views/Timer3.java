/*
**  Class Timer3
**  src/com/CharmySoft/charminder/view/Timer3.java
*/
package com.CharmySoft.charminder.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.base.WindowTimerDialog;
import com.CharmySoft.charminder.data.Reminder;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

import java.util.Calendar;

public class Timer3 extends WindowTimerDialog implements OnClickListener {

	private TextView[] mDigits;
	private ImageView mTitleIcon;
	private TextView mHour;
	private TextView mMinute;
	private int iCurrentDigit;

	@Override
	protected void onInitialize() {
		super.onInitialize();

		mDigits = new TextView[2];
		for (int i = 0; i < 2; i++) {
			mDigits[i] = new TextView(G.context);
			mDigits[i].setLayoutParams(new LayoutParams((int) dpToPx(28),
					(int) dpToPx(40)));
			mDigits[i].setX(mLayoutParams.getWidth() / 2 - dpToPx(16)
					+ dpToPx(28) * i);
			mDigits[i].setY(dpToPx(36));
			mDigits[i].setText("0");
			mDigits[i].setGravity(Gravity.CENTER);
			mDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
			mDigits[i].setOnClickListener(this);
			addToMainView(mDigits[i]);
		}
		iCurrentDigit = 0;
		UpdateCurrentDigit();
		mHour = new TextView(G.context);
		mHour.setLayoutParams(new LayoutParams((int) dpToPx(48),
				(int) dpToPx(20)));
		mHour.setX(mLayoutParams.getWidth() / 2 - dpToPx(62));
		mHour.setText(G.context.getString(R.string.unit_everyhour));
		mHour.setGravity(Gravity.CENTER);
		mHour.setTextColor(C.COLOR_LIGHTBLUE);
		mHour.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

		addToMainView(mHour);
		mMinute = new TextView(G.context);
		mMinute.setLayoutParams(new LayoutParams((int) dpToPx(20),
				(int) dpToPx(20)));
		mMinute.setX(mLayoutParams.getWidth() / 2 + dpToPx(20) * 2);
		mMinute.setY(dpToPx(52));
		mMinute.setText(G.context.getString(R.string.unit_minute));
		mMinute.setGravity(Gravity.CENTER);
		mMinute.setTextColor(C.COLOR_LIGHTBLUE);
		mMinute.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

		addToMainView(mMinute);

		mTitleIcon = new ImageView(G.context);
		mTitleIcon.setImageResource(R.drawable.timer3_icon);
		mTitleIcon.setLayoutParams(new LayoutParams((int) dpToPx(32),
				(int) dpToPx(32)));
		mTitleIcon.setX(mLayoutParams.getWidth() / 2 - dpToPx(32) / 2);

		addToMainView(mTitleIcon);
	}
	
	@Override
	protected void onDestroy() {
		for (int i = 0; i < mDigits.length; i++) {
			removeFromMainView(mDigits[i]);
			mDigits[i] = null;
		}
		mDigits = null;
		mTitleIcon.setImageBitmap(null);
		removeFromMainView(mTitleIcon);
		mTitleIcon = null;
		removeFromMainView(mHour);
		mHour = null;
		removeFromMainView(mMinute);
		mMinute = null;
		super.onDestroy();
	}

	@Override
	protected void onCreate() {
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(mTitleIcon, "y",
				mLayoutParams.getHeight() / 2 - mLayoutParams.getWidth() / 2
						+ dpToPx(4), dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);
		ValueAnimator aHourY = ObjectAnimator.ofFloat(mHour, "y",
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(52), dpToPx(52));
		aHourY.setDuration(ANIMATION_DURATION);

		ValueAnimator aMinuteY = ObjectAnimator.ofFloat(mMinute, "y",
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(52), dpToPx(52));
		aMinuteY.setDuration(ANIMATION_DURATION);

		ValueAnimator[] aDigitsY = new ValueAnimator[2];
		for (int i = 0; i < 2; i++) {
			aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y",
					(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
							+ dpToPx(34), dpToPx(34));
			aDigitsY[i].setDuration(ANIMATION_DURATION);
		}

		aTitleIconY.start();
		aHourY.start();
		aMinuteY.start();
		for (int i = 0; i < 2; i++) {
			aDigitsY[i].start();
		}
	}

	@Override
	public void onRemove() {

		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(mTitleIcon, "y",
				dpToPx(4),
				mLayoutParams.getHeight() / 2 - mLayoutParams.getWidth() / 2
						+ dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);

		ValueAnimator aHourY = ObjectAnimator.ofFloat(mHour, "y", dpToPx(52),
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(52));
		aHourY.setDuration(ANIMATION_DURATION);
		ValueAnimator aMinuteY = ObjectAnimator.ofFloat(mMinute, "y",
				dpToPx(52),
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(52));
		aMinuteY.setDuration(ANIMATION_DURATION);

		ValueAnimator[] aDigitsY = new ValueAnimator[2];
		for (int i = 0; i < 2; i++) {
			aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y", dpToPx(34),
					(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
							+ dpToPx(34));
			aDigitsY[i].setDuration(ANIMATION_DURATION);
		}

		aTitleIconY.start();
		aHourY.start();
		aMinuteY.start();
		for (int i = 0; i < 2; i++) {
			aDigitsY[i].start();
		}

	}

	@Override
	public void onClick(View v) {

		for (int i = 0; i < 2; i++) {
			if (v == mDigits[i]) {
				iCurrentDigit = i;
				UpdateCurrentDigit();
			}
		}

	}

	@Override
	protected void onUpdateLayout() {
		super.onUpdateLayout();
		mLayoutParams.setWidth((int) dpToPx(256));
		mLayoutParams.setHeight((int) dpToPx(320));
		mLayoutParams.x = (getScreenWidth() - mLayoutParams.getWidth()) / 2;
		mLayoutParams.y = (getScreenHeight() - mLayoutParams.getHeight()) / 2;
		mLayoutParams.alpha = 0.95f;
	}

	private void UpdateCurrentDigit() {
		for (int i = 0; i < 2; i++) {
			if (iCurrentDigit == i) {
				mDigits[i].setBackgroundColor(C.COLOR_LIGHTBLUE);
				mDigits[i].setTextColor(C.COLOR_DARKBLUE);
			} else {
				mDigits[i].setBackgroundColor(C.COLOR_DARKBLUE);
				mDigits[i].setTextColor(C.COLOR_LIGHTBLUE);
			}
		}
	}

	@Override
	protected void onOk() {
		addReminder(true);
	}

	@Override
	protected void onCancel() {
	}

	@Override
	protected void onKeyDown(int i) {
		if (iCurrentDigit == 0 && (i >= 5 && i != 9)) {
			mDigits[0].setText("0");
			iCurrentDigit++;
		}
		mDigits[iCurrentDigit].setText(mNumKeys[i].getText());
		iCurrentDigit++;
		if (iCurrentDigit == 2)
			iCurrentDigit = 0;
		UpdateCurrentDigit();
	}

	@Override
	protected void addReminder(boolean pushBubble) {
		Reminder newReminder = new Reminder(3);
		newReminder.mTimeToRemind = Calendar.getInstance();
		newReminder.mTimeToRemind.set(Calendar.MINUTE, 
				Integer.valueOf(mDigits[0].getText().toString()+mDigits[1].getText()));
		newReminder.mTimeToRemind.set(Calendar.SECOND, 0);
		if(newReminder.mTimeToRemind.compareTo(Calendar.getInstance()) <=0) 
			newReminder.mTimeToRemind.add(Calendar.HOUR, 1);
		newReminder.iPriority = iPriority;
		newReminder.iRepeat = 1;// Hourly
		G.reminders.add(newReminder, pushBubble);
		
	}

}
