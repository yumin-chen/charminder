package com.pujoy.charminder.views;

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

public class Timer1 extends WindowTimerDialog implements OnClickListener{
	
	private TextView[] mDigits;
	private ImageView mTitleIcon;
	private TextView mHour;
	private TextView mMinute;
	private int iCurrentDigit;
	
	@Override
	protected void onInitialize(){
		super.onInitialize();
		
    	mDigits = new TextView[4];
    	ValueAnimator[] aDigitsY = new ValueAnimator[4];
    	for(int i=0; i<4; i++){
    		mDigits[i] = new TextView(mCon);
    		mDigits[i].setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(40)));
    		mDigits[i].setX(mLayoutParams.getWidth()/2
    				- dpToPx(20*2) - dpToPx(8) + dpToPx(20)*i + (i>1?dpToPx(12):0));
    		mDigits[i].setY(dpToPx(36));
    		mDigits[i].setText("0");
    		mDigits[i].setGravity(Gravity.CENTER);
    		mDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    		mDigits[i].setOnClickListener(this);
    		aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y",
        			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(34), dpToPx(34));
    		aDigitsY[i].setDuration(ANIMATION_DURATION);
    		addToMainView(mDigits[i]);
    	}
    	iCurrentDigit = 2;
    	UpdateCurrentDigit();
    	mHour = new TextView(mCon);
    	mHour.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	mHour.setX(mLayoutParams.getWidth()/2- dpToPx(12));
    	mHour.setText(mCon.getString(R.string.unit_hour));
    	mHour.setGravity(Gravity.CENTER);
    	mHour.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mHour.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mHour);
    	mMinute = new TextView(mCon);
    	mMinute.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	mMinute.setX(mLayoutParams.getWidth()/2 + dpToPx(20)*2);
    	mMinute.setY(dpToPx(52));
    	mMinute.setText(mCon.getString(R.string.unit_minute));
    	mMinute.setGravity(Gravity.CENTER);
    	mMinute.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mMinute.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(mMinute);
    	
    	
    	mTitleIcon = new ImageView(mCon);
    	mTitleIcon.setImageResource(R.drawable.timer1_icon);
    	mTitleIcon.setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(32)));
    	mTitleIcon.setX(mLayoutParams.getWidth()/2-dpToPx(32)/2);
    	
    	addToMainView(mTitleIcon);
	}
	
	@Override 
	protected void onCreate(){

		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(mTitleIcon, "y",
				mLayoutParams.getHeight()/2 - mLayoutParams.getWidth()/2 + dpToPx(4) , dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);
    	ValueAnimator aHourY = ObjectAnimator.ofFloat(mHour, "y",
    			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(52), dpToPx(52));
    	aHourY.setDuration(ANIMATION_DURATION);
    	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(mMinute, "y",
    			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(52), dpToPx(52));
    	aMinuteY.setDuration(ANIMATION_DURATION);
    	
    	ValueAnimator[] aDigitsY = new ValueAnimator[4];
    	for(int i=0; i<4; i++){
    		aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y",
        			(mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(34), dpToPx(34));
    		aDigitsY[i].setDuration(ANIMATION_DURATION);
    	}

		aTitleIconY.start();
		aHourY.start();
		aMinuteY.start();
		for(int i=0; i<4; i++){
			aDigitsY[i].start();
		}
	}
	
	@Override
	public void onRemove(){


		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(mTitleIcon, "y",
				dpToPx(4), mLayoutParams.getHeight()/2 - mLayoutParams.getWidth()/2 + dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);

    	ValueAnimator aHourY = ObjectAnimator.ofFloat(mHour, "y",
    			dpToPx(52), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(52));
    	aHourY.setDuration(ANIMATION_DURATION);
    	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(mMinute, "y",
    			dpToPx(52), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(52));
    	aMinuteY.setDuration(ANIMATION_DURATION);
    	
    	ValueAnimator[] aDigitsY = new ValueAnimator[4];
    	for(int i=0; i<4; i++){
    		aDigitsY[i] = ObjectAnimator.ofFloat(mDigits[i], "y",
    				dpToPx(34), (mLayoutParams.getHeight()-mLayoutParams.getWidth())/2 + dpToPx(34));
    		aDigitsY[i].setDuration(ANIMATION_DURATION);
    	}
		
		aTitleIconY.start();
		aHourY.start();
		aMinuteY.start();
		for(int i=0; i<4; i++){
			aDigitsY[i].start();
		}
		
	}
	
	public void onClick(View v) {
		
		for(int i=0; i<4; i++){
			if(v == mDigits[i]){
				iCurrentDigit = i;
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
		for(int i=0; i<4; i++){
			if(iCurrentDigit == i){
	    		mDigits[i].setBackgroundColor(Constants.COLOR_LIGHTBLUE);
	    		mDigits[i].setTextColor(Constants.COLOR_DARKBLUE);
			}else{
	    		mDigits[i].setBackgroundColor(Constants.COLOR_DARKBLUE);
	    		mDigits[i].setTextColor(Constants.COLOR_LIGHTBLUE);
			}
		}
	}
	
	@Override
	protected void onOk(){
//		Reminder newReminder = new Reminder(1);
	//	newReminder.time_to_remind = Calendar.getInstance();
	//	newReminder.time_to_remind.add(Calendar.HOUR, 
	//			Integer.valueOf(tvDigits[0].getText().toString()+tvDigits[1].getText()));
	//	newReminder.time_to_remind.add(Calendar.MINUTE, 
	//			Integer.valueOf(tvDigits[2].getText().toString()+tvDigits[3].getText()));
	//	newReminder.level = level;
	//	newReminder.note = FormatTimeText();
	//	MainActivity.AddReminder(newReminder);
	//	MainActivity.PushFloatingBubble(c.getString(R.string.bubble_add_reminder) +
	//	newReminder.note + c.getString(R.string.bubble_timer1));
	}

	@Override
	protected void onCancel() {
		
	}
	
	@Override
	protected void onKeyDown(int i){
		if(iCurrentDigit == 2 && (i>=5 && i!=9)){
			mDigits[2].setText("0");
			iCurrentDigit ++;
		}
		mDigits[iCurrentDigit].setText(mNumKeys[i].getText());
		iCurrentDigit ++;
		if(iCurrentDigit == 4)
			iCurrentDigit = 0;
		UpdateCurrentDigit();
	}
	
	//private String FormatTimeText(){
	//	String ret = tvDigits[0].getText().toString()+tvDigits[1].getText();
	//	if(!(ret).isEmpty()){
	//		ret += con.getString(R.string.unit_hour);
	//	}
	//	String sec = tvDigits[2].getText().toString()+tvDigits[3].getText();
	//	if(!(sec.isEmpty() || ret.isEmpty())){
	//		ret += sec + con.getString(R.string.unit_minute);
	//	}
	//	return ret;
	//}


}
