package com.pujoy.charminder.base;

import static com.pujoy.charminder.MainActivity.con;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;

public abstract class FloatingTimerDialog extends FloatingDialog {

	protected TextView[] tvNumKeys;
	private TextView tvLevel;
	private ImageView[] ivStar;
	public int level = 3;
	
	protected abstract void onKeyDown(int key); 
	
	@Override
	public void create() {
		super.create();
    	ValueAnimator[] aStarX = new ValueAnimator[5];
    	ValueAnimator[] aStarY = new ValueAnimator[5];
    	for(int i=0; i<5; i++){
    		float toX = ((float) (layoutParams.getWidth()/2 + Math.sin(((120/5) * (i-2)) * Math.PI / 180.0) * (layoutParams.getWidth()*0.4)
    				- dpToPx(32)/2));
    		float toY = ((float) (layoutParams.getHeight()-layoutParams.getWidth()/2 + Math.cos(((120/5) * (i-2)) * Math.PI / 180.0) * (layoutParams.getWidth()*0.4)
    				- dpToPx(32)/2));

        	aStarX[i] = ObjectAnimator.ofFloat(ivStar[i], "x",
        			layoutParams.getWidth()/2 - dpToPx(32)/2, toX);
        	aStarX[i].setDuration(ANIMATION_DURATION);
        	aStarY[i] = ObjectAnimator.ofFloat(ivStar[i], "y",
        			layoutParams.getHeight()-layoutParams.getWidth()/2 - dpToPx(32)/2, toY);
        	aStarY[i].setDuration(ANIMATION_DURATION);
    	}
    	

    	
    	ValueAnimator[] aNumKeysAlpha = new ValueAnimator[10];
    	for(int i=0; i<10; i++){
    		aNumKeysAlpha[i] = ObjectAnimator.ofFloat(tvNumKeys[i], "alpha",
        			0, 1);
    		aNumKeysAlpha[i].setDuration(ANIMATION_DURATION);
    	}
    	
    	ValueAnimator aLevelY = ObjectAnimator.ofFloat(tvLevel, "y",
    			layoutParams.getHeight()-layoutParams.getWidth()/2+layoutParams.getWidth()*NONCENTER_PROPORTION/2+dpToPx(4)-(layoutParams.getHeight()-layoutParams.getWidth())/2, 
        			layoutParams.getHeight()-layoutParams.getWidth()/2+layoutParams.getWidth()*NONCENTER_PROPORTION/2+dpToPx(4));
    	aLevelY.setDuration(ANIMATION_DURATION);
    	

		aLevelY.start();
		for(int i=0; i<5; i++){
			aStarX[i].start();
			aStarY[i].start();
		}
		for(int i=0; i<10; i++){
    		aNumKeysAlpha[i].start();;
    	}
		
	}

	@Override
	public void remove() {
		super.remove();
    	ValueAnimator[] aStarX = new ValueAnimator[5];
    	ValueAnimator[] aStarY = new ValueAnimator[5];
    	for(int i=0; i<5; i++){
        	aStarX[i] = ObjectAnimator.ofFloat(ivStar[i], "x",
        			ivStar[i].getX(), layoutParams.getWidth()/2);
        	aStarX[i].setDuration(ANIMATION_DURATION);
        	aStarY[i] = ObjectAnimator.ofFloat(ivStar[i], "y",
        			ivStar[i].getY(), layoutParams.getHeight()-layoutParams.getWidth()/2);
        	aStarY[i].setDuration(ANIMATION_DURATION);
    	}
    	
    	ValueAnimator[] aNumKeysAlpha = new ValueAnimator[10];
    	for(int i=0; i<10; i++){
    		aNumKeysAlpha[i] = ObjectAnimator.ofFloat(tvNumKeys[i], "alpha",
        			1, 0);
    		aNumKeysAlpha[i].setDuration(ANIMATION_DURATION);
    	}
		
    	ValueAnimator aLevelY = ObjectAnimator.ofFloat(tvLevel, "y",
    			layoutParams.getHeight()-layoutParams.getWidth()/2+layoutParams.getWidth()*NONCENTER_PROPORTION/2+dpToPx(4),
    			layoutParams.getHeight()-layoutParams.getWidth()/2+layoutParams.getWidth()*NONCENTER_PROPORTION/2+dpToPx(4)-(layoutParams.getHeight()-layoutParams.getWidth())/2);
    	aLevelY.setDuration(ANIMATION_DURATION);
    	

		for(int i=0; i<5; i++){
			aStarX[i].start();
			aStarY[i].start();
		}
		for(int i=0; i<10; i++){
    		aNumKeysAlpha[i].start();;
    	}
		aLevelY.start();

	}
	
	@Override
	protected void onInitialize(boolean bFullUpperCircle){
		super.onInitialize(bFullUpperCircle);
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
    		tvNumKeys[i] = new TextView(con);
    		tvNumKeys[i].setLayoutParams(new LayoutParams(
    				(int) (layoutParams.getWidth()*NON_BORDER_SCALE/3)+2, 
    				bFullUpperCircle? (int) ((layoutParams.getHeight() - layoutParams.getWidth() + layoutParams.getWidth()/2*NONCENTER_PROPORTION)/4+2):
    				(int) ((layoutParams.getHeight() - layoutParams.getWidth() + layoutParams.getWidth()*NONCENTER_PROPORTION)/4+2)));
    		tvNumKeys[i].setX((float)(int) (layoutParams.getWidth()*BORDER_SCALE +
    				(layoutParams.getWidth()*NON_BORDER_SCALE/3)*(i%3)-1));
    		if(bFullUpperCircle){
        		tvNumKeys[i].setY((float)(int) (layoutParams.getWidth()/2 + 
        				(layoutParams.getHeight() - layoutParams.getWidth()+ layoutParams.getWidth()/2*NONCENTER_PROPORTION)/4*Math.floor(i/3)-1));
    		}else{
        		tvNumKeys[i].setY((float)(int) (layoutParams.getWidth()/2- layoutParams.getWidth()*NONCENTER_PROPORTION/2 + 
        				(layoutParams.getHeight() - layoutParams.getWidth()+ layoutParams.getWidth()*NONCENTER_PROPORTION)/4*Math.floor(i/3)-1));
    		}
    		tvNumKeys[i].setText((Integer.valueOf(i + 1)).toString());
    		tvNumKeys[i].setGravity(Gravity.CENTER);
    		tvNumKeys[i].setTextColor(Constants.COLOR_DARKBLUE);
    		tvNumKeys[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    		tvNumKeys[i].setOnTouchListener(NumKeysTouchListener);
    		tvNumKeys[i].setOnClickListener(new OnClickListener(){
    			
				@Override
				public void onClick(View v) {
					for(int i=0; i<10; i++){
						if(v == tvNumKeys[i]){
							onKeyDown(i);
						}
					}
				}
    			
    		});
    		addToMainView(tvNumKeys[i]);
    	}
    	tvNumKeys[9].setX(layoutParams.getWidth()*BORDER_SCALE +(layoutParams.getWidth()*NON_BORDER_SCALE/3)*(10%3));
    	tvNumKeys[9].setText("0");
    	
    	ivStar = new ImageView[5];
    	level = 3;
    	View.OnTouchListener StarsTouchListener = new View.OnTouchListener() {
			@Override
    	    public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction())
				{
				case MotionEvent.ACTION_MOVE:
					for(int i=0; i<5; i++){
						if(motionEvent.getRawX() >= layoutParams.x + ivStar[i].getX() &&
								motionEvent.getRawX() < layoutParams.x + ivStar[i].getX() + (int)dpToPx(32)){
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
    		ivStar[i] = new ImageView(con);
    		ivStar[i].setLayoutParams(new LayoutParams((int)dpToPx(32), (int)dpToPx(32)));
    		ivStar[i].setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					for(int i=0; i<5; i++){
						if(v == ivStar[i]){
							level = i+1;
							UpdateStarImage();
						}
					}
				}
    			
    		});
    		ivStar[i].setOnTouchListener(StarsTouchListener);
    		addToMainView(ivStar[i]);
    	}
    	UpdateStarImage();
    	
    	tvLevel = new TextView(con);
    	tvLevel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvLevel.setGravity(Gravity.CENTER);
    	tvLevel.setTextColor(Color.rgb(228, 242, 254));
    	tvLevel.setText(con.getString(R.string.priority));
    	tvLevel.setLayoutParams(new LayoutParams((int)dpToPx(64), (int)dpToPx(32)));
    	tvLevel.setX(layoutParams.getWidth()/2-dpToPx(64)/2);
    	tvLevel.setY(layoutParams.getHeight()-layoutParams.getWidth()/2+layoutParams.getWidth()*NONCENTER_PROPORTION/2+dpToPx(4));
    	addToMainView(tvLevel);
	}
	
	@Override
	protected void onInitialize(){
		onInitialize(false);
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

	@Override
	protected void onUpdateLayout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onOk() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onCancel() {
		// TODO Auto-generated method stub
		
	}

}
