package com.pujoy.charminder.views;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;
import com.pujoy.charminder.base.WindowBase;

import static com.pujoy.charminder.MainActivity.iLang;
import static com.pujoy.charminder.MainActivity.NUM_CIRCLE_ITEMS;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import static com.pujoy.charminder.MainActivity.mCon;

public class MainCircle extends WindowBase{
	RelativeLayout mMainView;
	ImageView mBackground;
	TextView mCircleDescription;
	public ImageView[] mCircleItems;
	FloatingText mItemDescription;
	private int iOldHover;
	public int iHoveringItem;
	private static final int ICON_WIDTH = 80;
	private static final int ITEM_POSITION_OFFSET = -3;
	private static final float INNER_CIRCLE_RADIUS = 82.73437500000001f;
	
	@Override
	protected void onInitialize(){
		mMainView = new RelativeLayout(mCon);
		mBackground = new ImageView(mCon);
		mBackground.setImageResource(R.drawable.circle_bg);
    	mCircleItems = new ImageView[NUM_CIRCLE_ITEMS];
    	mCircleDescription = new TextView(mCon);
    	RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
    			(int)dpToPx(96), (int)dpToPx(96));
		onUpdateLayout();
    	textParams.leftMargin = (mLayoutParams.getWidth() - (int)dpToPx(96))/2;
    	textParams.topMargin = (mLayoutParams.getHeight() - (int)dpToPx(96))/2;
    	mCircleDescription.setGravity(Gravity.CENTER);
    	mCircleDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + iLang*6);
    	mCircleDescription.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mMainView.addView(mCircleDescription, textParams);
    	mBackground.setLayoutParams(new LayoutParams(mLayoutParams.getWidth(), mLayoutParams.getHeight()));
    	mMainView.addView(mBackground);
    	RelativeLayout.LayoutParams[] params = new RelativeLayout.LayoutParams[NUM_CIRCLE_ITEMS]; 
    	for(int i=0; i < NUM_CIRCLE_ITEMS; i++){
    		mCircleItems[i] = new ImageView(mCon);
    		params[i] = new RelativeLayout.LayoutParams((int) dpToPx(ICON_WIDTH), (int) dpToPx(ICON_WIDTH));
    		params[i].leftMargin = 0;
    		params[i].topMargin = 0;
    	}
    	mCircleItems[0].setImageResource(R.drawable.timer1_icon_c);
    	mCircleItems[1].setImageResource(R.drawable.timer2_icon_c);
    	mCircleItems[2].setImageResource(R.drawable.timer3_icon_c);
    	mCircleItems[3].setImageResource(R.drawable.audio_reminder);
    	mCircleItems[4].setImageResource(R.drawable.settings);
    	mCircleItems[5].setImageResource(R.drawable.reminderlist);
    	mCircleItems[6].setImageResource(R.drawable.exit);
    	for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
    		mMainView.addView(mCircleItems[i], params[i]);
    	}
    	
	}
	
	@Override
	protected void onCreate(){
		addView(mMainView, mLayoutParams); 
    	mCircleDescription.setText("");
    	mCircleDescription.setBackgroundColor(android.graphics.Color.argb(0, 48, 78, 98));
    	Animation animation = AnimationUtils.loadAnimation(mCon, R.anim.zoom);
    	mBackground.startAnimation(animation);
    	for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
    		float tox = (float) (mLayoutParams.getWidth()/2 + dpToPx(INNER_CIRCLE_RADIUS) *
    				Math.sin(((360/NUM_CIRCLE_ITEMS) * (i+ITEM_POSITION_OFFSET)) * Math.PI / 180.0) -  dpToPx(ICON_WIDTH)/2);
    		float toy = (float) (mLayoutParams.getHeight()/2 + dpToPx(INNER_CIRCLE_RADIUS) * 
    				Math.cos(((360/NUM_CIRCLE_ITEMS) * (i+ITEM_POSITION_OFFSET)) * Math.PI / 180.0) -  dpToPx(ICON_WIDTH)/2);
    		float fromx = (float) (mLayoutParams.getWidth()/2 + dpToPx(INNER_CIRCLE_RADIUS) * 
    				Math.sin(((360/NUM_CIRCLE_ITEMS) * (i+ITEM_POSITION_OFFSET)) * Math.PI / 180.0)/4 -  dpToPx(ICON_WIDTH)/2);
    		float fromy = (float) (mLayoutParams.getHeight()/2 + dpToPx(INNER_CIRCLE_RADIUS) *
    				Math.cos(((360/NUM_CIRCLE_ITEMS) * (i+ITEM_POSITION_OFFSET)) * Math.PI / 180.0)/4 -  dpToPx(ICON_WIDTH)/2);
    		ValueAnimator aCircleItemsY = ObjectAnimator.ofFloat(mCircleItems[i], "y", fromy, toy);
    		aCircleItemsY.setDuration(200);
    		ValueAnimator aCircleItemsX = ObjectAnimator.ofFloat(mCircleItems[i], "x", fromx, tox);
    		aCircleItemsX.setDuration(200);
    		ValueAnimator aCircleItemsAlpha = ObjectAnimator.ofFloat(mCircleItems[i], "alpha", 0, 1.5f);
    		aCircleItemsAlpha.setDuration(200);
    		aCircleItemsY.start();
    		aCircleItemsX.start();  
    		aCircleItemsAlpha.start();
    	}

	}
	
	@Override
	protected void onRemove(){
		removeView(mMainView);
		if(mItemDescription != null){
			mItemDescription.remove();
			mItemDescription = null;
			iHoveringItem = 0;
		}
	}
	
	@Override
	protected void onUpdateLayout(){
		mLayoutParams.setWidth((int) dpToPx(240));
		mLayoutParams.setHeight((int) dpToPx(240));  
		mLayoutParams.setX((getScreenWidth() - mLayoutParams.getWidth())/2);
		mLayoutParams.setY((getScreenHeight() - mLayoutParams.getHeight())/2); 	
	}
	
	public int getX(){
		return mLayoutParams.getX();
	}
	
	public int getY(){
		return mLayoutParams.getY();
	}

	public void Hover(int i) {
		if(iHoveringItem != i + 1){
			iHoveringItem = i + 1;
		}else{
			return;
		}
		switch(i){
		case 0:
			mCircleDescription.setText(mCon.getString(R.string.circle_title_timer1));
			updateOldHoverItem();
			mItemDescription = new FloatingText();
			mItemDescription.setText(mCon.getString(R.string.description_timer1,
					mCon.getString(R.string.title_timer1)));
			mItemDescription.create();
			mCircleItems[i].setImageResource(R.drawable.timer1_icon_a);
			iOldHover = 1;
			break;
		case 1:
			mCircleDescription.setText(mCon.getString(R.string.circle_title_timer2));
			updateOldHoverItem();
			mItemDescription = new FloatingText();
			mItemDescription.setText(mCon.getString(R.string.description_timer2,
					mCon.getString(R.string.title_timer2)));
			mItemDescription.create();
			mCircleItems[i].setImageResource(R.drawable.timer2_icon_a);
			iOldHover = 2;
			break;
		case 2:
			mCircleDescription.setText(mCon.getString(R.string.circle_title_timer3));
			updateOldHoverItem();
			mItemDescription = new FloatingText();
			mItemDescription.setText(mCon.getString(R.string.description_timer3,
					mCon.getString(R.string.title_timer3)));
			mItemDescription.create();
			mCircleItems[i].setImageResource(R.drawable.timer3_icon_a);
			iOldHover = 3;
			break;
		case 3:
			mCircleDescription.setText(mCon.getString(R.string.circle_title_timer4));
			updateOldHoverItem();
			mItemDescription = new FloatingText();
			mItemDescription.setText(mCon.getString(R.string.description_timer4,
					mCon.getString(R.string.title_timer4)));
			mItemDescription.create();
			mCircleItems[i].setImageResource(R.drawable.audio_reminder_a);
			iOldHover = 4;
			break;
		case 4:
			mCircleDescription.setText(mCon.getString(R.string.circle_settings));
			updateOldHoverItem();
			mItemDescription = new FloatingText();
			mItemDescription.setText(mCon.getString(R.string.description_settings,
					mCon.getString(R.string.settings)));
			mItemDescription.create();
			mCircleItems[i].setImageResource(R.drawable.settings_a);
			iOldHover = 5;
			break;
		case 5:
			mCircleDescription.setText(mCon.getString(R.string.circle_reminder_list));
			updateOldHoverItem();
			mItemDescription = new FloatingText();
			mItemDescription.setText(mCon.getString(R.string.description_reminder_list,
					mCon.getString(R.string.reminder_list)));
			mItemDescription.create();
			mCircleItems[i].setImageResource(R.drawable.reminderlist_a);
			iOldHover = 6;
			break;
		case 6:
			mCircleDescription.setText(mCon.getString(R.string.circle_exit));
			updateOldHoverItem();
			mItemDescription = new FloatingText();
			mItemDescription.setText(mCon.getString(R.string.description_exit,
					mCon.getString(R.string.exit)));
			mItemDescription.create();
			mCircleItems[i].setImageResource(R.drawable.exit_a);
			iOldHover = 7;
			break;
		}
	}

	public void updateOldHoverItem() {
		if(mItemDescription != null){
			mItemDescription.remove();
			mItemDescription = null;
		}
    	switch(iOldHover){
		case 1:
			mCircleItems[0].setImageResource(R.drawable.timer1_icon_c);
		case 2:
			mCircleItems[1].setImageResource(R.drawable.timer2_icon_c);
		case 3:
			mCircleItems[2].setImageResource(R.drawable.timer3_icon_c);
		case 4:
			mCircleItems[3].setImageResource(R.drawable.audio_reminder);
		case 5:
			mCircleItems[4].setImageResource(R.drawable.settings);
		case 6:
			mCircleItems[5].setImageResource(R.drawable.reminderlist);
		case 7:
			mCircleItems[6].setImageResource(R.drawable.exit);
		}
		iOldHover = 0;
	}

	public boolean isPointInsideItem(float pointX, float pointY, int item) {
		final int RADIUS_COMPLEMENT = 8;
		return isPointInsideCircle((int) pointX, (int) pointY,
				(int) (getX() + mCircleItems[item].getX() + mCircleItems[item].getWidth()/2),
				(int) (getY() + mCircleItems[item].getY() + mCircleItems[item].getHeight()/2),
				(int)(mCircleItems[item].getWidth()/2 + dpToPx(RADIUS_COMPLEMENT)));
	}
	
}
