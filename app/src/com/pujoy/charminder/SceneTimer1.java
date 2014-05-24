package com.pujoy.charminder;

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

public class SceneTimer1 extends Scene implements OnClickListener{
	
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
	static TextView[] tvDigits;
	static TextView[] tvNumKeys;
	static ImageView[] ivStar;
	static TextView tvHour;
	static TextView tvMinute;
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
    	ivUpperCircle.setImageResource(R.drawable.semi_circle);
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
    				(int) ((wmParams.height - wmParams.width + wmParams.width*KEYBOARD_AREA)/4+2)));
    		tvNumKeys[i].setX((float)(int) (wmParams.width*BORDER_SCALE +
    				(wmParams.width*NON_BORDER_SCALE/3)*(i%3)-1));
    		tvNumKeys[i].setY((float)(int) (wmParams.width/2- wmParams.width*KEYBOARD_AREA/2 + 
    				(wmParams.height - wmParams.width+ wmParams.width*KEYBOARD_AREA)/4*Math.floor(i/3)-1));
    		tvNumKeys[i].setText(new Integer(i+1).toString());
    		tvNumKeys[i].setGravity(Gravity.CENTER);
    		//tvNumKeys[i].setBackgroundColor(Color.rgb(168, 212, 242));
    		tvNumKeys[i].setTextColor(Color.rgb(48, 78, 98));
    		tvNumKeys[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    		tvNumKeys[i].setOnTouchListener(NumKeysTouchListener);
    		tvNumKeys[i].setOnClickListener(this);
    		vLayout.addView(tvNumKeys[i]);
    	}
    	tvNumKeys[9].setX(wmParams.width*BORDER_SCALE +(wmParams.width*NON_BORDER_SCALE/3)*(10%3));
    	tvNumKeys[9].setText("0");
    	

    	tvDigits = new TextView[4];
    	ValueAnimator[] aDigitsY = new ValueAnimator[4];
    	for(int i=0; i<4; i++){
    		tvDigits[i] = new TextView(c);
    		tvDigits[i].setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(40)));
    		tvDigits[i].setX(wmParams.width/2
    				- dpToPx(20*2) - dpToPx(8) + dpToPx(20)*i + (i>1?dpToPx(12):0));
    		tvDigits[i].setY(dpToPx(36));
    		tvDigits[i].setText("0");
    		tvDigits[i].setGravity(Gravity.CENTER);
    		tvDigits[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    		tvDigits[i].setOnClickListener(this);
    		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
        			(wmParams.height-wmParams.width)/2 + dpToPx(34), dpToPx(34));
    		aDigitsY[i].setDuration(500);
    		vLayout.addView(tvDigits[i]);
    	}
    	currentDigit = 2;
    	UpdateCurrentDigit();
    	tvHour = new TextView(c);
    	tvHour.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvHour.setX(wmParams.width/2- dpToPx(12));
    	tvHour.setText(c.getString(R.string.hour));
    	tvHour.setGravity(Gravity.CENTER);
    	tvHour.setTextColor(Color.rgb(228, 242, 254));
    	tvHour.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    	ValueAnimator aHourY = ObjectAnimator.ofFloat(tvHour, "y",
    			(wmParams.height-wmParams.width)/2 + dpToPx(52), dpToPx(52));
    	aHourY.setDuration(500);
		vLayout.addView(tvHour);
    	tvMinute = new TextView(c);
    	tvMinute.setLayoutParams(new LayoutParams((int)dpToPx(20), (int)dpToPx(20)));
    	tvMinute.setX(wmParams.width/2 + dpToPx(20)*2);
    	tvMinute.setY(dpToPx(52));
    	tvMinute.setText(c.getString(R.string.minute));
    	tvMinute.setGravity(Gravity.CENTER);
    	tvMinute.setTextColor(Color.rgb(228, 242, 254));
    	tvMinute.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
    	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(tvMinute, "y",
    			(wmParams.height-wmParams.width)/2 + dpToPx(52), dpToPx(52));
    	aMinuteY.setDuration(500);
		vLayout.addView(tvMinute);
    	
    	
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
    	
    	vLayout.addView(tvLevel);
    	
    	tvTitle = new TextView(c);
    	tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvTitle.setGravity(Gravity.CENTER);
    	tvTitle.setTextColor(Color.rgb(228, 242, 254));
    	tvTitle.setText(c.getString(R.string.title_timer1));
    	tvTitle.setLayoutParams(new LayoutParams((int)dpToPx(64), (int)dpToPx(28)));
    	tvTitle.setX(wmParams.width/2-dpToPx(40)/2);
    	
    	vLayout.addView(tvTitle);
    	
    	ivTitleIcon = new ImageView(c);
    	ivTitleIcon.setImageResource(R.drawable.timer1_icon);
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
		for(int i=0; i<5; i++){
			aStarX[i].start();
			aStarY[i].start();
		}
		for(int i=0; i<4; i++){
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
    	ValueAnimator[] aDigitsY = new ValueAnimator[4];
    	for(int i=0; i<4; i++){
    		aDigitsY[i] = ObjectAnimator.ofFloat(tvDigits[i], "y",
    				dpToPx(34), (wmParams.height-wmParams.width)/2 + dpToPx(34));
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

    	ValueAnimator aHourY = ObjectAnimator.ofFloat(tvHour, "y",
    			dpToPx(52), (wmParams.height-wmParams.width)/2 + dpToPx(52));
    	aHourY.setDuration(500);
    	ValueAnimator aMinuteY = ObjectAnimator.ofFloat(tvMinute, "y",
    			dpToPx(52), (wmParams.height-wmParams.width)/2 + dpToPx(52));
    	aMinuteY.setDuration(500);
		
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
		aHourY.start();
		aMinuteY.start();
		for(int i=0; i<5; i++){
			aStarX[i].start();
			aStarY[i].start();
		}
		for(int i=0; i<4; i++){
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
		for(int i=0; i<4; i++){
			if(v == tvDigits[i]){
				currentDigit = i;
				UpdateCurrentDigit();
			}
		}
		for(int i=0; i<10; i++){
			if(v == tvNumKeys[i]){
				if(currentDigit == 2 && (i>=5 && i!=9)){
					tvDigits[2].setText("0");
					currentDigit ++;
				}
				tvDigits[currentDigit].setText(tvNumKeys[i].getText());
				currentDigit ++;
				if(currentDigit == 4)
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
    	wmParams.type = 2002;   
    	wmParams.format = 1; 
    	wmParams.flags = 40;  
    	wmParams.width = (int) dpToPx(256);
    	wmParams.height = (int) dpToPx(320);
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
		for(int i=0; i<4; i++){
			if(currentDigit == i){
	    		tvDigits[i].setBackgroundColor(Color.rgb(228, 242, 254));
	    		tvDigits[i].setTextColor(Color.rgb(48, 78, 98));
			}else{
	    		tvDigits[i].setBackgroundColor(Color.rgb(48, 78, 98));
	    		tvDigits[i].setTextColor(Color.rgb(228, 242, 254));
			}
		}
	}
	
	private void Ok(){
		Reminder newReminder = new Reminder(1);
		newReminder.time_to_remind = Calendar.getInstance();
		newReminder.time_to_remind.add(Calendar.HOUR, 
				Integer.valueOf(tvDigits[0].getText().toString()+tvDigits[1].getText()));
		newReminder.time_to_remind.add(Calendar.MINUTE, 
				Integer.valueOf(tvDigits[2].getText().toString()+tvDigits[3].getText()));
		newReminder.level = level;
		newReminder.note = FormatTimeText();
		MainActivity.AddReminder(newReminder);
		MainActivity.PushFloatingBubble(c.getString(R.string.bubble_add_reminder) +
		newReminder.note + c.getString(R.string.bubble_timer1));
	}
	
	private String FormatTimeText(){
		String ret = tvDigits[0].getText().toString()+tvDigits[1].getText();
		if(!(ret).isEmpty()){
			ret += c.getString(R.string.hour);
		}
		String sec = tvDigits[2].getText().toString()+tvDigits[3].getText();
		if(!(sec.isEmpty() || ret.isEmpty())){
			ret += sec + c.getString(R.string.minute);
		}
		return ret;
	}
}
