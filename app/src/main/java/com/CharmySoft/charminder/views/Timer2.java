/*
**  Class Timer2
**  src/com/CharmySoft/charminder/view/Timer2.java
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

public class Timer2 extends WindowTimerDialog implements OnClickListener {

	private ImageView mTitleIcon;
	private TextView mAmPm;
	private TextView[] mDigits;
	private TextView mHour;
	private TextView mMinute;
	private TextView mYear;
	private TextView mMonth;
	private TextView mDay;
	private TextView mTwoZeroOne;

	static int currentDigit;

	@Override
	protected void onInitialize() {
		super.onInitialize(true);
		mDigits = new TextView[9];
		for (int i = 0; i < 4; i++) {
			mDigits[i] = new TextView(G.context);
			mDigits[i].setLayoutParams(new LayoutParams((int) dpToPx(20),
					(int) dpToPx(40)));
			mDigits[i].setX(mLayoutParams.getWidth() / 2 - dpToPx(20 * 2)
					+ dpToPx(20) * i + (i > 1 ? dpToPx(12) : 0));
			mDigits[i].setText("0");
			mDigits[i].setGravity(Gravity.CENTER);
			mDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
			mDigits[i].setOnClickListener(this);
			addToMainView(mDigits[i]);
		}
		for (int i = 4; i < 9; i++) {
			mDigits[i] = new TextView(G.context);
			mDigits[i].setLayoutParams(new LayoutParams((int) dpToPx(20),
					(int) dpToPx(40)));
			mDigits[i].setX(mLayoutParams.getWidth() / 2 - dpToPx(20 * 7)
					+ dpToPx(20) * i + (i > 5 ? dpToPx(12) : 0) - dpToPx(32)
					- dpToPx(12));
			mDigits[i].setText("0");
			mDigits[i].setGravity(Gravity.CENTER);
			mDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
			mDigits[i].setOnClickListener(this);
			addToMainView(mDigits[i]);
		}
		mDigits[8].setX(mLayoutParams.getWidth() / 2 - dpToPx(20 * 7)
				+ dpToPx(20) * (8 + 3) + dpToPx(24) - dpToPx(24) - dpToPx(12));
		Calendar cal = Calendar.getInstance();
		mDigits[8].setText(String.valueOf(Integer
				.valueOf(cal.get(Calendar.YEAR)).toString().charAt(3)));
		String s = Integer.valueOf(cal.get(Calendar.HOUR_OF_DAY)).toString();
		if (s.length() == 1) {
			mDigits[1].setText(s);
		} else {
			mDigits[0].setText(String.valueOf(s.charAt(0)));
			mDigits[1].setText(String.valueOf(s.charAt(1)));
		}
		s = Integer.valueOf(cal.get(Calendar.MINUTE)).toString();
		if (s.length() == 1) {
			mDigits[3].setText(s);
		} else {
			mDigits[2].setText(String.valueOf(s.charAt(0)));
			mDigits[3].setText(String.valueOf(s.charAt(1)));
		}
		s = Integer.valueOf(cal.get(Calendar.MONTH) + 1).toString();
		if (s.length() == 1) {
			mDigits[5].setText(s);
		} else {
			mDigits[4].setText(String.valueOf(s.charAt(0)));
			mDigits[5].setText(String.valueOf(s.charAt(1)));
		}
		s = Integer.valueOf(cal.get(Calendar.DAY_OF_MONTH)).toString();
		if (s.length() == 1) {
			mDigits[7].setText(s);
		} else {
			mDigits[6].setText(String.valueOf(s.charAt(0)));
			mDigits[7].setText(String.valueOf(s.charAt(1)));
		}

		mTwoZeroOne = new TextView(G.context);
		mTwoZeroOne.setLayoutParams(new LayoutParams((int) dpToPx(80),
				(int) dpToPx(40)));
		mTwoZeroOne.setX(mLayoutParams.getWidth() / 2 - dpToPx(20 * 7)
				+ dpToPx(20) * 8 + dpToPx(26) - dpToPx(36) - dpToPx(12));

		mTwoZeroOne.setText("/201");
		mTwoZeroOne.setGravity(Gravity.CENTER);
		mTwoZeroOne.setTextColor(C.COLOR_LIGHTBLUE);
		mTwoZeroOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
		addToMainView(mTwoZeroOne);

		mMonth = new TextView(G.context);
		mMonth.setLayoutParams(new LayoutParams((int) dpToPx(20),
				(int) dpToPx(20)));
		mMonth.setX(mLayoutParams.getWidth() / 2 - dpToPx(20 * 7) + dpToPx(20)
				* 6 - dpToPx(4) - dpToPx(32) - dpToPx(12));
		mMonth.setText(G.context.getString(R.string.unit_month));
		mMonth.setGravity(Gravity.CENTER);
		mMonth.setTextColor(C.COLOR_LIGHTBLUE);
		mMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mMonth);

		mDay = new TextView(G.context);
		mDay.setLayoutParams(new LayoutParams((int) dpToPx(20),
				(int) dpToPx(20)));
		mDay.setX(mLayoutParams.getWidth() / 2 - dpToPx(20 * 7) + dpToPx(20)
				* 8 + dpToPx(8) - dpToPx(32) - dpToPx(12));
		mDay.setText(G.context.getString(R.string.unit_day));
		mDay.setGravity(Gravity.CENTER);
		mDay.setTextColor(C.COLOR_LIGHTBLUE);
		mDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mDay);

		mYear = new TextView(G.context);
		mYear.setLayoutParams(new LayoutParams((int) dpToPx(20),
				(int) dpToPx(20)));
		mYear.setX(mLayoutParams.getWidth() / 2 - dpToPx(20 * 7) + dpToPx(20)
				* 8 + dpToPx(66));
		mYear.setText(G.context.getString(R.string.unit_year));
		mYear.setGravity(Gravity.CENTER);
		mYear.setTextColor(C.COLOR_LIGHTBLUE);
		mYear.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mYear);

		mAmPm = new TextView(G.context);
		mAmPm.setLayoutParams(new LayoutParams((int) dpToPx(32),
				(int) dpToPx(20)));
		mAmPm.setX(mLayoutParams.getWidth() / 2 - dpToPx(2) - dpToPx(40)
				- dpToPx(28));
		mAmPm.setText(G.context.getString(R.string.a_m_));
		mAmPm.setGravity(Gravity.CENTER);
		mAmPm.setTextColor(C.COLOR_LIGHTBLUE);
		mAmPm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mAmPm);

		mHour = new TextView(G.context);
		mHour.setLayoutParams(new LayoutParams((int) dpToPx(20),
				(int) dpToPx(20)));
		mHour.setX(mLayoutParams.getWidth() / 2 - dpToPx(4));
		mHour.setText(G.context.getString(R.string.unit_hour));
		mHour.setGravity(Gravity.CENTER);
		mHour.setTextColor(C.COLOR_LIGHTBLUE);
		mHour.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mHour);
		mMinute = new TextView(G.context);
		mMinute.setLayoutParams(new LayoutParams((int) dpToPx(20),
				(int) dpToPx(20)));
		mMinute.setX(mLayoutParams.getWidth() / 2 + dpToPx(20) * 2 + dpToPx(8));
		mMinute.setText(G.context.getString(R.string.unit_minute));
		mMinute.setGravity(Gravity.CENTER);
		mMinute.setTextColor(C.COLOR_LIGHTBLUE);
		mMinute.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mMinute);

		currentDigit = 0;
		UpdateCurrentDigit();

		mTitleIcon = new ImageView(G.context);
		mTitleIcon.setImageResource(R.drawable.timer2_icon);
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
		removeFromMainView(mAmPm);
		mAmPm = null;
		removeFromMainView(mHour);
		mHour = null;
		removeFromMainView(mMinute);
		mMinute = null;
		removeFromMainView(mYear);
		mYear = null;
		removeFromMainView(mMonth);
		mMonth = null;
		removeFromMainView(mDay);
		mDay = null;
		removeFromMainView(mTwoZeroOne);
		mTwoZeroOne = null;
		super.onDestroy();
	}

	@Override
	protected void onCreate() {

		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(mTitleIcon, "y",
				mLayoutParams.getHeight() / 2 - mLayoutParams.getWidth() / 2
						+ dpToPx(4), dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);

		ValueAnimator[] aDigitsY = new ValueAnimator[9];
		for (int i = 0; i < 4; i++) {
			aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y",
					(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
							+ dpToPx(40), dpToPx(40));
			aDigitsY[i].setDuration(ANIMATION_DURATION);
		}

		for (int i = 4; i < 9; i++) {
			aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y",
					(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
							+ dpToPx(84), dpToPx(84));
			aDigitsY[i].setDuration(ANIMATION_DURATION);

		}

		ValueAnimator a201Y = ObjectAnimator.ofFloat(mTwoZeroOne, "y",
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(84), dpToPx(84));
		a201Y.setDuration(ANIMATION_DURATION);

		ValueAnimator aYearY = ObjectAnimator.ofFloat(mYear, "y",
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(84 + 18), dpToPx(84 + 18));
		aYearY.setDuration(ANIMATION_DURATION);

		ValueAnimator aMonthY = ObjectAnimator.ofFloat(mMonth, "y",
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(84 + 18), dpToPx(84 + 18));
		aMonthY.setDuration(ANIMATION_DURATION);

		ValueAnimator aDayY = ObjectAnimator.ofFloat(mDay, "y",
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(84 + 18), dpToPx(84 + 18));
		aDayY.setDuration(ANIMATION_DURATION);

		ValueAnimator aAmPmY = ObjectAnimator.ofFloat(mAmPm, "y",
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(58), dpToPx(58));
		aAmPmY.setDuration(ANIMATION_DURATION);

		ValueAnimator aHourY = ObjectAnimator.ofFloat(mHour, "y",
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(58), dpToPx(58));
		aHourY.setDuration(ANIMATION_DURATION);

		ValueAnimator aMinuteY = ObjectAnimator.ofFloat(mMinute, "y",
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(58), dpToPx(58));
		aMinuteY.setDuration(ANIMATION_DURATION);

		aTitleIconY.start();
		a201Y.start();
		aYearY.start();
		aMonthY.start();
		aDayY.start();
		aHourY.start();
		aMinuteY.start();
		aAmPmY.start();
		for (int i = 0; i < 9; i++) {
			aDigitsY[i].start();
		}

	}

	@Override
	public void onRemove() {
		ValueAnimator[] aDigitsY = new ValueAnimator[9];
		for (int i = 0; i < 4; i++) {
			aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y", dpToPx(40),
					(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
							+ dpToPx(40));
			aDigitsY[i].setDuration(ANIMATION_DURATION);
		}

		for (int i = 4; i < 9; i++) {
			aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y", dpToPx(84),
					(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
							+ dpToPx(84));
			aDigitsY[i].setDuration(ANIMATION_DURATION);
		}
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(mTitleIcon, "y",
				dpToPx(4),
				mLayoutParams.getHeight() / 2 - mLayoutParams.getWidth() / 2
						+ dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);

		ValueAnimator aAmPmY = ObjectAnimator.ofFloat(mAmPm, "y", dpToPx(58),
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(58));
		aAmPmY.setDuration(ANIMATION_DURATION);
		ValueAnimator aHourY = ObjectAnimator.ofFloat(mHour, "y", dpToPx(58),
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(58));
		aHourY.setDuration(ANIMATION_DURATION);
		ValueAnimator aMinuteY = ObjectAnimator.ofFloat(mMinute, "y",
				dpToPx(58),
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(58));
		aMinuteY.setDuration(ANIMATION_DURATION);

		ValueAnimator aMonthY = ObjectAnimator.ofFloat(mMonth, "y",
				dpToPx(84 + 18),
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(84 + 18));
		aMonthY.setDuration(ANIMATION_DURATION);
		aMonthY.start();

		ValueAnimator aDayY = ObjectAnimator.ofFloat(mDay, "y",
				dpToPx(84 + 18),
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(84 + 18));
		aDayY.setDuration(ANIMATION_DURATION);
		aDayY.start();

		ValueAnimator aYearY = ObjectAnimator.ofFloat(mYear, "y",
				dpToPx(84 + 18),
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(84 + 18));
		aYearY.setDuration(ANIMATION_DURATION);
		aYearY.start();

		ValueAnimator a201Y = ObjectAnimator.ofFloat(mTwoZeroOne, "y",
				dpToPx(84),
				(mLayoutParams.getHeight() - mLayoutParams.getWidth()) / 2
						+ dpToPx(84));
		a201Y.setDuration(ANIMATION_DURATION);
		a201Y.start();

		aTitleIconY.start();
		aAmPmY.start();
		aHourY.start();
		aMinuteY.start();
		for (int i = 0; i < 9; i++) {
			aDigitsY[i].start();
		}

	}

	@Override
	public void onClick(View v) {
		for (int i = 0; i < 9; i++) {
			if (v == mDigits[i]) {
				currentDigit = i;
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
		for (int i = 0; i < 9; i++) {
			if (currentDigit == i) {
				mDigits[i].setBackgroundColor(C.COLOR_LIGHTBLUE);
				mDigits[i].setTextColor(C.COLOR_DARKBLUE);
			} else {
				mDigits[i].setBackgroundColor(C.COLOR_DARKBLUE);
				mDigits[i].setTextColor(C.COLOR_LIGHTBLUE);
			}
		}
		if (Integer.valueOf("" + mDigits[0].getText() + mDigits[1].getText()) >= 12) {
			mAmPm.setText(G.context.getString(R.string.p_m_));
		} else {
			mAmPm.setText(G.context.getString(R.string.a_m_));
		}
	}

	@Override
	protected void onKeyDown(int key) {
		int intToBeAdded = key == 9 ? 0 : key + 1;
		switch (currentDigit) {
		case 0:
			if (intToBeAdded > 2) {
				mDigits[0].setText("0");
				currentDigit++;
				mDigits[1].setText(String.valueOf(intToBeAdded));
			} else {
				if (intToBeAdded == 2
						&& (Integer.valueOf((String) mDigits[1].getText()) >= 4)) {
					mDigits[1].setText("0");
				}
				mDigits[0].setText(String.valueOf(intToBeAdded));
			}
			break;
		case 1:
			if (Integer.valueOf((String) mDigits[0].getText()) >= 2
					&& intToBeAdded == 4) {
				mDigits[0].setText("0");
				mDigits[1].setText("0");
			} else if (!((Integer.valueOf((String) mDigits[0].getText()) >= 2) && intToBeAdded > 3)) {
				mDigits[1].setText(String.valueOf(intToBeAdded));
			} else {
				return;
			}
			break;
		case 2:
			if (key >= 5 && key != 9) {
				mDigits[currentDigit].setText("0");
				currentDigit++;
				mDigits[currentDigit].setText(mNumKeys[key].getText());
			} else {
				mDigits[currentDigit].setText(mNumKeys[key].getText());
			}
			break;
		case 4:
			if (intToBeAdded > 1) {
				mDigits[currentDigit].setText("0");
				currentDigit++;
			} else if (intToBeAdded == 1
					&& (Integer.valueOf((String) mDigits[5].getText()) >= 3)) {
				mDigits[5].setText("0");
			}
			mDigits[currentDigit].setText(mNumKeys[key].getText());
			break;
		case 5:
			if (intToBeAdded == 0 && mDigits[4].getText() == "0")
				return;
			if (intToBeAdded >= 3
					&& Integer.valueOf((String) mDigits[4].getText()) >= 1)
				return;
			mDigits[currentDigit].setText(mNumKeys[key].getText());
			break;
		case 6:
			if (intToBeAdded > 3) {
				mDigits[currentDigit].setText("0");
				currentDigit++;
			} else if (intToBeAdded == 3
					&& (Integer.valueOf((String) mDigits[7].getText()) >= 1)) {
				mDigits[7].setText("0");
			}
			mDigits[currentDigit].setText(mNumKeys[key].getText());
			break;
		case 7:
			if (intToBeAdded == 0 && mDigits[6].getText() == "0")
				return;
			if (((Integer.valueOf((String) mDigits[6].getText()) >= 3) && intToBeAdded > 1))
				return;
			mDigits[currentDigit].setText(mNumKeys[key].getText());
			break;

		default:
			mDigits[currentDigit].setText(mNumKeys[key].getText());

		}

		currentDigit++;
		if (currentDigit == 9)
			currentDigit = 0;
		UpdateCurrentDigit();
	}

	@Override
	protected void onOk() {
		addReminder(true);
	}

	@Override
	protected void onCancel() {

	}

	@Override
	protected void addReminder(boolean pushBubble) {
		Reminder newReminder = new Reminder(2);
		newReminder.mTimeToRemind.add(Calendar.YEAR, Integer.valueOf((String) mDigits[8].getText()) 
				- (String.valueOf(newReminder.mTimeToRemind.get(Calendar.YEAR)).charAt(3) - '0'));
		newReminder.mTimeToRemind.set(Calendar.MONTH,
				Integer.valueOf(mDigits[4].getText().toString() + mDigits[5].getText()) - 1);
		newReminder.mTimeToRemind.set(Calendar.DAY_OF_MONTH,
				Integer.valueOf(mDigits[6].getText().toString() + mDigits[7].getText()));
		newReminder.mTimeToRemind.set(Calendar.HOUR_OF_DAY, 
				Integer.valueOf(mDigits[0].getText().toString() + mDigits[1].getText()));
		newReminder.mTimeToRemind.set(Calendar.MINUTE, 
				Integer.valueOf(mDigits[2].getText().toString() + mDigits[3].getText()));
		newReminder.mTimeToRemind.set(Calendar.SECOND, 0);
		newReminder.iPriority = iPriority;
		G.reminders.add(newReminder, pushBubble);
	}

}
