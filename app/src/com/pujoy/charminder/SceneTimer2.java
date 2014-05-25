package com.pujoy.charminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SceneTimer2 extends Scene implements OnClickListener{
	
	Context c;
	static RelativeLayout vLayout;
	static ImageView ivUpperCircle;
	static ImageView ivMiddleFiller;
	static ImageView ivLowerCircle;
	static ImageView ivTitleIcon;
	static TextView tvTitle;
	static TextView tvOkCircle;
	static TextView tvCancelCircle;
	static TextView tvLevel;
	static TextView tvAmPm;
	static TextView[] tvDigits;
	static TextView[] tvNumKeys;
	static ImageView[] ivStar;
	static TextView tvHour;
	static TextView tvMinute;
	static TextView tvYear;
	static TextView tvMonth;
	static TextView tvDay;
	static TextView tv201;
	
	static WindowManager.LayoutParams wmParams;
	static int currentDigit;
	static int level = 3;
	static boolean bVisible;
	
	
	static final float NON_BORDER_SCALE = 0.960548885077187f;
	static final float BORDER_SCALE = 0.0221843003412969f;
	static final float KEYBOARD_AREA = 0.3720136518771331f;
	
	public void Create(Context c){
		if(bVisible)
			return;
		super.Create(c);
		this.c = c;
		wmParams = new WindowManager.LayoutParams();
		UpdatePosition();
		vLayout = new RelativeLayout(c);
		vLayout.layout(0, 0, wmParams.width, wmParams.height);
    	ivUpperCircle = new ImageView(c);
    	ivLowerCircle = new ImageView(c);
    	ivMiddleFiller = new ImageView(c);
    	ivUpperCircle.setImageResource(R.drawable.semi_circle_full);
    	ivUpperCircle.setLayoutParams(new LayoutParams(wmParams.width, wmParams.width/2));
    	ivMiddleFiller.setImageResource(R.drawable.semi_circle_fill);
    	ivMiddleFiller.setLayoutParams(new LayoutParams(wmParams.width, wmParams.height - wmParams.width));
    	ivMiddleFiller.setY(wmParams.width/2);
    	ivMiddleFiller.setScaleType(ScaleType.FIT_XY);
    	ivLowerCircle.setImageResource(R.drawable.semi_circle_bottom);
    	ivLowerCircle.setLayoutParams(new LayoutParams(wmParams.width, wmParams.width/2));
    	vLayout.addView(ivMiddleFiller);
    	vLayout.addView(ivUpperCircle);
    	vLayout.addView(ivLowerCircle);
    	
    	View.OnTouchListener NumKeysTouchListener = new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(Color.rgb(100, 191, 206));
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(Color.TRANSPARENT);
					break;
				}
				return false;
    	    }
		};
    	
    	tvNumKeys = new TextView[10];
    	for(int i=0; i<10; i++){
    		tvNumKeys[i] = new TextView(c);
    		tvNumKeys[i].setLayoutParams(new LayoutParams(
    				(int) (wmParams.width*NON_BORDER_SCALE/3)+2, 
    				(int) ((wmParams.height - wmParams.width + wmParams.width/2*KEYBOARD_AREA)/4+2)));
    		tvNumKeys[i].setX((float)(int) (wmParams.width*BORDER_SCALE +
    				(wmParams.width*NON_BORDER_SCALE/3)*(i%3)-1));
    		tvNumKeys[i].setY((float)(int) (wmParams.width/2 + 
    				(wmParams.height - wmParams.width+ wmParams.width/2*KEYBOARD_AREA)/4*Math.floor(i/3)-1));
    		tvNumKeys[i].setText(new Integer(i+1).toString());
    		tvNumKeys[i].setGravity(Gravity.CENTER);
    		tvNumKeys[i].setTextColor(Color.rgb(48, 78, 98));
    		tvNumKeys[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    		tvNumKeys[i].setOnTouchListener(NumKeysTouchListener);
    		tvNumKeys[i].setOnClickListener(this);
    		vLayout.addView(tvNumKeys[i]);
    	}
    	tvNumKeys[9].setX(wmParams.width*BORDER_SCALE +(wmParams.width*NON_BORDER_SCALE/3)*(10%3));
    	tvNumKeys[9].setText("0");
    	

    	tvDigits = new TextView[9];
    	ValueAnimator[] aDigitsY = new ValueAnimator[9];
    	for(int i=0; i<4; i++){
    		tvDigits[i] = new TextView(c);
    		tvDigits[i].setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(40)));
    		tvDigits[i].setX(wmParams.width/2
    				- dpToPx(20*2) + dpToPx(20)*i + (i>1?dpToPx(12):0));
    		tvDigits[i].setText("0");
    		tvDigits[i].setGravity(Gravity.CENTER);
    		tvDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    		tvDigits[i].setOnClickListener(this);
    		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
        			(wmParams.height-wmParams.width)/2 + dpToPx(40), dpToPx(40));
    		aDigitsY[i].setDuration(500);
    		vLayout.addView(tvDigits[i]);
    	}
    	for(int i=4; i<9; i++){
    		tvDigits[i] = new TextView(c);
    		tvDigits[i].setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(40)));
    		tvDigits[i].setX(wmParams.width/2
    				- dpToPx(20*7) + dpToPx(20)*i + (i>5?dpToPx(12):0) - dpToPx(32) - dpToPx(12));
    		tvDigits[i].setText("0");
    		tvDigits[i].setGravity(Gravity.CENTER);
    		tvDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    		tvDigits[i].setOnClickListener(this);
    		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
        			(wmParams.height-wmParams.width)/2 + dpToPx(84), dpToPx(84));
    		aDigitsY[i].setDuration(500);
    		vLayout.addView(tvDigits[i]);
    	}
    	tvDigits[8].setX(wmParams.width/2
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
    	
    	
    	
    	
    	tv201 = new TextView(c);
    	tv201.setLayoutParams(new LayoutParams((int)dpToPx(80), (int)dpToPx(40)));
    	tv201.setX(wmParams.width/2
				- dpToPx(20*7) + dpToPx(20)*8 + dpToPx(26) - dpToPx(36) - dpToPx(12));

    	tv201.setText("/201");
    	tv201.setGravity(Gravity.CENTER);
    	tv201.setTextColor(Color.rgb(228, 242, 254));
    	tv201.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    	ValueAnimator a201Y = ObjectAnimator.ofFloat(tv201, "y",
    			(wmParams.height-wmParams.width)/2 + dpToPx(84), dpToPx(84));
    	a201Y.setDuration(500);
		vLayout.addView(tv201);
		a201Y.start();
    	
    	
    	tvMonth = new TextView(c);
    	tvMonth.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvMonth.setX(wmParams.width/2
				- dpToPx(20*7) + dpToPx(20)*6 - dpToPx(4) - dpToPx(32) - dpToPx(12));
    	tvMonth.setText(c.getString(R.string.month));
    	tvMonth.setGravity(Gravity.CENTER);
    	tvMonth.setTextColor(Color.rgb(228, 242, 254));
    	tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    	ValueAnimator aMonthY = ObjectAnimator.ofFloat(tvMonth, "y",
    			(wmParams.height-wmParams.width)/2 + dpToPx(84+18), dpToPx(84+18));
    	aMonthY.setDuration(500);
		vLayout.addView(tvMonth);
		
		tvDay = new TextView(c);
    	tvDay.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvDay.setX(wmParams.width/2
				- dpToPx(20*7) + dpToPx(20)*8 + dpToPx(8) - dpToPx(32) - dpToPx(12));
    	tvDay.setText(c.getString(R.string.day));
    	tvDay.setGravity(Gravity.CENTER);
    	tvDay.setTextColor(Color.rgb(228, 242, 254));
    	tvDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    	ValueAnimator aDayY = ObjectAnimator.ofFloat(tvDay, "y",
    			(wmParams.height-wmParams.width)/2 + dpToPx(84+18), dpToPx(84+18));
    	aDayY.setDuration(500);
		vLayout.addView(tvDay);
		
		tvYear = new TextView(c);
    	tvYear.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvYear.setX(wmParams.width/2
				- dpToPx(20*7) + dpToPx(20)*8 + dpToPx(66));
    	tvYear.setText(c.getString(R.string.year));
    	tvYear.setGravity(Gravity.CENTER);
    	tvYear.setTextColor(Color.rgb(228, 242, 254));
    	tvYear.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    	ValueAnimator aYearY = ObjectAnimator.ofFloat(tvYear, "y",
    			(wmParams.height-wmParams.width)/2 + dpToPx(84+18), dpToPx(84+18));
    	aYearY.setDuration(500);
		vLayout.addView(tvYear);
		aYearY.start();
    	
    	tvAmPm = new TextView(c);
    	tvAmPm.setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(20)));
    	tvAmPm.setX(wmParams.width/2- dpToPx(2) - dpToPx(40) - dpToPx(28));
    	tvAmPm.setText(c.getString(R.string.am));
    	tvAmPm.setGravity(Gravity.CENTER);
    	tvAmPm.setTextColor(Color.rgb(228, 242, 254));
    	tvAmPm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    	ValueAnimator aAmPmY = ObjectAnimator.ofFloat(tvAmPm, "y",
    			(wmParams.height-wmParams.width)/2 + dpToPx(58), dpToPx(58));
    	aAmPmY.setDuration(500);
    	vLayout.addView(tvAmPm);
    	
    	tvHour = new TextView(c);
    	tvHour.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvHour.setX(wmParams.width/2- dpToPx(4));
    	tvHour.setText(c.getString(R.string.hour));
    	tvHour.setGravity(Gravity.CENTER);
    	tvHour.setTextColor(Color.rgb(228, 242, 254));
    	tvHour.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    	ValueAnimator aHourY = ObjectAnimator.ofFloat(tvHour, "y",
    			(wmParams.height-wmParams.width)/2 + dpToPx(58), dpToPx(58));
    	aHourY.setDuration(500);
		vLayout.addView(tvHour);
    	tvMinute = new TextView(c);
    	tvMinute.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvMinute.setX(wmParams.width/2 + dpToPx(20)*2  + dpToPx(8));
    	tvMinute.setText(c.getString(R.string.minute));
    	tvMinute.setGravity(Gravity.CENTER);
    	tvMinute.setTextColor(Color.rgb(228, 242, 254));
    	tvMinute.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(tvMinute, "y",
    			(wmParams.height-wmParams.width)/2 + dpToPx(58), dpToPx(58));
    	aMinuteY.setDuration(500);
		vLayout.addView(tvMinute);
    	

    	currentDigit = 0;
    	UpdateCurrentDigit();
    	
    	
    	View.OnTouchListener OkCancelTouchListener = new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.drawable.small_circle_d);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(R.drawable.small_circle);
					break;
				}
				return false;
    	    }
		};
		
		
    	
    	tvOkCircle = new TextView(c);
    	tvCancelCircle = new TextView(c);
    	tvOkCircle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvCancelCircle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvOkCircle.setGravity(Gravity.CENTER);
    	tvCancelCircle.setGravity(Gravity.CENTER);
    	tvOkCircle.setTextColor(Color.rgb(48, 78, 98));
    	tvCancelCircle.setTextColor(Color.rgb(48, 78, 98));
    	tvOkCircle.setText(c.getString(R.string.ok));
    	tvCancelCircle.setText(c.getString(R.string.cancel));
    	tvOkCircle.setBackgroundResource(R.drawable.small_circle);
    	tvOkCircle.setLayoutParams(new LayoutParams((int)dpToPx(73.39249146757679f), (int)dpToPx(73.39249146757679f)));
    	tvCancelCircle.setBackgroundResource(R.drawable.small_circle);
    	tvCancelCircle.setLayoutParams(new LayoutParams((int)dpToPx(73.39249146757679f), (int)dpToPx(73.39249146757679f)));
    	tvOkCircle.setX(0);
    	tvCancelCircle.setX(wmParams.width - tvCancelCircle.getLayoutParams().width);
    	tvOkCircle.setOnClickListener(this);
    	tvCancelCircle.setOnClickListener(this);
    	tvOkCircle.setOnTouchListener(OkCancelTouchListener);
    	tvCancelCircle.setOnTouchListener(OkCancelTouchListener);
    	vLayout.addView(tvOkCircle);
    	vLayout.addView(tvCancelCircle);
    	
    	
    	ivStar = new ImageView[5];
    	level = 3;
    	ValueAnimator[] aStarX = new ValueAnimator[5];
    	ValueAnimator[] aStarY = new ValueAnimator[5];
    	View.OnTouchListener StarsTouchListener = new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_MOVE:
					for(int i=0; i<5; i++){
						if(motionEvent.getRawX() >= wmParams.x + ivStar[i].getX() &&
								motionEvent.getRawX() < wmParams.x + ivStar[i].getX() + (int)dpToPx(32)){
							level = i+1;
							UpdateStarImage();		
							break;
						}
					}
					break;
				}
				return false;
    	    }
		};
    	for(int i=0; i<5; i++){
    		ivStar[i] = new ImageView(c);
    		ivStar[i].setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(32)));
    		ivStar[i].setOnClickListener(this);
    		ivStar[i].setOnTouchListener(StarsTouchListener);
    		float toX = ((float) (wmParams.width/2 + Math.sin(((120/5) * (i-2)) * Math.PI / 180.0) * (wmParams.width*0.4)
    				- dpToPx(32)/2));
    		float toY = ((float) (wmParams.height-wmParams.width/2 + Math.cos(((120/5) * (i-2)) * Math.PI / 180.0) * (wmParams.width*0.4)
    				- dpToPx(32)/2));

        	aStarX[i] = ObjectAnimator.ofFloat(ivStar[i], "x",
        			wmParams.width/2 - dpToPx(32)/2, toX);
        	aStarX[i].setDuration(500);
        	aStarY[i] = ObjectAnimator.ofFloat(ivStar[i], "y",
        			wmParams.height-wmParams.width/2 - dpToPx(32)/2, toY);
        	aStarY[i].setDuration(500);
    		vLayout.addView(ivStar[i]);
    	}
    	UpdateStarImage();
    	
    	tvLevel = new TextView(c);
    	tvLevel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvLevel.setGravity(Gravity.CENTER);
    	tvLevel.setTextColor(Color.rgb(228, 242, 254));
    	tvLevel.setText(c.getString(R.string.important_level));
    	tvLevel.setLayoutParams(new LayoutParams((int)dpToPx(64), (int)dpToPx(32)));
    	tvLevel.setX(wmParams.width/2-dpToPx(64)/2);
    	tvLevel.setY(wmParams.height-wmParams.width/2+wmParams.width*KEYBOARD_AREA/2+dpToPx(4));
    	tvLevel.setOnTouchListener(StarsTouchListener);
    	vLayout.addView(tvLevel);
    	
    	tvTitle = new TextView(c);
    	tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvTitle.setGravity(Gravity.CENTER);
    	tvTitle.setTextColor(Color.rgb(228, 242, 254));
    	tvTitle.setText(c.getString(R.string.title_timer2));
    	tvTitle.setLayoutParams(new LayoutParams((int)dpToPx(64), (int)dpToPx(28)));
    	tvTitle.setX(wmParams.width/2-dpToPx(40)/2);
    	
    	vLayout.addView(tvTitle);
    	
    	ivTitleIcon = new ImageView(c);
    	ivTitleIcon.setImageResource(R.drawable.timer2_icon);
    	ivTitleIcon.setLayoutParams(new LayoutParams((int)dpToPx(28), (int)dpToPx(28)));
    	ivTitleIcon.setX(wmParams.width/2-dpToPx(40)/2-dpToPx(26));
    	
    	vLayout.addView(ivTitleIcon);
    	
    	MainActivity.wm.addView(vLayout, wmParams);
    	
		ValueAnimator aUpperCicleY = ObjectAnimator.ofFloat(ivUpperCircle, "y",
				wmParams.height/2 - wmParams.width/2, 0);
		aUpperCicleY.setDuration(500);
		ValueAnimator aLowerCicleY = ObjectAnimator.ofFloat(ivLowerCircle, "y",
				wmParams.height/2, wmParams.height - wmParams.width/2);
		aLowerCicleY.setDuration(500);
		ValueAnimator aMiddleFillerHeight = ObjectAnimator.ofFloat(ivMiddleFiller, "scaleY",
				0, 1);
		aMiddleFillerHeight.setDuration(500);
		ValueAnimator aOkCircleY = ObjectAnimator.ofFloat(tvOkCircle, "y",
				wmParams.height/2-tvOkCircle.getLayoutParams().height, 0);
		aOkCircleY.setDuration(500);
		ValueAnimator aCancelCircleY = ObjectAnimator.ofFloat(tvCancelCircle, "y",
				wmParams.height/2-tvCancelCircle.getLayoutParams().height, 0);
		aCancelCircleY.setDuration(500);

		ValueAnimator aTitleY = ObjectAnimator.ofFloat(tvTitle, "y",
				wmParams.height/2 - wmParams.width/2 + dpToPx(10), dpToPx(10));
		aTitleY.setDuration(500);
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(ivTitleIcon, "y",
				wmParams.height/2 - wmParams.width/2 + dpToPx(10), dpToPx(10));
		aTitleIconY.setDuration(500);
		
		aUpperCicleY.start();
		aMiddleFillerHeight.start();   
		aLowerCicleY.start();  
		aOkCircleY.start();
		aCancelCircleY.start();
		aTitleY.start();
		aTitleIconY.start();
		aHourY.start();
		aMinuteY.start();
		aAmPmY.start();
		aMonthY.start();
		aDayY.start();
		for(int i=0; i<5; i++){
			aStarX[i].start();
			aStarY[i].start();
		}
		for(int i=0; i<9; i++){
			aDigitsY[i].start();
		}
		
		bVisible = true;
	}
	public void Remove(){
		if(!bVisible)
			return;
    	ValueAnimator[] aStarX = new ValueAnimator[5];
    	ValueAnimator[] aStarY = new ValueAnimator[5];
    	for(int i=0; i<5; i++){
        	aStarX[i] = ObjectAnimator.ofFloat(ivStar[i], "x",
        			ivStar[i].getX(), wmParams.width/2);
        	aStarX[i].setDuration(500);
        	aStarY[i] = ObjectAnimator.ofFloat(ivStar[i], "y",
        			ivStar[i].getY(), wmParams.height-wmParams.width/2);
        	aStarY[i].setDuration(500);
    	}
    	ValueAnimator[] aDigitsY = new ValueAnimator[9];
    	for(int i=0; i<4; i++){
    		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
    				dpToPx(40), (wmParams.height-wmParams.width)/2 + dpToPx(40));
    		aDigitsY[i].setDuration(500);
    	}
		ValueAnimator aUpperCicleY = ObjectAnimator.ofFloat(ivUpperCircle, "y",
				0, wmParams.height/2 - wmParams.width/2);
		aUpperCicleY.setDuration(500);
		ValueAnimator aLowerCicleY = ObjectAnimator.ofFloat(ivLowerCircle, "y",
				wmParams.height - wmParams.width/2, wmParams.height/2);
		aLowerCicleY.setDuration(500);
		ValueAnimator aMiddleFillerHeight = ObjectAnimator.ofFloat(ivMiddleFiller, "scaleY",
				1, 0);
		aMiddleFillerHeight.setDuration(500);
		ValueAnimator aOkCircleY = ObjectAnimator.ofFloat(tvOkCircle, "y",
				0, wmParams.height/2-tvOkCircle.getLayoutParams().height);
		aOkCircleY.setDuration(500);
		ValueAnimator aCancelCircleY = ObjectAnimator.ofFloat(tvCancelCircle, "y",
				0, wmParams.height/2-tvCancelCircle.getLayoutParams().height);
		aCancelCircleY.setDuration(500);
		ValueAnimator aTitleY = ObjectAnimator.ofFloat(tvTitle, "y",
				dpToPx(10), wmParams.height/2 - wmParams.width/2 + dpToPx(10));
		aTitleY.setDuration(500);
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(ivTitleIcon, "y",
				dpToPx(10), wmParams.height/2 - wmParams.width/2 + dpToPx(10));
		aTitleIconY.setDuration(500);

    	ValueAnimator aAmPmY = ObjectAnimator.ofFloat(tvAmPm, "y",
    			dpToPx(58), (wmParams.height-wmParams.width)/2 + dpToPx(58));
    	aAmPmY.setDuration(500);
    	ValueAnimator aHourY = ObjectAnimator.ofFloat(tvHour, "y",
    			dpToPx(58), (wmParams.height-wmParams.width)/2 + dpToPx(58));
    	aHourY.setDuration(500);
    	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(tvMinute, "y",
    			dpToPx(58), (wmParams.height-wmParams.width)/2 + dpToPx(58));
    	aMinuteY.setDuration(500);
    	
    	ValueAnimator aMonthY = ObjectAnimator.ofFloat(tvMonth, "y",
    			dpToPx(84+18), (wmParams.height-wmParams.width)/2 + dpToPx(84+18));
    	aMonthY.setDuration(500);
    	aMonthY.start();

    	ValueAnimator aDayY = ObjectAnimator.ofFloat(tvDay, "y",
    			dpToPx(84+18), (wmParams.height-wmParams.width)/2 + dpToPx(84+18));
    	aDayY.setDuration(500);
    	aDayY.start();
    	
    	ValueAnimator aYearY = ObjectAnimator.ofFloat(tvYear, "y",
    			dpToPx(84+18), (wmParams.height-wmParams.width)/2 + dpToPx(84+18));
    	aYearY.setDuration(500);
    	aYearY.start();
    	

    	ValueAnimator a201Y = ObjectAnimator.ofFloat(tv201, "y",
    			dpToPx(84), (wmParams.height-wmParams.width)/2 + dpToPx(84));
    	a201Y.setDuration(500);
    	a201Y.start();

    	for(int i=4; i<9; i++){
    		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
    				dpToPx(84), (wmParams.height-wmParams.width)/2 + dpToPx(84));
    		aDigitsY[i].setDuration(500);
    	}
    	
		aUpperCicleY.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            	MainActivity.wm.removeView(vLayout);
        		bVisible = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

			@Override
			public void onAnimationCancel(Animator animation) {
				MainActivity.wm.removeView(vLayout);
				bVisible = false;
			}
        });
		
		
		aUpperCicleY.start();
		aMiddleFillerHeight.start();   
		aLowerCicleY.start();  
		aOkCircleY.start();
		aCancelCircleY.start();
		aTitleY.start();
		aTitleIconY.start();
		aAmPmY.start();
		aHourY.start();
		aMinuteY.start();
		for(int i=0; i<5; i++){
			aStarX[i].start();
			aStarY[i].start();
		}
		for(int i=0; i<9; i++){
			aDigitsY[i].start();
		}

		
		
	}
	
	public void onClick(View v) {
		if(v == tvOkCircle){
			Ok();
			Remove();
		}

		if(v == tvCancelCircle){
			Remove();
		}
		for(int i=0; i<9; i++){
			if(v == tvDigits[i]){
				currentDigit = i;
				UpdateCurrentDigit();
			}
		}
		for(int i=0; i<10; i++){
			if(v == tvNumKeys[i]){
				int intToBeAdded = i==9? 0: i+1;
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
					if(i>=5 && i!=9){
						tvDigits[currentDigit].setText("0");
						currentDigit ++;
						tvDigits[currentDigit].setText(tvNumKeys[i].getText());
					}else{
						tvDigits[currentDigit].setText(tvNumKeys[i].getText());
					}
					break;
				case 4:
					if(intToBeAdded>1){
						tvDigits[currentDigit].setText("0");
						currentDigit ++;
					}else if(intToBeAdded == 1 && (Integer.valueOf((String) tvDigits[5].getText()) >=3)){
							tvDigits[5].setText("0");
					}
					tvDigits[currentDigit].setText(tvNumKeys[i].getText());
					break;
				case 5:
					if(intToBeAdded == 0 && tvDigits[4].getText() == "0")
						return;
					if(intToBeAdded >=3 && Integer.valueOf((String) tvDigits[4].getText()) >= 1)
						return;
					tvDigits[currentDigit].setText(tvNumKeys[i].getText());
					break;
				case 6:
					if(intToBeAdded>3){
						tvDigits[currentDigit].setText("0");
						currentDigit ++;
					}else if (intToBeAdded == 3&& (Integer.valueOf((String) tvDigits[7].getText()) >=1)){
						tvDigits[7].setText("0");
					}
					tvDigits[currentDigit].setText(tvNumKeys[i].getText());
					break;
				case 7:
					if(intToBeAdded == 0 && tvDigits[6].getText() == "0")
						return;
					if(((Integer.valueOf((String) tvDigits[6].getText()) >=3) && intToBeAdded>1))
						return;
					tvDigits[currentDigit].setText(tvNumKeys[i].getText());
					break;
					
				default:
					tvDigits[currentDigit].setText(tvNumKeys[i].getText());
					
				}
				
				currentDigit ++;
				if(currentDigit == 9)
					currentDigit = 0;
				UpdateCurrentDigit();
			}
		}
		for(int i=0; i<5; i++){
			if(v == ivStar[i]){
				level = i+1;
				UpdateStarImage();
			}
		}
	}
	
	private void UpdatePosition(){
    	wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;   
    	wmParams.format = android.graphics.PixelFormat.RGBA_8888; 
    	wmParams.flags = 40;  
    	wmParams.width = (int) dpToPx(256);
    	wmParams.height = (int) dpToPx(360);
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.alpha = 0.95f;
        wmParams.x = (metrics.widthPixels-wmParams.width)/2;
        wmParams.y = (metrics.heightPixels-wmParams.height)/2;   
	}
	private void UpdateStarImage(){
    	for(int i=0; i<5; i++){
    		if(level<=i){
        		ivStar[i].setImageResource(R.drawable.star0);
    		}else{
    			ivStar[i].setImageResource(R.drawable.star1);
    		}
    	}
	}
	private void UpdateCurrentDigit(){
		for(int i=0; i<9; i++){
			if(currentDigit == i){
	    		tvDigits[i].setBackgroundColor(Color.rgb(228, 242, 254));
	    		tvDigits[i].setTextColor(Color.rgb(48, 78, 98));
			}else{
	    		tvDigits[i].setBackgroundColor(Color.rgb(48, 78, 98));
	    		tvDigits[i].setTextColor(Color.rgb(228, 242, 254));
			}
		}
		if(Integer.valueOf(""+tvDigits[0].getText()+tvDigits[1].getText())>=12){
			tvAmPm.setText(c.getString(R.string.pm));
		}else{
			tvAmPm.setText(c.getString(R.string.am));
		}
	}
	
	private void Ok(){
		Reminder newReminder = new Reminder(2);
		String sTimeToRemind = "1"+ tvDigits[8].getText() + ":" + 
				tvDigits[4].getText() + tvDigits[5].getText() + ":" +
				tvDigits[6].getText() + tvDigits[7].getText() + ":" +
				tvDigits[0].getText() + tvDigits[1].getText() + ":" +
				tvDigits[2].getText() + tvDigits[3].getText();
		SimpleDateFormat df = new SimpleDateFormat("yy:MM:dd:HH:mm");
		Date d;
		try {
			d = df.parse(sTimeToRemind);
			newReminder.time_to_remind = Calendar.getInstance();
			newReminder.time_to_remind.set(Calendar.SECOND, 0);
			newReminder.time_to_remind.setTime(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MainActivity.PushFloatingBubble(c.getString(R.string.bubble_error));
		}
		newReminder.note = newReminder.time_to_remind.get(Calendar.MONTH) + c.getString(R.string.month)
				+ newReminder.time_to_remind.get(Calendar.DAY_OF_MONTH) + c.getString(R.string.day)
				+ newReminder.time_to_remind.get(Calendar.HOUR_OF_DAY) + c.getString(R.string.hour)
				+ newReminder.time_to_remind.get(Calendar.MINUTE) + c.getString(R.string.minute);
		newReminder.level = level;
		MainActivity.AddReminder(newReminder);
		MainActivity.PushFloatingBubble(c.getString(R.string.bubble_add_reminder) +
				newReminder.note + c.getString(R.string.bubble_timer2));
	}

	
}
