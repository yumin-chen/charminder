package com.pujoy.charminder.views;

import java.util.Calendar;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;
import com.pujoy.charminder.base.WindowTimerDialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import static com.pujoy.charminder.MainActivity.mCon;

public class Timer2 extends WindowTimerDialog implements OnClickListener{
	
	static ImageView mTitleIcon;
	static TextView mAmPm;
	static TextView[] mDigits;
	static TextView mHour;
	static TextView mMinute;
	static TextView mYear;
	static TextView mMonth;
	static TextView mDay;
	static TextView mTwoZeroOne;
	
	static int currentDigit;
	
	protected void onInitialize(){
		super.onInitialize(true);
    	mDigits = new TextView[9];
    	for(int i=0; i<4; i++){
    		mDigits[i] = new TextView(mCon);
    		mDigits[i].setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(40)));
    		mDigits[i].setX(mLayoutParams.getWidth()/2
    				- dpToPx(20*2) + dpToPx(20)*i + (i>1?dpToPx(12):0));
    		mDigits[i].setText("0");
    		mDigits[i].setGravity(Gravity.CENTER);
    		mDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    		mDigits[i].setOnClickListener(this);
    		addToMainView(mDigits[i]);
    	}
    	for(int i=4; i<9; i++){
    		mDigits[i] = new TextView(mCon);
    		mDigits[i].setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(40)));
    		mDigits[i].setX(mLayoutParams.getWidth()/2
    				- dpToPx(20*7) + dpToPx(20)*i + (i>5?dpToPx(12):0) - dpToPx(32) - dpToPx(12));
    		mDigits[i].setText("0");
    		mDigits[i].setGravity(Gravity.CENTER);
    		mDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    		mDigits[i].setOnClickListener(this);
    		addToMainView(mDigits[i]);
    	}
    	mDigits[8].setX(mLayoutParams.getWidth()/2
				- dpToPx(20*7) + dpToPx(20)*(8+3) + dpToPx(24) - dpToPx(24) - dpToPx(12));
    	Calendar cal = Calendar.getInstance();
    	mDigits[8].setText(String.valueOf(Integer.valueOf(cal.get(Calendar.YEAR)).toString().charAt(3)));
    	String s = Integer.valueOf(cal.get(Calendar.HOUR_OF_DAY)).toString();
    	if(s.length() == 1){
    		mDigits[1].setText(s);
    	}else{
    		mDigits[0].setText(String.valueOf(s.charAt(0)));
    		mDigits[1].setText(String.valueOf(s.charAt(1)));
    	}
    	s = Integer.valueOf(cal.get(Calendar.MINUTE)).toString();
    	if(s.length() == 1){
    		mDigits[3].setText(s);
    	}else{
    		mDigits[2].setText(String.valueOf(s.charAt(0)));
    		mDigits[3].setText(String.valueOf(s.charAt(1)));
    	}
    	s = Integer.valueOf(cal.get(Calendar.MONTH)+1).toString();
    	if(s.length() == 1){
    		mDigits[5].setText(s);
    	}else{
    		mDigits[4].setText(String.valueOf(s.charAt(0)));
    		mDigits[5].setText(String.valueOf(s.charAt(1)));
    	}
    	s = Integer.valueOf(cal.get(Calendar.DAY_OF_MONTH)).toString();
    	if(s.length() == 1){
    		mDigits[7].setText(s);
    	}else{
    		mDigits[6].setText(String.valueOf(s.charAt(0)));
    		mDigits[7].setText(String.valueOf(s.charAt(1)));
    	}
    	
    	mTwoZeroOne = new TextView(mCon);
    	mTwoZeroOne.setLayoutParams(new LayoutParams((int)dpToPx(80), (int)dpToPx(40)));
    	mTwoZeroOne.setX(mLayoutParams.getWidth()/2
				- dpToPx(20*7) + dpToPx(20)*8 + dpToPx(26) - dpToPx(36) - dpToPx(12));

    	mTwoZeroOne.setText("/201");
    	mTwoZeroOne.setGravity(Gravity.CENTER);
    	mTwoZeroOne.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mTwoZeroOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    	addToMainView(mTwoZeroOne);
    	
    	
    	mMonth = new TextView(mCon);
    	mMonth.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	mMonth.setX(mLayoutParams.getWidth()/2
				- dpToPx(20*7) + dpToPx(20)*6 - dpToPx(4) - dpToPx(32) - dpToPx(12));
    	mMonth.setText(mCon.getString(R.string.unit_month));
    	mMonth.setGravity(Gravity.CENTER);
    	mMonth.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mMonth);
		
		mDay = new TextView(mCon);
    	mDay.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	mDay.setX(mLayoutParams.getWidth()/2
				- dpToPx(20*7) + dpToPx(20)*8 + dpToPx(8) - dpToPx(32) - dpToPx(12));
    	mDay.setText(mCon.getString(R.string.unit_day));
    	mDay.setGravity(Gravity.CENTER);
    	mDay.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mDay);
		
		mYear = new TextView(mCon);
    	mYear.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	mYear.setX(mLayoutParams.getWidth()/2
				- dpToPx(20*7) + dpToPx(20)*8 + dpToPx(66));
    	mYear.setText(mCon.getString(R.string.unit_year));
    	mYear.setGravity(Gravity.CENTER);
    	mYear.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mYear.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mYear);
    	
    	mAmPm = new TextView(mCon);
    	mAmPm.setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(20)));
    	mAmPm.setX(mLayoutParams.getWidth()/2- dpToPx(2) - dpToPx(40) - dpToPx(28));
    	mAmPm.setText(mCon.getString(R.string.a_m_));
    	mAmPm.setGravity(Gravity.CENTER);
    	mAmPm.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mAmPm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    	addToMainView(mAmPm);
    	
    	mHour = new TextView(mCon);
    	mHour.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	mHour.setX(mLayoutParams.getWidth()/2- dpToPx(4));
    	mHour.setText(mCon.getString(R.string.unit_hour));
    	mHour.setGravity(Gravity.CENTER);
    	mHour.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mHour.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mHour);
    	mMinute = new TextView(mCon);
    	mMinute.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	mMinute.setX(mLayoutParams.getWidth()/2 + dpToPx(20)*2  + dpToPx(8));
    	mMinute.setText(mCon.getString(R.string.unit_minute));
    	mMinute.setGravity(Gravity.CENTER);
    	mMinute.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mMinute.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mMinute);
    	

    	currentDigit = 0;
    	UpdateCurrentDigit();

    	
    	mTitleIcon = new ImageView(mCon);
    	mTitleIcon.setImageResource(R.drawable.timer2_icon);
    	mTitleIcon.setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(32)));
    	mTitleIcon.setX(mLayoutParams.getWidth()/2-dpToPx(32)/2);
    	addToMainView(mTitleIcon);
    	
	}
	
	@Override 
	protected void onCreate(){
	
	ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(mTitleIcon, "y",
			mLayoutParams.getHeight()/2 - mLayoutParams.getWidth()/2 + dpToPx(4), dpToPx(4));
	aTitleIconY.setDuration(500);
   

	ValueAnimator[] aDigitsY = new ValueAnimator[9];
	for(int i=0; i<4; i++){
		aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y",
    			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(40), dpToPx(40));
		aDigitsY[i].setDuration(500);
	}
	
	for(int i=4; i<9; i++){
		aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y",
    			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(84), dpToPx(84));
		aDigitsY[i].setDuration(500);

	}


	ValueAnimator a201Y = ObjectAnimator.ofFloat(mTwoZeroOne, "y",
			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(84), dpToPx(84));
	a201Y.setDuration(500);


	ValueAnimator aYearY = ObjectAnimator.ofFloat(mYear, "y",
			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(84+18), dpToPx(84+18));
	aYearY.setDuration(500);


	ValueAnimator aMonthY = ObjectAnimator.ofFloat(mMonth, "y",
			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(84+18), dpToPx(84+18));
	aMonthY.setDuration(500);


	ValueAnimator aDayY = ObjectAnimator.ofFloat(mDay, "y",
			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(84+18), dpToPx(84+18));
	aDayY.setDuration(500);


	ValueAnimator aAmPmY = ObjectAnimator.ofFloat(mAmPm, "y",
			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(58), dpToPx(58));
	aAmPmY.setDuration(500);


	ValueAnimator aHourY = ObjectAnimator.ofFloat(mHour, "y",
			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(58), dpToPx(58));
	aHourY.setDuration(500);
	

	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(mMinute, "y",
			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(58), dpToPx(58));
	aMinuteY.setDuration(500);
	
		aTitleIconY.start();
		a201Y.start();
		aYearY.start();
		aMonthY.start();
		aDayY.start();
		aHourY.start();
		aMinuteY.start();
		aAmPmY.start();
		for(int i=0; i<9; i++){
			aDigitsY[i].start();
		}
		
	}
	
	@Override
	public void onRemove(){
    	ValueAnimator[] aDigitsY = new ValueAnimator[9];
    	for(int i=0; i<4; i++){
    		aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y",
    				dpToPx(40), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(40));
    		aDigitsY[i].setDuration(500);
    	}
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(mTitleIcon, "y",
				dpToPx(4), mLayoutParams.getHeight()/2 - mLayoutParams.getWidth()/2 + dpToPx(4));
		aTitleIconY.setDuration(500);

    	ValueAnimator aAmPmY = ObjectAnimator.ofFloat(mAmPm, "y",
    			dpToPx(58), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(58));
    	aAmPmY.setDuration(500);
    	ValueAnimator aHourY = ObjectAnimator.ofFloat(mHour, "y",
    			dpToPx(58), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(58));
    	aHourY.setDuration(500);
    	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(mMinute, "y",
    			dpToPx(58), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(58));
    	aMinuteY.setDuration(500);
    	
    	ValueAnimator aMonthY = ObjectAnimator.ofFloat(mMonth, "y",
    			dpToPx(84+18), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(84+18));
    	aMonthY.setDuration(500);
    	aMonthY.start();

    	ValueAnimator aDayY = ObjectAnimator.ofFloat(mDay, "y",
    			dpToPx(84+18), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(84+18));
    	aDayY.setDuration(500);
    	aDayY.start();
    	
    	ValueAnimator aYearY = ObjectAnimator.ofFloat(mYear, "y",
    			dpToPx(84+18), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(84+18));
    	aYearY.setDuration(500);
    	aYearY.start();
    	

    	ValueAnimator a201Y = ObjectAnimator.ofFloat(mTwoZeroOne, "y",
    			dpToPx(84), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(84));
    	a201Y.setDuration(500);
    	a201Y.start();

    	for(int i=4; i<9; i++){
    		aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y",
    				dpToPx(84), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(84));
    		aDigitsY[i].setDuration(500);
    	}
    	

		aTitleIconY.start();
		aAmPmY.start();
		aHourY.start();
		aMinuteY.start();
		for(int i=0; i<9; i++){
			aDigitsY[i].start();
		}
		
	}
	
	public void onClick(View v) {
		for(int i=0; i<4; i++){
			if(v == mDigits[i]){
				currentDigit = i;
				UpdateCurrentDigit();
			}
		}
	}
	
	@Override
	protected void onUpdateLayout(){
    	mLayoutParams.setWidth((int) dpToPx(256));
    	mLayoutParams.setHeight((int) dpToPx(320));
        mLayoutParams.x = (getScreenWidth()-mLayoutParams.getWidth())/2;
        mLayoutParams.y = (getScreenHeight()-mLayoutParams.getHeight())/2;   
        mLayoutParams.alpha = 0.95f;
	}
	
	private void UpdateCurrentDigit(){
		for(int i=0; i<9; i++){
			if(currentDigit == i){
	    		mDigits[i].setBackgroundColor(Constants.COLOR_LIGHTBLUE);
	    		mDigits[i].setTextColor(Constants.COLOR_DARKBLUE);
			}else{
	    		mDigits[i].setBackgroundColor(Constants.COLOR_DARKBLUE);
	    		mDigits[i].setTextColor(Constants.COLOR_LIGHTBLUE);
			}
		}
		if(Integer.valueOf(""+mDigits[0].getText()+mDigits[1].getText())>=12){
			mAmPm.setText(mCon.getString(R.string.p_m_));
		}else{
			mAmPm.setText(mCon.getString(R.string.a_m_));
		}
	}
	
	
	@Override
	protected void onKeyDown(int key) {
		int intToBeAdded = key==9? 0: key+1;
		switch(currentDigit){
		case 0:
			if(intToBeAdded > 2){
				mDigits[0].setText("0");
				currentDigit ++;
				mDigits[1].setText(String.valueOf(intToBeAdded));
			}else{
				if(intToBeAdded == 2 && (Integer.valueOf((String) mDigits[1].getText()) >=4)){
					mDigits[1].setText("0");
				}
				mDigits[0].setText(String.valueOf(intToBeAdded));
			}
			break;
		case 1:
			if(Integer.valueOf((String) mDigits[0].getText()) >=2 && intToBeAdded == 4){
				mDigits[0].setText("0");
				mDigits[1].setText("0");
			}else if(!((Integer.valueOf((String) mDigits[0].getText()) >=2) && intToBeAdded>3)){
				mDigits[1].setText(String.valueOf(intToBeAdded));
			}else{
				return;
			}
			break;
		case 2:
			if(key>=5 && key!=9){
				mDigits[currentDigit].setText("0");
				currentDigit ++;
				mDigits[currentDigit].setText(mNumKeys[key].getText());
			}else{
				mDigits[currentDigit].setText(mNumKeys[key].getText());
			}
			break;
		case 4:
			if(intToBeAdded>1){
				mDigits[currentDigit].setText("0");
				currentDigit ++;
			}else if(intToBeAdded == 1 && (Integer.valueOf((String) mDigits[5].getText()) >=3)){
					mDigits[5].setText("0");
			}
			mDigits[currentDigit].setText(mNumKeys[key].getText());
			break;
		case 5:
			if(intToBeAdded == 0 && mDigits[4].getText() == "0")
				return;
			if(intToBeAdded >=3 && Integer.valueOf((String) mDigits[4].getText()) >= 1)
				return;
			mDigits[currentDigit].setText(mNumKeys[key].getText());
			break;
		case 6:
			if(intToBeAdded>3){
				mDigits[currentDigit].setText("0");
				currentDigit ++;
			}else if (intToBeAdded == 3&& (Integer.valueOf((String) mDigits[7].getText()) >=1)){
				mDigits[7].setText("0");
			}
			mDigits[currentDigit].setText(mNumKeys[key].getText());
			break;
		case 7:
			if(intToBeAdded == 0 && mDigits[6].getText() == "0")
				return;
			if(((Integer.valueOf((String) mDigits[6].getText()) >=3) && intToBeAdded>1))
				return;
			mDigits[currentDigit].setText(mNumKeys[key].getText());
			break;
			
		default:
			mDigits[currentDigit].setText(mNumKeys[key].getText());
			
		}
		
		currentDigit ++;
		if(currentDigit == 9)
			currentDigit = 0;
		UpdateCurrentDigit();
	}

	@Override
	protected void onOk() {
		
	}

	@Override
	protected void onCancel() {
		
	}


}
