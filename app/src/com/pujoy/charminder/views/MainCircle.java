package com.pujoy.charminder.views;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;
import com.pujoy.charminder.base.FloatingBase;

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
import static com.pujoy.charminder.MainActivity.con;

public class MainCircle extends FloatingBase{
	RelativeLayout mainView;
	ImageView ivBackground;
	TextView tvCircleDescription;
	public ImageView[] ivCircleItems;
	FloatingText mainCircleItemDescription;
	private int iOldHover;
	public int HoveringItem;
	private static final int ICON_WIDTH = 80;
	private static final int ITEM_POSITION_OFFSET = -3;
	private static final float INNER_CIRCLE_RADIUS = 82.73437500000001f;
	
	@Override
	protected void onInitialize(){
		mainView = new RelativeLayout(con);
		ivBackground = new ImageView(con);
		ivBackground.setImageResource(R.drawable.circle_bg);
    	ivCircleItems = new ImageView[NUM_CIRCLE_ITEMS];
    	tvCircleDescription = new TextView(con);
    	RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
    			(int)dpToPx(96), (int)dpToPx(96));
		onUpdateLayout();
    	textParams.leftMargin = (layoutParams.getWidth() - (int)dpToPx(96))/2;
    	textParams.topMargin = (layoutParams.getHeight() - (int)dpToPx(96))/2;
    	tvCircleDescription.setGravity(Gravity.CENTER);
    	tvCircleDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16 + iLang*6);
    	tvCircleDescription.setTextColor(Constants.COLOR_LIGHTBLUE);
    	mainView.addView(tvCircleDescription, textParams);
    	ivBackground.setLayoutParams(new LayoutParams(layoutParams.getWidth(), layoutParams.getHeight()));
    	mainView.addView(ivBackground);
    	RelativeLayout.LayoutParams[] params = new RelativeLayout.LayoutParams[NUM_CIRCLE_ITEMS]; 
    	for(int i=0; i < NUM_CIRCLE_ITEMS; i++){
    		ivCircleItems[i] = new ImageView(con);
    		params[i] = new RelativeLayout.LayoutParams((int) dpToPx(ICON_WIDTH), (int) dpToPx(ICON_WIDTH));
    		params[i].leftMargin = 0;
    		params[i].topMargin = 0;
    	}
    	ivCircleItems[0].setImageResource(R.drawable.timer1_icon_c);
    	ivCircleItems[1].setImageResource(R.drawable.timer2_icon_c);
    	ivCircleItems[2].setImageResource(R.drawable.timer3_icon_c);
    	ivCircleItems[3].setImageResource(R.drawable.audio_reminder);
    	ivCircleItems[4].setImageResource(R.drawable.settings);
    	ivCircleItems[5].setImageResource(R.drawable.reminderlist);
    	ivCircleItems[6].setImageResource(R.drawable.exit);
    	for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
    		mainView.addView(ivCircleItems[i], params[i]);
    	}
    	
	}
	
	@Override
	protected void onCreate(){
		addView(mainView, layoutParams); 
    	tvCircleDescription.setText("");
    	tvCircleDescription.setBackgroundColor(android.graphics.Color.argb(0, 48, 78, 98));
    	Animation animation = AnimationUtils.loadAnimation(con, R.anim.zoom);
    	ivBackground.startAnimation(animation);
    	for(int i=0;i<NUM_CIRCLE_ITEMS;i++){
    		float tox = (float) (layoutParams.getWidth()/2 + dpToPx(INNER_CIRCLE_RADIUS) *
    				Math.sin(((360/NUM_CIRCLE_ITEMS) * (i+ITEM_POSITION_OFFSET)) * Math.PI / 180.0) -  dpToPx(ICON_WIDTH)/2);
    		float toy = (float) (layoutParams.getHeight()/2 + dpToPx(INNER_CIRCLE_RADIUS) * 
    				Math.cos(((360/NUM_CIRCLE_ITEMS) * (i+ITEM_POSITION_OFFSET)) * Math.PI / 180.0) -  dpToPx(ICON_WIDTH)/2);
    		float fromx = (float) (layoutParams.getWidth()/2 + dpToPx(INNER_CIRCLE_RADIUS) * 
    				Math.sin(((360/NUM_CIRCLE_ITEMS) * (i+ITEM_POSITION_OFFSET)) * Math.PI / 180.0)/4 -  dpToPx(ICON_WIDTH)/2);
    		float fromy = (float) (layoutParams.getHeight()/2 + dpToPx(INNER_CIRCLE_RADIUS) *
    				Math.cos(((360/NUM_CIRCLE_ITEMS) * (i+ITEM_POSITION_OFFSET)) * Math.PI / 180.0)/4 -  dpToPx(ICON_WIDTH)/2);
    		ValueAnimator aCircleItemsY = ObjectAnimator.ofFloat(ivCircleItems[i], "y", fromy, toy);
    		aCircleItemsY.setDuration(200);
    		ValueAnimator aCircleItemsX = ObjectAnimator.ofFloat(ivCircleItems[i], "x", fromx, tox);
    		aCircleItemsX.setDuration(200);
    		ValueAnimator aCircleItemsAlpha = ObjectAnimator.ofFloat(ivCircleItems[i], "alpha", 0, 1.5f);
    		aCircleItemsAlpha.setDuration(200);
    		aCircleItemsY.start();
    		aCircleItemsX.start();  
    		aCircleItemsAlpha.start();
    	}

	}
	
	@Override
	protected void onRemove(){
		removeView(mainView);
		if(mainCircleItemDescription != null){
			mainCircleItemDescription.remove();
			mainCircleItemDescription = null;
			HoveringItem = 0;
		}
	}
	
	@Override
	protected void onUpdateLayout(){
		layoutParams.setWidth((int) dpToPx(240));
		layoutParams.setHeight((int) dpToPx(240));  
		layoutParams.setX((getScreenWidth() - layoutParams.getWidth())/2);
		layoutParams.setY((getScreenHeight() - layoutParams.getHeight())/2); 	
	}
	
	public int getX(){
		return layoutParams.getX();
	}
	
	public int getY(){
		return layoutParams.getY();
	}

	public void Hover(int i) {
		if(HoveringItem != i + 1){
			HoveringItem = i + 1;
		}else{
			return;
		}
		switch(i){
		case 0:
			tvCircleDescription.setText(con.getString(R.string.circle_title_timer1));
			updateOldHoverItem();
			mainCircleItemDescription = new FloatingText();
			mainCircleItemDescription.setText(con.getString(R.string.description_timer1,
					con.getString(R.string.title_timer1)));
			mainCircleItemDescription.create();
			ivCircleItems[i].setImageResource(R.drawable.timer1_icon_a);
			iOldHover = 1;
			break;
		case 1:
			tvCircleDescription.setText(con.getString(R.string.circle_title_timer2));
			updateOldHoverItem();
			mainCircleItemDescription = new FloatingText();
			mainCircleItemDescription.setText(con.getString(R.string.description_timer2,
					con.getString(R.string.title_timer2)));
			mainCircleItemDescription.create();
			ivCircleItems[i].setImageResource(R.drawable.timer2_icon_a);
			iOldHover = 2;
			break;
		case 2:
			tvCircleDescription.setText(con.getString(R.string.circle_title_timer3));
			updateOldHoverItem();
			mainCircleItemDescription = new FloatingText();
			mainCircleItemDescription.setText(con.getString(R.string.description_timer3,
					con.getString(R.string.title_timer3)));
			mainCircleItemDescription.create();
			ivCircleItems[i].setImageResource(R.drawable.timer3_icon_a);
			iOldHover = 3;
			break;
		case 3:
			tvCircleDescription.setText(con.getString(R.string.circle_title_timer4));
			updateOldHoverItem();
			mainCircleItemDescription = new FloatingText();
			mainCircleItemDescription.setText(con.getString(R.string.description_timer4,
					con.getString(R.string.title_timer4)));
			mainCircleItemDescription.create();
			ivCircleItems[i].setImageResource(R.drawable.audio_reminder_a);
			iOldHover = 4;
			break;
		case 4:
			tvCircleDescription.setText(con.getString(R.string.circle_settings));
			updateOldHoverItem();
			mainCircleItemDescription = new FloatingText();
			mainCircleItemDescription.setText(con.getString(R.string.description_settings,
					con.getString(R.string.settings)));
			mainCircleItemDescription.create();
			ivCircleItems[i].setImageResource(R.drawable.settings_a);
			iOldHover = 5;
			break;
		case 5:
			tvCircleDescription.setText(con.getString(R.string.circle_reminder_list));
			updateOldHoverItem();
			mainCircleItemDescription = new FloatingText();
			mainCircleItemDescription.setText(con.getString(R.string.description_reminder_list,
					con.getString(R.string.reminder_list)));
			mainCircleItemDescription.create();
			ivCircleItems[i].setImageResource(R.drawable.reminderlist_a);
			iOldHover = 6;
			break;
		case 6:
			tvCircleDescription.setText(con.getString(R.string.circle_exit));
			updateOldHoverItem();
			mainCircleItemDescription = new FloatingText();
			mainCircleItemDescription.setText(con.getString(R.string.description_exit,
					con.getString(R.string.exit)));
			mainCircleItemDescription.create();
			ivCircleItems[i].setImageResource(R.drawable.exit_a);
			iOldHover = 7;
			break;
		}
	}

	public void updateOldHoverItem() {
		if(mainCircleItemDescription != null){
			mainCircleItemDescription.remove();
			mainCircleItemDescription = null;
		}
    	switch(iOldHover){
		case 1:
			ivCircleItems[0].setImageResource(R.drawable.timer1_icon_c);
		case 2:
			ivCircleItems[1].setImageResource(R.drawable.timer2_icon_c);
		case 3:
			ivCircleItems[2].setImageResource(R.drawable.timer3_icon_c);
		case 4:
			ivCircleItems[3].setImageResource(R.drawable.audio_reminder);
		case 5:
			ivCircleItems[4].setImageResource(R.drawable.settings);
		case 6:
			ivCircleItems[5].setImageResource(R.drawable.reminderlist);
		case 7:
			ivCircleItems[6].setImageResource(R.drawable.exit);
		}
		iOldHover = 0;
	}

	public boolean isPointInsideItem(float pointX, float pointY, int item) {
		final int RADIUS_COMPLEMENT = 8;
		return isPointInsideCircle((int) pointX, (int) pointY,
				(int) (getX() + ivCircleItems[item].getX() + ivCircleItems[item].getWidth()/2),
				(int) (getY() + ivCircleItems[item].getY() + ivCircleItems[item].getHeight()/2),
				(int)(ivCircleItems[item].getWidth()/2 + dpToPx(RADIUS_COMPLEMENT)));
	}
	
}
