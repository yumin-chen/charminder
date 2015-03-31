/*
**  Class CircleLayoutSettingActivity
**  src/com/CharmySoft/charminder/activities/CircleLayoutSettingActivity.java
*/
package com.CharmySoft.charminder.activities;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.base.ActivityBase;
import com.CharmySoft.charminder.base.ViewBase;
import com.CharmySoft.charminder.helper.Helper;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public class CircleLayoutSettingActivity extends ActivityBase implements OnDragListener{

	
	double dTempAngel;
	ImageView[] mCircleItems;
	RelativeLayout mBigCircle; 
	RelativeLayout mUnusedSection;
	
	private final String DRAG_FROM_CIRCLE_TAG = "DRAG_FROM_CIRCLE"; 
	private final String DRAG_FROM_UNUSED_TAG = "DRAG_FROM_UNUSED"; 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle_layout_setting);

		mBigCircle = (RelativeLayout) findViewById(R.id.circle_layout_setting_layout);
		mUnusedSection = (RelativeLayout) findViewById(R.id.circle_layout_setting_not_used_layout);
		//android.view.ViewGroup.LayoutParams layout = mUnusedSection.getLayoutParams();
		mUnusedSection.setPadding(0, 0, 0, 0);
		mUnusedSection.setOnDragListener(this);
		final GestureDetector unusedGestureDetector = new GestureDetector(this, new UnusedGestureListener());
		mUnusedSection.setOnTouchListener(new OnTouchListener(){
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
                return unusedGestureDetector.onTouchEvent(event);
            }
		});
		mUnusedSection.setClickable(true);
		final GestureDetector circleGestureDetector = new GestureDetector(this, new BigCircleGestureListener());
		mBigCircle.setOnTouchListener(new OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
            	if(event.getAction() == MotionEvent.ACTION_UP){
            		G.settings.dCircleAngle	+= dTempAngel;
            		dTempAngel = 0;
            		G.settings.save();
            	}
                return circleGestureDetector.onTouchEvent(event);
            }

        });
		mBigCircle.setOnDragListener(this);
		mBigCircle.setClickable(true);
		TypedArray drawble = getResources().obtainTypedArray(
				R.array.main_menu_icons);
		mCircleItems = new ImageView[C.NUM_CIRCLE_ITEMS];
		int iconSize = (int) getResources().getDimension(
				R.dimen.circle_item_size);
		for (int i = 0; i < C.NUM_CIRCLE_ITEMS; i++) {
			mCircleItems[i] = new ImageView(this);
			mCircleItems[i].setImageDrawable(drawble.getDrawable(i));
			boolean bUse = false;
			for (int j = 0; j < G.settings.mCircleSection.length; j++) {
				if(G.settings.mCircleSection[j] == i){
					mBigCircle.addView(mCircleItems[i], new RelativeLayout.LayoutParams(iconSize, iconSize));
					bUse = true;
					break;
				}
			}
			if(!bUse){
				mUnusedSection.addView(mCircleItems[i], new RelativeLayout.LayoutParams(iconSize, iconSize));
			}
		}
		drawble.recycle();
		
		updateCircleItems();
	}
	
	class UnusedGestureListener implements OnGestureListener{
		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			for (int i = 0; i < C.NUM_CIRCLE_ITEMS; i++) {
				boolean bUsed = false;
				for (int j = 0; j < G.settings.mCircleSection.length; j++) {
					if(G.settings.mCircleSection[j] == i){
						bUsed = true;
						break;
					}
				}
				if (!bUsed){
					if (ViewBase.isPointInsideRect(e.getX(), e.getY(), 
							mCircleItems[i].getX(), mCircleItems[i].getY(),
							mCircleItems[i].getWidth(), mCircleItems[i].getHeight())) {
						Drawable oldDrawable = mCircleItems[i].getDrawable();
						mCircleItems[i].setBackgroundResource(R.drawable.light_circle_wrapper);
						DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(mCircleItems[i]);
						ClipData data = ClipData.newPlainText(DRAG_FROM_UNUSED_TAG, String.valueOf(i));
						mCircleItems[i].startDrag(data, shadowBuilder, mCircleItems[i], 0);
						mCircleItems[i].setVisibility(View.INVISIBLE);
						mCircleItems[i].setImageDrawable(oldDrawable);
						break;
					}
				}
			}
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}
	}
	
	class BigCircleGestureListener implements OnGestureListener{
		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			int circleSize = (int) getResources().getDimension(
					R.dimen.big_circle_size);
			//If the mouse is not inside the transparent inner circle
			if(!ViewBase.isPointInsideCircle((int) e1.getX(), (int) e1.getY(),
					circleSize/2, circleSize/2, 
					(int) ViewBase.dpToPx(C.INNER_CIRCLE_RADIUS))){
				double oldAngel = Math.atan2(e1.getY() - circleSize/2, e1.getX() - circleSize/2);
				double newAngle = Math.atan2(e2.getY() - circleSize/2, e2.getX() - circleSize/2);
				dTempAngel = oldAngel - newAngle;
				updateCircleItems();
			}
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			for (int i = 0; i < G.settings.mCircleSection.length; i++) {
				final int RADIUS_COMPLEMENT = 8;
				int iconSize = (int) getResources().getDimension(
						R.dimen.circle_item_size);
				int item = G.settings.mCircleSection[i];
				if (ViewBase.isPointInsideCircle((int)e.getX(), (int)e.getY(), 
						(int)(mCircleItems[item].getX() + mCircleItems[item].getWidth() / 2),
						(int)(mCircleItems[item].getY() + mCircleItems[item].getHeight() / 2),
						(int)(iconSize/2 + ViewBase.dpToPx(RADIUS_COMPLEMENT)))) {
					Drawable oldDrawable = mCircleItems[item].getDrawable();
					mCircleItems[item].setBackgroundResource(R.drawable.light_circle_wrapper);
					DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(mCircleItems[item]);
					ClipData data = ClipData.newPlainText(DRAG_FROM_CIRCLE_TAG, String.valueOf(i));
					mCircleItems[item].startDrag(data, shadowBuilder, mCircleItems[item], 0);
					mCircleItems[item].setVisibility(View.INVISIBLE);
					mCircleItems[item].setImageDrawable(oldDrawable);
					break;
				}
			}
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}
	}

	private void updateCircleItems(){
		int iconSize = (int) getResources().getDimension(
				R.dimen.circle_item_size);
		int circleSize = (int) getResources().getDimension(
				R.dimen.big_circle_size);

		int unusedCount = 0;
		for (int i = 0; i < C.NUM_CIRCLE_ITEMS; i++) {
			boolean bUsed = false;
			mCircleItems[i].setBackgroundResource(0);
			for (int j = 0; j < G.settings.mCircleSection.length; j++) {
				if(G.settings.mCircleSection[j] == i){
					//Each x from center = radius * sin(a)
					//Each y from center = radius * cos(a)
					mCircleItems[i].setX((float) (circleSize / 2 + ViewBase.dpToPx(C.INNER_CIRCLE_RADIUS)
									* Math.sin(((360 / G.settings.mCircleSection.length) * (j + C.ITEM_POSITION_OFFSET))
											* Math.PI / 180.0 + G.settings.dCircleAngle + dTempAngel) - iconSize / 2));
					mCircleItems[i].setY((float) (circleSize / 2 + ViewBase.dpToPx(C.INNER_CIRCLE_RADIUS)
									* Math.cos(((360 / G.settings.mCircleSection.length) * (j + C.ITEM_POSITION_OFFSET))
											* Math.PI / 180.0 + G.settings.dCircleAngle + dTempAngel) - iconSize / 2));
					bUsed = true;
					break;
				}
			}
			if (!bUsed){
				mCircleItems[i].setX((unusedCount%3)*iconSize);
				mCircleItems[i].setY((unusedCount/3)*iconSize);
				unusedCount ++;
			}
		}
		mUnusedSection.getLayoutParams().height = (unusedCount/3 + 1)*iconSize;
	}

	@Override
	public boolean onDrag(View v, DragEvent event) {
	    switch (event.getAction()) {
	    case DragEvent.ACTION_DRAG_STARTED:
	    	break;
	    case DragEvent.ACTION_DRAG_ENTERED:
	    	break;
	    case DragEvent.ACTION_DRAG_EXITED:    
	    	break;
	    case DragEvent.ACTION_DROP:
	    	ClipData data = event.getClipData();
    		if(data != null && data.getItemCount() == 1){
    			Item dataItem = data.getItemAt(0);
		    	String tag = (String) data.getDescription().getLabel();
	    		int item = Integer.valueOf((String) dataItem.getText());
	    		ImageView view = (ImageView) event.getLocalState();
	    		view.setVisibility(View.VISIBLE);
		    	if(tag.compareTo(DRAG_FROM_CIRCLE_TAG) == 0){
		    		// If from circle to unused
		    		if(v == mUnusedSection){
		    			// Settings can't be removed.
		    			if(G.settings.mCircleSection[item] == 4)
		    			{
		    				Helper.pushText(getResources().getString(R.string.circle_layout_setting_cannot_remove_settings_icon));
		    				return true;
		    			}
		    			((ViewGroup) view.getParent()).removeView(view);
		    			mUnusedSection.addView(view);
		    			G.settings.deleteCircleItem(item);
		    		}else if(v == mBigCircle){// If from circle to circle
		    			int circleSize = (int) getResources().getDimension(
		    					R.dimen.big_circle_size);
		    			int iconSize = (int) getResources().getDimension(
		    					R.dimen.circle_item_size);
						double offsetAngle = Math.atan2(mCircleItems[G.settings.mCircleSection[0]].getY() + iconSize/2 - circleSize/2,
								mCircleItems[G.settings.mCircleSection[0]].getX() + iconSize/2 - circleSize/2);
						double mouseAngle = Math.atan2(event.getY() - circleSize/2,
								event.getX() - circleSize/2) - offsetAngle;
						if (mouseAngle > 0){
							mouseAngle -= 2 * Math.PI;
						}
						int j = (int) - (mouseAngle / (2 * Math.PI / G.settings.mCircleSection.length)) + 1;
						if (j >= G.settings.mCircleSection.length)
							j = 0;
						byte b = G.settings.mCircleSection[item];
						G.settings.deleteCircleItem(item);
		    			G.settings.addCircleItem(b, j);
		    			updateCircleItems();
		    			
		    		}

					
		    		updateCircleItems();
		    	}else if(tag.compareTo(DRAG_FROM_UNUSED_TAG) == 0){
		    		// If from unused to unused
		    		if(v == mUnusedSection){
		    			return true;
		    		}else if(v == mBigCircle){// If from unused to circle
		    			int circleSize = (int) getResources().getDimension(
		    					R.dimen.big_circle_size);
		    			int iconSize = (int) getResources().getDimension(
		    					R.dimen.circle_item_size);
						double offsetAngle = Math.atan2(mCircleItems[G.settings.mCircleSection[0]].getY() + iconSize/2 - circleSize/2,
								mCircleItems[G.settings.mCircleSection[0]].getX() + iconSize/2 - circleSize/2);
						double mouseAngle = Math.atan2(event.getY() - circleSize/2,
								event.getX() - circleSize/2) - offsetAngle;
						if (mouseAngle > 0){
							mouseAngle -= 2 * Math.PI;
						}
						int j = (int) - (mouseAngle / (2 * Math.PI / G.settings.mCircleSection.length)) + 1;
						if (j >= G.settings.mCircleSection.length)
							j = 0;
						((ViewGroup) view.getParent()).removeView(view);
		    			mBigCircle.addView(view);
		    			G.settings.addCircleItem((byte)item, j);
		    			updateCircleItems();
		    			return true;
		    		}
		    	}
    		}
    		break;
	    case DragEvent.ACTION_DRAG_ENDED:
	    	//If the drag event was not handled
	    	if(!event.getResult()){
	    		ImageView view = (ImageView) event.getLocalState();
	    		view.setVisibility(View.VISIBLE);
	    	}
	    	break;
	    default:
	    	break;
	    }
	    return true;
	}


}
