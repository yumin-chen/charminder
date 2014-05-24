package com.pujoy.charminder;

import java.util.Calendar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SceneTimer4 extends Activity implements OnClickListener{

	static DisplayMetrics metrics;
	static RelativeLayout vLayout;
	static ImageView ivUpperCircle;
	static ImageView ivMiddleFiller;
	static ImageView ivLowerCircle;
	static ImageView ivTitleIcon;
	static ImageView ivDateIcon;
	static ImageView ivTimeIcon;
	static ImageView ivContentIcon;
	static TextView tvDate;
	static TextView tvTime;
	static EditText etContent;
	static TextView tvTitle;
	static TextView tvOkCircle;
	static TextView tvCancelCircle;
	static TextView tvLevel;
	static ImageView[] ivStar;
	static WindowManager.LayoutParams wmParams;
	static Calendar remindingTime;
	static int currentDigit;
	static int level = 3;
	static boolean bVisible;
	
	
	static final float NON_BORDER_SCALE = 0.960548885077187f;
	static final float BORDER_SCALE = 0.0221843003412969f;
	static final float CENTER_AREA = 0.3720136518771331f;
	protected void onCreate(Bundle savedInstanceState) {
		if(bVisible)
			return;
        super.onCreate(savedInstanceState);
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		wmParams = new WindowManager.LayoutParams();
		UpdatePosition();
		remindingTime = Calendar.getInstance();
		vLayout = new RelativeLayout(this);
		vLayout.layout(0, 0, wmParams.width, wmParams.height);
		
		getWindow().setBackgroundDrawable(null);
		getWindow().set
		
    	ivUpperCircle = new ImageView(this);
    	ivLowerCircle = new ImageView(this);
    	ivMiddleFiller = new ImageView(this);
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
    	

    	
    	ivDateIcon = new ImageView(this);
    	ivDateIcon.setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(32)));
    	ivDateIcon.setImageResource(R.drawable.calendar_b);
    	ivDateIcon.setY(wmParams.width/2 - wmParams.width*CENTER_AREA/2 + dpToPx(4));
    	ivDateIcon.setX(dpToPx(24));
    	vLayout.addView(ivDateIcon);
    	
    	ivTimeIcon = new ImageView(this);
    	ivTimeIcon.setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(32)));
    	ivTimeIcon.setImageResource(R.drawable.time_b);
    	ivTimeIcon.setY(wmParams.width/2 - wmParams.width*CENTER_AREA/2 + dpToPx(4) + dpToPx(36));
    	ivTimeIcon.setX(dpToPx(24));
    	vLayout.addView(ivTimeIcon);
    	
    	ivContentIcon = new ImageView(this);
    	ivContentIcon.setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(32)));
    	ivContentIcon.setImageResource(R.drawable.content_b);
    	ivContentIcon.setY(wmParams.width/2 - wmParams.width*CENTER_AREA/2 + dpToPx(4) + dpToPx(36) + dpToPx(36));
    	ivContentIcon.setX(dpToPx(24));
    	vLayout.addView(ivContentIcon);
    	
    	tvDate = new TextView(this);
    	tvDate.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
    	tvDate.setTextColor(Color.rgb(48, 78, 98));
    	tvDate.setText(this.getString(R.string.date_));
    	tvDate.setLayoutParams(new LayoutParams((int)dpToPx(128), (int)dpToPx(32)));
    	tvDate.setX(dpToPx(24) + (int)dpToPx(32) );
    	tvDate.setY(wmParams.width/2 - wmParams.width*CENTER_AREA/2 + dpToPx(6));
    	vLayout.addView(tvDate);
    	
    	tvTime = new TextView(this);
    	tvTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
    	tvTime.setTextColor(Color.rgb(48, 78, 98));
    	tvTime.setText(this.getString(R.string.time_));
    	tvTime.setLayoutParams(new LayoutParams((int)dpToPx(128), (int)dpToPx(32)));
    	tvTime.setX(dpToPx(24) + (int)dpToPx(32) );
    	tvTime.setY(wmParams.width/2 - wmParams.width*CENTER_AREA/2 + dpToPx(6) + dpToPx(36));
    	vLayout.addView(tvTime);
    	
    	etContent = new EditText(this);
    	etContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
    	etContent.setTextColor(Color.rgb(48, 78, 98));
    	etContent.setHint(this.getString(R.string.note));
    	etContent.setMaxLines(1);
    	etContent.setInputType(InputType.TYPE_CLASS_TEXT);
    	etContent.setFocusable(true);
    	etContent.setFocusableInTouchMode(true);
    	etContent.setX(dpToPx(24) + (int)dpToPx(32) );
    	etContent.setY(wmParams.width/2 - wmParams.width*CENTER_AREA/2 + dpToPx(36) + dpToPx(36));
    	vLayout.addView(etContent);
    	
    	
    	
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
		
    	tvOkCircle = new TextView(this);
    	tvCancelCircle = new TextView(this);
    	tvOkCircle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvCancelCircle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvOkCircle.setGravity(Gravity.CENTER);
    	tvCancelCircle.setGravity(Gravity.CENTER);
    	tvOkCircle.setTextColor(Color.rgb(48, 78, 98));
    	tvCancelCircle.setTextColor(Color.rgb(48, 78, 98));
    	tvOkCircle.setText(this.getString(R.string.ok));
    	tvCancelCircle.setText(this.getString(R.string.cancel));
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
    		ivStar[i] = new ImageView(this);
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
    	
    	tvLevel = new TextView(this);
    	tvLevel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvLevel.setGravity(Gravity.CENTER);
    	tvLevel.setTextColor(Color.rgb(228, 242, 254));
    	tvLevel.setText(this.getString(R.string.important_level));
    	tvLevel.setLayoutParams(new LayoutParams((int)dpToPx(64), (int)dpToPx(32)));
    	tvLevel.setX(wmParams.width/2-dpToPx(64)/2);
    	tvLevel.setY(wmParams.height-wmParams.width/2+wmParams.width*CENTER_AREA/2+dpToPx(4));
    	
    	vLayout.addView(tvLevel);
    	
    	tvTitle = new TextView(this);
    	tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvTitle.setGravity(Gravity.CENTER);
    	tvTitle.setTextColor(Color.rgb(228, 242, 254));
    	tvTitle.setText(this.getString(R.string.title_timer4));
    	tvTitle.setLayoutParams(new LayoutParams((int)dpToPx(64), (int)dpToPx(28)));
    	tvTitle.setX(wmParams.width/2-dpToPx(40)/2);
    	
    	vLayout.addView(tvTitle);
    	
    	ivTitleIcon = new ImageView(this);
    	ivTitleIcon.setImageResource(R.drawable.timer4_icon);
    	ivTitleIcon.setLayoutParams(new LayoutParams((int)dpToPx(28), (int)dpToPx(28)));
    	ivTitleIcon.setX(wmParams.width/2-dpToPx(40)/2-dpToPx(26));
    	
    	vLayout.addView(ivTitleIcon);
    	
    	
    	//MainActivity.wm.addView(vLayout, wmParams);
    	
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
				wmParams.height/2 - wmParams.width/2 + dpToPx(32), dpToPx(32));
		aTitleY.setDuration(500);
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(ivTitleIcon, "y",
				wmParams.height/2 - wmParams.width/2 + dpToPx(32), dpToPx(32));
		aTitleIconY.setDuration(500);
		
		aUpperCicleY.start();
		aMiddleFillerHeight.start();   
		aLowerCicleY.start();  
		aOkCircleY.start();
		aCancelCircleY.start();
		aTitleY.start();
		aTitleIconY.start();
		for(int i=0; i<5; i++){
			aStarX[i].start();
			aStarY[i].start();
		}

		
		setContentView(vLayout);
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
				dpToPx(32), wmParams.height/2 - wmParams.width/2 + dpToPx(32));
		aTitleY.setDuration(500);
		ValueAnimator aTitleIconY = ObjectAnimator.ofFloat(ivTitleIcon, "y",
				dpToPx(32), wmParams.height/2 - wmParams.width/2 + dpToPx(32));
		aTitleIconY.setDuration(500);

		aUpperCicleY.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            	//MainActivity.wm.removeView(vLayout);
        		bVisible = false;
				finish();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

			@Override
			public void onAnimationCancel(Animator animation) {
				//MainActivity.wm.removeView(vLayout);
				bVisible = false;
				finish();
			}
        });
		
		
		aUpperCicleY.start();
		aMiddleFillerHeight.start();   
		aLowerCicleY.start();  
		aOkCircleY.start();
		aCancelCircleY.start();
		aTitleY.start();
		aTitleIconY.start();
		for(int i=0; i<5; i++){
			aStarX[i].start();
			aStarY[i].start();
		}
		
	}
	
	public void onClick(View v) {
		if(v == tvOkCircle){
			TimePickerDialog.OnTimeSetListener timePickerListener = 
		            new TimePickerDialog.OnTimeSetListener() {
				public void onTimeSet(TimePicker view, int selectedHour,
						int selectedMinute) {
					remindingTime.set(Calendar.HOUR_OF_DAY, selectedHour);
					remindingTime.set(Calendar.MINUTE, selectedMinute);
			        tvTime.setText(getString(R.string.time_));
				}
			};

		    new TimePickerDialog(this, timePickerListener, remindingTime
                    .get(Calendar.HOUR_OF_DAY), remindingTime.get(Calendar.MINUTE), true).show();
			//Ok();
			//Remove();
		}

		if(v == tvCancelCircle){
			Remove();
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
    	wmParams.height = (int) dpToPx(320-48);
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

	public static float dpToPx(float dp){
		return MainActivity.dpToPx(dp);
    }
	private void Ok(){
		
	}

}
