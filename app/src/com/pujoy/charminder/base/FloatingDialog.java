package com.pujoy.charminder.base;

import static com.pujoy.charminder.MainActivity.con;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;

public abstract class FloatingDialog extends FloatingBase{
	
	private RelativeLayout mainView;
	private ImageView ivUpperCircle;
	private ImageView ivMiddleFiller;
	private ImageView ivLowerCircle;
	private TextView tvOkCircle;
	private TextView tvCancelCircle;
	
	protected static final float NON_BORDER_SCALE = 0.960548885077187f;
	protected static final float BORDER_SCALE = 0.0221843003412969f;
	protected static final float NONCENTER_PROPORTION = 0.3720136518771331f;
	
	protected static final int ANIMATION_DURATION = 500;
	
	
	protected abstract void onCreate(); 
	protected abstract void onRemove(); 
	protected abstract void onUpdateLayout(); 
	protected abstract void onOk(); 
	protected abstract void onCancel(); 
	
	public FloatingDialog(){
		super();
	}
	
	@Override
	public void create(){
		super.create();
		addView(mainView, layoutParams);
		ValueAnimator aUpperCicleY = ObjectAnimator.ofFloat(ivUpperCircle, "y",
				layoutParams.getHeight()/2 - layoutParams.getWidth()/2, 0);
		aUpperCicleY.setDuration(ANIMATION_DURATION);
		ValueAnimator aLowerCicleY = ObjectAnimator.ofFloat(ivLowerCircle, "y",
				layoutParams.getHeight()/2, layoutParams.getHeight() - layoutParams.getWidth()/2);
		aLowerCicleY.setDuration(ANIMATION_DURATION);
		ValueAnimator aMiddleFillerHeight = ObjectAnimator.ofFloat(ivMiddleFiller, "scaleY",
				0, 1);
		aMiddleFillerHeight.setDuration(ANIMATION_DURATION);
		ValueAnimator aOkCircleY = ObjectAnimator.ofFloat(tvOkCircle, "y",
				layoutParams.getHeight()/2-tvOkCircle.getLayoutParams().height, 0);
		aOkCircleY.setDuration(ANIMATION_DURATION);
		ValueAnimator aCancelCircleY = ObjectAnimator.ofFloat(tvCancelCircle, "y",
				layoutParams.getHeight()/2-tvCancelCircle.getLayoutParams().height, 0);
		aCancelCircleY.setDuration(ANIMATION_DURATION);
		
		aUpperCicleY.start();
		aMiddleFillerHeight.start();   
		aLowerCicleY.start();  
		aOkCircleY.start();
		aCancelCircleY.start();
	}
	
	@Override
	public void remove(){
		if (!bCreated){
			return;
		}
		
		ValueAnimator aUpperCicleY = ObjectAnimator.ofFloat(ivUpperCircle, "y",
				0, layoutParams.getHeight()/2 - layoutParams.getWidth()/2);
		aUpperCicleY.setDuration(ANIMATION_DURATION);
		ValueAnimator aLowerCicleY = ObjectAnimator.ofFloat(ivLowerCircle, "y",
				layoutParams.getHeight() - layoutParams.getWidth()/2, layoutParams.getHeight()/2);
		aLowerCicleY.setDuration(ANIMATION_DURATION);
		ValueAnimator aMiddleFillerHeight = ObjectAnimator.ofFloat(ivMiddleFiller, "scaleY",
				1, 0);
		aMiddleFillerHeight.setDuration(ANIMATION_DURATION);
		ValueAnimator aOkCircleY = ObjectAnimator.ofFloat(tvOkCircle, "y",
				0, layoutParams.getHeight()/2-tvOkCircle.getLayoutParams().height);
		aOkCircleY.setDuration(ANIMATION_DURATION);
		ValueAnimator aCancelCircleY = ObjectAnimator.ofFloat(tvCancelCircle, "y",
				0, layoutParams.getHeight()/2-tvCancelCircle.getLayoutParams().height);
		aCancelCircleY.setDuration(ANIMATION_DURATION);
		
		aUpperCicleY.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            	removeView(mainView);
            	bCreated = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

			@Override
			public void onAnimationCancel(Animator animation) {
				removeView(mainView);
				bCreated = false;
			}
        });
		
		aUpperCicleY.start();
		aMiddleFillerHeight.start();   
		aLowerCicleY.start();  
		aOkCircleY.start();
		aCancelCircleY.start();
		
		onRemove();
	}
	
	public void addToMainView(View child){
		mainView.addView(child);
		onInitSortView();
	}
	public void removeFromMainView(View child){
		mainView.removeView(child);
	}
	
	protected void onInitialize(boolean bFullUpperCircle){
		onUpdateLayout();
		mainView = new RelativeLayout(con);
    	ivUpperCircle = new ImageView(con);
    	ivLowerCircle = new ImageView(con);
    	ivMiddleFiller = new ImageView(con);
		mainView.layout(0, 0, layoutParams.getWidth(), layoutParams.getHeight());
    	ivUpperCircle.setLayoutParams(new LayoutParams(layoutParams.getWidth(), layoutParams.getWidth()/2));
		ivMiddleFiller.setY(layoutParams.getWidth()/2);
		ivMiddleFiller.setLayoutParams(new LayoutParams(layoutParams.getWidth(), layoutParams.getHeight() - layoutParams.getWidth()));
    	ivLowerCircle.setLayoutParams(new LayoutParams(layoutParams.getWidth(), layoutParams.getWidth()/2));
    	ivUpperCircle.setImageResource(bFullUpperCircle? R.drawable.semi_circle_full:
    		R.drawable.semi_circle);
    	ivMiddleFiller.setImageResource(R.drawable.semi_circle_fill);
    	ivMiddleFiller.setScaleType(ScaleType.FIT_XY);
    	ivLowerCircle.setImageResource(R.drawable.semi_circle_bottom);
    	mainView.addView(ivMiddleFiller);
    	mainView.addView(ivUpperCircle);
    	mainView.addView(ivLowerCircle);
    	
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
    	
    	tvOkCircle = new TextView(con);
    	tvCancelCircle = new TextView(con);
    	tvOkCircle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvCancelCircle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvOkCircle.setGravity(Gravity.CENTER);
    	tvCancelCircle.setGravity(Gravity.CENTER);
    	tvOkCircle.setTextColor(Constants.COLOR_DARKBLUE);
    	tvCancelCircle.setTextColor(Constants.COLOR_DARKBLUE);
    	tvOkCircle.setText(con.getString(R.string.ok));
    	tvCancelCircle.setText(con.getString(R.string.cancel));
    	tvOkCircle.setBackgroundResource(R.drawable.small_circle);
    	tvOkCircle.setLayoutParams(new LayoutParams((int)dpToPx(73.39249146757679f), (int)dpToPx(73.39249146757679f)));
    	tvCancelCircle.setBackgroundResource(R.drawable.small_circle);
    	tvCancelCircle.setLayoutParams(new LayoutParams((int)dpToPx(73.39249146757679f), (int)dpToPx(73.39249146757679f)));
    	tvOkCircle.setX(0);
    	tvCancelCircle.setX(layoutParams.getWidth() - tvCancelCircle.getLayoutParams().width);
    	tvOkCircle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onOk();
				remove();
			}
		});
    	tvCancelCircle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onCancel();
				remove();
			}
		});
    	tvOkCircle.setOnTouchListener(OkCancelTouchListener);
    	tvCancelCircle.setOnTouchListener(OkCancelTouchListener);
    	mainView.addView(tvOkCircle);
    	mainView.addView(tvCancelCircle);
	}
	
	
	@Override
	protected void onInitialize(){
		onInitialize(false);
	}
	
	protected void onInitSortView(){
		tvOkCircle.bringToFront();
		tvCancelCircle.bringToFront();
	}
	
	public void setOkText(String text){
    	tvOkCircle.setText(text);
	}
	
	public void setCancelText(String text){
    	tvCancelCircle.setText(text);
	}
	
	
}
