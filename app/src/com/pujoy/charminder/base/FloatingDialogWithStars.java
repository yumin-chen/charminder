package com.pujoy.charminder.base;

import static com.pujoy.charminder.MainActivity.con;

import com.pujoy.charminder.R;

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

public abstract class FloatingDialogWithStars extends FloatingDialog {
	
	private TextView tvPriority;
	private ImageView[] ivStar;
	protected int level = 3;
	

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
			aStarX[i].start();
			aStarY[i].start();
    	}

    	
    	ValueAnimator aLevelY = ObjectAnimator.ofFloat(tvPriority, "y",
    			layoutParams.getHeight()-layoutParams.getWidth()/2+layoutParams.getWidth()*NONCENTER_PROPORTION/2+dpToPx(4)-(layoutParams.getHeight()-layoutParams.getWidth())/2, 
        			layoutParams.getHeight()-layoutParams.getWidth()/2+layoutParams.getWidth()*NONCENTER_PROPORTION/2+dpToPx(4));
    	aLevelY.setDuration(ANIMATION_DURATION);
    	aLevelY.start();
    	
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
			aStarX[i].start();
			aStarY[i].start();
    	}

    	ValueAnimator aLevelY = ObjectAnimator.ofFloat(tvPriority, "y",
    			layoutParams.getHeight()-layoutParams.getWidth()/2+layoutParams.getWidth()*NONCENTER_PROPORTION/2+dpToPx(4),
    			layoutParams.getHeight()-layoutParams.getWidth()/2+layoutParams.getWidth()*NONCENTER_PROPORTION/2+dpToPx(4)-(layoutParams.getHeight()-layoutParams.getWidth())/2);
    	aLevelY.setDuration(ANIMATION_DURATION);
		aLevelY.start();
		
	}
	
	@Override
	protected void onInitialize(boolean bFullUpperCircle){
		super.onInitialize(bFullUpperCircle);
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
    	
    	tvPriority = new TextView(con);
    	tvPriority.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
    	tvPriority.setGravity(Gravity.CENTER);
    	tvPriority.setTextColor(Color.rgb(228, 242, 254));
    	tvPriority.setText(con.getString(R.string.priority));
    	tvPriority.setLayoutParams(new LayoutParams((int)dpToPx(64), (int)dpToPx(32)));
    	tvPriority.setX(layoutParams.getWidth()/2-dpToPx(64)/2);
    	tvPriority.setY(layoutParams.getHeight()-layoutParams.getWidth()/2+layoutParams.getWidth()*NONCENTER_PROPORTION/2+dpToPx(4));
    	addToMainView(tvPriority);
    	
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

}
