package com.pujoy.charminder.base;

import static com.pujoy.charminder.MainActivity.con;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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

public abstract class FloatingTimerDialog extends FloatingDialogWithStars {

	protected TextView[] tvNumKeys;
	private ImageView ivAddNote;
	
	protected abstract void onKeyDown(int key); 
	
	@Override
	public void create() {
		super.create();
    	ValueAnimator[] aNumKeysAlpha = new ValueAnimator[10];
    	for(int i=0; i<10; i++){
    		aNumKeysAlpha[i] = ObjectAnimator.ofFloat(tvNumKeys[i], "alpha",
        			0, 1);
    		aNumKeysAlpha[i].setDuration(ANIMATION_DURATION);
    		aNumKeysAlpha[i].start();
    	}
		
	}

	@Override
	public void remove() {
		super.remove();
    	
    	ValueAnimator[] aNumKeysAlpha = new ValueAnimator[10];
    	for(int i=0; i<10; i++){
    		aNumKeysAlpha[i] = ObjectAnimator.ofFloat(tvNumKeys[i], "alpha",
        			1, 0);
    		aNumKeysAlpha[i].setDuration(ANIMATION_DURATION);
    		aNumKeysAlpha[i].start();
    	}

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
					v.setBackgroundColor(Constants.COLOR_GREENBLUE);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(Constants.COLOR_TRANSPARENT);
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
    	
    	ivAddNote = new ImageView(con);
    	ivAddNote.setImageResource(R.drawable.edit);
    	int height = bFullUpperCircle? (int) ((layoutParams.getHeight() - layoutParams.getWidth() + layoutParams.getWidth()/2*NONCENTER_PROPORTION)/4+2):
			(int) ((layoutParams.getHeight() - layoutParams.getWidth() + layoutParams.getWidth()*NONCENTER_PROPORTION)/4+2);
    	ivAddNote.setLayoutParams(new LayoutParams(height-2, height-2));
    	ivAddNote.setX(layoutParams.getWidth()*BORDER_SCALE +(layoutParams.getWidth()*NON_BORDER_SCALE/3)*(11%3)
    			+(layoutParams.getWidth()*NON_BORDER_SCALE/3)/2 - height/2);
    	if(bFullUpperCircle){
    		ivAddNote.setY((float)(int) (layoutParams.getWidth()/2 + 
    				(layoutParams.getHeight() - layoutParams.getWidth()+ layoutParams.getWidth()/2*NONCENTER_PROPORTION)/4*Math.floor(11/3)-1));
		}else{
			ivAddNote.setY((float)(int) (layoutParams.getWidth()/2- layoutParams.getWidth()*NONCENTER_PROPORTION/2 + 
    				(layoutParams.getHeight() - layoutParams.getWidth()+ layoutParams.getWidth()*NONCENTER_PROPORTION)/4*Math.floor(11/3)-1));
		}
    	ivAddNote.setOnTouchListener(NumKeysTouchListener);
    	ivAddNote.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
			}
    		
    	});
    	addToMainView(ivAddNote);
    	

	}
	
	@Override
	protected void onInitialize(){
		onInitialize(false);
	}
	

}
