package com.pujoy.charminder.views;

import java.util.Calendar;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;
import com.pujoy.charminder.base.FloatingTimerDialog;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import static com.pujoy.charminder.MainActivity.con;

public class Timer2 extends FloatingTimerDialog implements OnClickListener{
	
	static ImageView ivTitleIcon;
	static TextView tvAmPm;
	static TextView[] tvDigits;
	static TextView tvHour;
	static TextView tvMinute;
	static TextView tvYear;
	static TextView tvMonth;
	static TextView tvDay;
	static TextView tv201;
	
	static int currentDigit;
	
	protected void onInitialize(){
		super.onInitialize(true);
    	tvDigits = new TextView[9];
    	for(int i=0; i<4; i++){
    		tvDigits[i] = new TextView(con);
    		tvDigits[i].setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(40)));
    		tvDigits[i].setX(layoutParams.getWidth()/2
    				- dpToPx(20*2) + dpToPx(20)*i + (i>1?dpToPx(12):0));
    		tvDigits[i].setText("0");
    		tvDigits[i].setGravity(Gravity.CENTER);
    		tvDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    		tvDigits[i].setOnClickListener(this);
    		addToMainView(tvDigits[i]);
    	}
    	for(int i=4; i<9; i++){
    		tvDigits[i] = new TextView(con);
    		tvDigits[i].setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(40)));
    		tvDigits[i].setX(layoutParams.getWidth()/2
    				- dpToPx(20*7) + dpToPx(20)*i + (i>5?dpToPx(12):0) - dpToPx(32) - dpToPx(12));
    		tvDigits[i].setText("0");
    		tvDigits[i].setGravity(Gravity.CENTER);
    		tvDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    		tvDigits[i].setOnClickListener(this);
    		addToMainView(tvDigits[i]);
    	}
    	tvDigits[8].setX(layoutParams.getWidth()/2
				- dpToPx(20*7) + dpToPx(20)*(8+3) + dpToPx(24) - dpToPx(24) - dpToPx(12));
    	Calendar cal = Calendar.getInstance();
    	tvDigits[8].setText(String.valueOf(Integer.valueOf(cal.get(Calendar.YEAR)).toString().charAt(3)));
    	String s = Integer.valueOf(cal.get(Calendar.HOUR_OF_DAY)).toString();
    	if(s.length() == 1){
    		tvDigits[1].setText(s);
    	}else{
    		tvDigits[0].setText(String.valueOf(s.charAt(0)));
    		tvDigits[1].setText(String.valueOf(s.charAt(1)));
    	}
    	s = Integer.valueOf(cal.get(Calendar.MINUTE)).toString();
    	if(s.length() == 1){
    		tvDigits[3].setText(s);
    	}else{
    		tvDigits[2].setText(String.valueOf(s.charAt(0)));
    		tvDigits[3].setText(String.valueOf(s.charAt(1)));
    	}
    	s = Integer.valueOf(cal.get(Calendar.MONTH)+1).toString();
    	if(s.length() == 1){
    		tvDigits[5].setText(s);
    	}else{
    		tvDigits[4].setText(String.valueOf(s.charAt(0)));
    		tvDigits[5].setText(String.valueOf(s.charAt(1)));
    	}
    	s = Integer.valueOf(cal.get(Calendar.DAY_OF_MONTH)).toString();
    	if(s.length() == 1){
    		tvDigits[7].setText(s);
    	}else{
    		tvDigits[6].setText(String.valueOf(s.charAt(0)));
    		tvDigits[7].setText(String.valueOf(s.charAt(1)));
    	}
    	
    	tv201 = new TextView(con);
    	tv201.setLayoutParams(new LayoutParams((int)dpToPx(80), (int)dpToPx(40)));
    	tv201.setX(layoutParams.getWidth()/2
				- dpToPx(20*7) + dpToPx(20)*8 + dpToPx(26) - dpToPx(36) - dpToPx(12));

    	tv201.setText("/201");
    	tv201.setGravity(Gravity.CENTER);
    	tv201.setTextColor(Constants.COLOR_LIGHTBLUE);
    	tv201.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    	addToMainView(tv201);
    	
    	
    	tvMonth = new TextView(con);
    	tvMonth.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvMonth.setX(layoutParams.getWidth()/2
				- dpToPx(20*7) + dpToPx(20)*6 - dpToPx(4) - dpToPx(32) - dpToPx(12));
    	tvMonth.setText(con.getString(R.string.unit_month));
    	tvMonth.setGravity(Gravity.CENTER);
    	tvMonth.setTextColor(Constants.COLOR_LIGHTBLUE);
    	tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(tvMonth);
		
		tvDay = new TextView(con);
    	tvDay.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvDay.setX(layoutParams.getWidth()/2
				- dpToPx(20*7) + dpToPx(20)*8 + dpToPx(8) - dpToPx(32) - dpToPx(12));
    	tvDay.setText(con.getString(R.string.unit_day));
    	tvDay.setGravity(Gravity.CENTER);
    	tvDay.setTextColor(Constants.COLOR_LIGHTBLUE);
    	tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(tvDay);
		
		tvYear = new TextView(con);
    	tvYear.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvYear.setX(layoutParams.getWidth()/2
				- dpToPx(20*7) + dpToPx(20)*8 + dpToPx(66));
    	tvYear.setText(con.getString(R.string.unit_year));
    	tvYear.setGravity(Gravity.CENTER);
    	tvYear.setTextColor(Constants.COLOR_LIGHTBLUE);
    	tvYear.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(tvYear);
    	
    	tvAmPm = new TextView(con);
    	tvAmPm.setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(20)));
    	tvAmPm.setX(layoutParams.getWidth()/2- dpToPx(2) - dpToPx(40) - dpToPx(28));
    	tvAmPm.setText(con.getString(R.string.a_m_));
    	tvAmPm.setGravity(Gravity.CENTER);
    	tvAmPm.setTextColor(Constants.COLOR_LIGHTBLUE);
    	tvAmPm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    	addToMainView(tvAmPm);
    	
    	tvHour = new TextView(con);
    	tvHour.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvHour.setX(layoutParams.getWidth()/2- dpToPx(4));
    	tvHour.setText(con.getString(R.string.unit_hour));
    	tvHour.setGravity(Gravity.CENTER);
    	tvHour.setTextColor(Constants.COLOR_LIGHTBLUE);
    	tvHour.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(tvHour);
    	tvMinute = new TextView(con);
    	tvMinute.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvMinute.setX(layoutParams.getWidth()/2 + dpToPx(20)*2  + dpToPx(8));
    	tvMinute.setText(con.getString(R.string.unit_minute));
    	tvMinute.setGravity(Gravity.CENTER);
    	tvMinute.setTextColor(Constants.COLOR_LIGHTBLUE);
    	tvMinute.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
		addToMainView(tvMinute);
    	

    	currentDigit = 0;
    	UpdateCurrentDigit();

    	
    	ivTitleIcon = new ImageView(con);
    	ivTitleIcon.setImageResource(R.drawable.timer2_icon);
    	ivTitleIcon.setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(32)));
    	ivTitleIcon.setX(layoutParams.getWidth()/2-dpToPx(32)/2);
    	addToMainView(ivTitleIcon);
    	
	}
	
	@Override 
	protected void onCreate(){
	
	ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(ivTitleIcon, "y",
			layoutParams.getHeight()/2 - layoutParams.getWidth()/2 + dpToPx(4), dpToPx(4));
	aTitleIconY.setDuration(500);
   

	ValueAnimator[] aDigitsY = new ValueAnimator[9];
	for(int i=0; i<4; i++){
		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
    			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(40), dpToPx(40));
		aDigitsY[i].setDuration(500);
	}
	
	for(int i=4; i<9; i++){
		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
    			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(84), dpToPx(84));
		aDigitsY[i].setDuration(500);

	}


	ValueAnimator a201Y = ObjectAnimator.ofFloat(tv201, "y",
			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(84), dpToPx(84));
	a201Y.setDuration(500);


	ValueAnimator aYearY = ObjectAnimator.ofFloat(tvYear, "y",
			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(84+18), dpToPx(84+18));
	aYearY.setDuration(500);


	ValueAnimator aMonthY = ObjectAnimator.ofFloat(tvMonth, "y",
			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(84+18), dpToPx(84+18));
	aMonthY.setDuration(500);


	ValueAnimator aDayY = ObjectAnimator.ofFloat(tvDay, "y",
			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(84+18), dpToPx(84+18));
	aDayY.setDuration(500);


	ValueAnimator aAmPmY = ObjectAnimator.ofFloat(tvAmPm, "y",
			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(58), dpToPx(58));
	aAmPmY.setDuration(500);


	ValueAnimator aHourY = ObjectAnimator.ofFloat(tvHour, "y",
			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(58), dpToPx(58));
	aHourY.setDuration(500);
	

	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(tvMinute, "y",
			(layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(58), dpToPx(58));
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
    		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
    				dpToPx(40), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(40));
    		aDigitsY[i].setDuration(500);
    	}
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(ivTitleIcon, "y",
				dpToPx(4), layoutParams.getHeight()/2 - layoutParams.getWidth()/2 + dpToPx(4));
		aTitleIconY.setDuration(500);

    	ValueAnimator aAmPmY = ObjectAnimator.ofFloat(tvAmPm, "y",
    			dpToPx(58), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(58));
    	aAmPmY.setDuration(500);
    	ValueAnimator aHourY = ObjectAnimator.ofFloat(tvHour, "y",
    			dpToPx(58), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(58));
    	aHourY.setDuration(500);
    	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(tvMinute, "y",
    			dpToPx(58), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(58));
    	aMinuteY.setDuration(500);
    	
    	ValueAnimator aMonthY = ObjectAnimator.ofFloat(tvMonth, "y",
    			dpToPx(84+18), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(84+18));
    	aMonthY.setDuration(500);
    	aMonthY.start();

    	ValueAnimator aDayY = ObjectAnimator.ofFloat(tvDay, "y",
    			dpToPx(84+18), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(84+18));
    	aDayY.setDuration(500);
    	aDayY.start();
    	
    	ValueAnimator aYearY = ObjectAnimator.ofFloat(tvYear, "y",
    			dpToPx(84+18), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(84+18));
    	aYearY.setDuration(500);
    	aYearY.start();
    	

    	ValueAnimator a201Y = ObjectAnimator.ofFloat(tv201, "y",
    			dpToPx(84), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(84));
    	a201Y.setDuration(500);
    	a201Y.start();

    	for(int i=4; i<9; i++){
    		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
    				dpToPx(84), (layoutParams.getHeight()-layoutParams.getWidth())/2 + dpToPx(84));
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
		for(int i=0; i<9; i++){
			if(currentDigit == i){
	    		tvDigits[i].setBackgroundColor(Constants.COLOR_LIGHTBLUE);
	    		tvDigits[i].setTextColor(Constants.COLOR_DARKBLUE);
			}else{
	    		tvDigits[i].setBackgroundColor(Constants.COLOR_DARKBLUE);
	    		tvDigits[i].setTextColor(Constants.COLOR_LIGHTBLUE);
			}
		}
		if(Integer.valueOf(""+tvDigits[0].getText()+tvDigits[1].getText())>=12){
			tvAmPm.setText(con.getString(R.string.p_m_));
		}else{
			tvAmPm.setText(con.getString(R.string.a_m_));
		}
	}
	
	
	@Override
	protected void onKeyDown(int key) {
		int intToBeAdded = key==9? 0: key+1;
		switch(currentDigit){
		case 0:
			if(intToBeAdded > 2){
				tvDigits[0].setText("0");
				currentDigit ++;
				tvDigits[1].setText(String.valueOf(intToBeAdded));
			}else{
				if(intToBeAdded == 2 && (Integer.valueOf((String) tvDigits[1].getText()) >=4)){
					tvDigits[1].setText("0");
				}
				tvDigits[0].setText(String.valueOf(intToBeAdded));
			}
			break;
		case 1:
			if(Integer.valueOf((String) tvDigits[0].getText()) >=2 && intToBeAdded == 4){
				tvDigits[0].setText("0");
				tvDigits[1].setText("0");
			}else if(!((Integer.valueOf((String) tvDigits[0].getText()) >=2) && intToBeAdded>3)){
				tvDigits[1].setText(String.valueOf(intToBeAdded));
			}else{
				return;
			}
			break;
		case 2:
			if(key>=5 && key!=9){
				tvDigits[currentDigit].setText("0");
				currentDigit ++;
				tvDigits[currentDigit].setText(tvNumKeys[key].getText());
			}else{
				tvDigits[currentDigit].setText(tvNumKeys[key].getText());
			}
			break;
		case 4:
			if(intToBeAdded>1){
				tvDigits[currentDigit].setText("0");
				currentDigit ++;
			}else if(intToBeAdded == 1 && (Integer.valueOf((String) tvDigits[5].getText()) >=3)){
					tvDigits[5].setText("0");
			}
			tvDigits[currentDigit].setText(tvNumKeys[key].getText());
			break;
		case 5:
			if(intToBeAdded == 0 && tvDigits[4].getText() == "0")
				return;
			if(intToBeAdded >=3 && Integer.valueOf((String) tvDigits[4].getText()) >= 1)
				return;
			tvDigits[currentDigit].setText(tvNumKeys[key].getText());
			break;
		case 6:
			if(intToBeAdded>3){
				tvDigits[currentDigit].setText("0");
				currentDigit ++;
			}else if (intToBeAdded == 3&& (Integer.valueOf((String) tvDigits[7].getText()) >=1)){
				tvDigits[7].setText("0");
			}
			tvDigits[currentDigit].setText(tvNumKeys[key].getText());
			break;
		case 7:
			if(intToBeAdded == 0 && tvDigits[6].getText() == "0")
				return;
			if(((Integer.valueOf((String) tvDigits[6].getText()) >=3) && intToBeAdded>1))
				return;
			tvDigits[currentDigit].setText(tvNumKeys[key].getText());
			break;
			
		default:
			tvDigits[currentDigit].setText(tvNumKeys[key].getText());
			
		}
		
		currentDigit ++;
		if(currentDigit == 9)
			currentDigit = 0;
		UpdateCurrentDigit();
	}


}
