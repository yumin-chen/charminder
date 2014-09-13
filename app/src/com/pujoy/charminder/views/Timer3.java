package com.pujoy.charminder.views;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;
import com.pujoy.charminder.base.FloatingTimerDialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import static com.pujoy.charminder.MainActivity.con;

public class Timer3 extends FloatingTimerDialog implements OnClickListener{
	
	private TextView[] tvDigits;
	private ImageView ivTitleIcon;
	private TextView tvHour;
	private TextView tvMinute;
	private int currentDigit;
	
	@Override
	protected void onInitialize(){
		super.onInitialize();

    	tvDigits = new TextView[2];
    	for(int i=0; i<2; i++){
    		tvDigits[i] = new TextView(con);
    		tvDigits[i].setLayoutParams(new LayoutParams((int)dpToPx(28), (int)dpToPx(40)));
    		tvDigits[i].setX(layoutParams.getWidth()/2
    				- dpToPx(16) + dpToPx(28)*i);
    		tvDigits[i].setY(dpToPx(36));
    		tvDigits[i].setText("0");
    		tvDigits[i].setGravity(Gravity.CENTER);
    		tvDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    		tvDigits[i].setOnClickListener(this);
    		addToMainView(tvDigits[i]);
    	}
    	currentDigit = 0;
    	UpdateCurrentDigit();
    	tvHour = new TextView(con);
    	tvHour.setLayoutParams(new LayoutParams((int)dpToPx(48), (int)dpToPx(20)));
    	tvHour.setX(layoutParams.getWidth()/2- dpToPx(62));
    	tvHour.setText(con.getString(R.string.unit_everyhour));
    	tvHour.setGravity(Gravity.CENTER);
    	tvHour.setTextColor(Color.rgb(228, 242, 254));
    	tvHour.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

		addToMainView(tvHour);
    	tvMinute = new TextView(con);
    	tvMinute.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvMinute.setX(layoutParams.getWidth()/2 + dpToPx(20)*2);
    	tvMinute.setY(dpToPx(52));
    	tvMinute.setText(con.getString(R.string.unit_minute));
    	tvMinute.setGravity(Gravity.CENTER);
    	tvMinute.setTextColor(Color.rgb(228, 242, 254));
    	tvMinute.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

		addToMainView(tvMinute);
    	
    	ivTitleIcon = new ImageView(con);
    	ivTitleIcon.setImageResource(R.drawable.timer3_icon);
    	ivTitleIcon.setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(32)));
    	ivTitleIcon.setX(layoutParams.getWidth()/2-dpToPx(32)/2);
    	
    	addToMainView(ivTitleIcon);
	}
	
	@Override 
	protected void onCreate(){
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(ivTitleIcon, "y",
				layoutParams.getHeight()/2 - layoutParams.getWidth()/2 + dpToPx(4), dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);
    	ValueAnimator aHourY = ObjectAnimator.ofFloat(tvHour, "y",
    			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(52), dpToPx(52));
    	aHourY.setDuration(ANIMATION_DURATION);

    	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(tvMinute, "y",
    			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(52), dpToPx(52));
    	aMinuteY.setDuration(ANIMATION_DURATION);
    	
    	ValueAnimator[] aDigitsY = new ValueAnimator[2];
    	for(int i=0; i<2; i++){
    		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
        			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(34), dpToPx(34));
    		aDigitsY[i].setDuration(ANIMATION_DURATION);
    	}
    	
		aTitleIconY.start();
		aHourY.start();
		aMinuteY.start();
		for(int i=0; i<2; i++){
			aDigitsY[i].start();
		}
	}
	
	@Override
	public void onRemove(){

		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(ivTitleIcon, "y",
				dpToPx(4), layoutParams.getHeight()/2 - layoutParams.getWidth()/2 + dpToPx(4));
		aTitleIconY.setDuration(ANIMATION_DURATION);

		ValueAnimator aHourY = ObjectAnimator.ofFloat(tvHour, "y",
    			dpToPx(52), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(52));
    	aHourY.setDuration(ANIMATION_DURATION);
    	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(tvMinute, "y",
    			dpToPx(52), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(52));
    	aMinuteY.setDuration(ANIMATION_DURATION);
    	
    	ValueAnimator[] aDigitsY = new ValueAnimator[2];
    	for(int i=0; i<2; i++){
    		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
    				dpToPx(34), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(34));
    		aDigitsY[i].setDuration(ANIMATION_DURATION);
    	}
		
		aTitleIconY.start();
		aHourY.start();
		aMinuteY.start();
		for(int i=0; i<2; i++){
			aDigitsY[i].start();
		}
		
	}
	
	public void onClick(View v) {
		
		for(int i=0; i<2; i++){
			if(v == tvDigits[i]){
				currentDigit = i;
				UpdateCurrentDigit();
			}
		}

		
		
	}
	
	@Override
	protected void onUpdateLayout(){
    	layoutParams.setWidth((int) dpToPx(256));
    	layoutParams.setHeight((int) dpToPx(320));
        layoutParams.x = (getScreenWidth()-layoutParams.getWidth())/2;
        layoutParams.y = (getScreenHeight()-layoutParams.getHeight())/2;   
        layoutParams.alpha = 0.95f;
	}

	private void UpdateCurrentDigit(){
		for(int i=0; i<2; i++){
			if(currentDigit == i){
	    		tvDigits[i].setBackgroundColor(Constants.COLOR_LIGHTBLUE);
	    		tvDigits[i].setTextColor(Constants.COLOR_DARKBLUE);
			}else{
	    		tvDigits[i].setBackgroundColor(Constants.COLOR_DARKBLUE);
	    		tvDigits[i].setTextColor(Constants.COLOR_LIGHTBLUE);
			}
		}
	}
	
	@Override
	protected void onOk(){
	}

	@Override
	protected void onCancel() {
		
	}
	
	@Override
	protected void onKeyDown(int i){
		if(currentDigit == 0 && (i>=5 && i!=9)){
			tvDigits[0].setText("0");
			currentDigit ++;
		}
		tvDigits[currentDigit].setText(tvNumKeys[i].getText());
		currentDigit ++;
		if(currentDigit == 2)
			currentDigit = 0;
		UpdateCurrentDigit();
	}
	


}
