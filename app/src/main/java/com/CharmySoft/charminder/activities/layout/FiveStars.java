/*
**  Class FiveStars
**  src/com/CharmySoft/charminder/activities/layout/FiveStars.java
*/
package com.CharmySoft.charminder.activities.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.base.ViewBase;

public class FiveStars extends LinearLayout {
	int iPriority;
	ImageView[] mStars;
	public FiveStars(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.IntegerStyleable, 0, 0);
		iPriority = array.getInteger(R.styleable.IntegerStyleable_number, 0);
		array.recycle();
		initialize(context);
		
	}
	
	public FiveStars(Context context){
		super(context);
		initialize(context);
	}
	
	private void initialize(Context context) {
		setOrientation(HORIZONTAL);
		LayoutParams layout = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		layout.gravity = Gravity.CENTER_VERTICAL;
		setLayoutParams(layout);
		mStars = new ImageView[5];
		for(int i = 0; i < 5; i ++){
			mStars[i] = new ImageView(context);
			mStars[i].setLayoutParams(new LayoutParams((int) ViewBase.dpToPx(24), (int) ViewBase.dpToPx(24)));
			this.addView(mStars[i]);
		}
		
	}
	
	private void update() {
		for(int i = 0; i < mStars.length; i ++){
			mStars[i].setImageResource(iPriority > i ? R.drawable.star1 : R.drawable.star0);
		}
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		update();
	}
	
	public void setPriority(int priority){
		iPriority = priority;
		update();
	}
	
	public int getPriority(){
		return iPriority;
	}
	
	public void setEditable(boolean editable){
		if(editable){
			this.setClickable(true);
			this.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_MOVE:
						for (int i = 0; i < 5; i++) {
							if (((event.getX() - mStars[0].getX() >= mStars[i].getX())
									&& ((event.getX() - mStars[0].getX())) < (mStars[i].getX() + (int) ViewBase.dpToPx(24)))) {
								iPriority = i + 1;
								update();
								break;
							}
						}
						break;
					}
					return false;
				}
				
			});
		}else{
			this.setOnTouchListener(null);
		}
		
	}


}
