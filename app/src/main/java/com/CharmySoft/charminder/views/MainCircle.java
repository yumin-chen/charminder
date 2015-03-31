/*
**  Class MainCircle
**  src/com/CharmySoft/charminder/view/MainCircle.java
*/
package com.CharmySoft.charminder.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.activities.ReminderListActivity;
import com.CharmySoft.charminder.activities.SettingsActivity;
import com.CharmySoft.charminder.base.WindowBase;
import com.CharmySoft.charminder.helper.Helper;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public class MainCircle extends WindowBase implements OnClickListener{
	RelativeLayout mMainView;
	ImageView mBackground;
	TextView mCircleDescription;
	ImageView mCloseButton;
	ImageView mQuestionButton;
	public ImageView[] mCircleItems;
	private int iOldHover;
	public int iHoveringItem;
	private static final int ICON_WIDTH = 60;
	private static final int ANIMATION_DURATION = 200;

	@Override
	protected void onInitialize() {
		mMainView = new RelativeLayout(G.context);
		mBackground = new ImageView(G.context);
		mBackground.setImageResource(R.drawable.circle_bg);
		mCircleDescription = new TextView(G.context);
		RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
				(int) dpToPx(96), (int) dpToPx(96));
		onUpdateLayout();
		textParams.leftMargin = (mLayoutParams.getWidth() - (int) dpToPx(96)) / 2;
		textParams.topMargin = (mLayoutParams.getHeight() - (int) dpToPx(96)) / 2;
		mCircleDescription.setGravity(Gravity.CENTER);
		mCircleDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP,
				16 + G.getLanguage() * 5);
		mCircleDescription.setTextColor(C.COLOR_LIGHTBLUE);
		
		mCloseButton = new ImageView(G.context);
		mCloseButton.setImageResource(R.drawable.close_circle);
		mCloseButton.setLayoutParams(new LayoutParams((int) dpToPx(48),
				(int) dpToPx(48)));
		mCloseButton.setBackgroundResource(R.drawable.darkblue_circle_wrapped);
		mCloseButton.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.drawable.darkblue_circle_wrapped_fired_r);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundResource(R.drawable.darkblue_circle_wrapped);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(R.drawable.darkblue_circle_wrapped);
					v.performClick();
					break;
				}
				return false;
			}
			
		});
		mCloseButton.setOnClickListener(this);
		mMainView.addView(mCloseButton);
		
		mQuestionButton = new ImageView(G.context);
		mQuestionButton.setImageResource(R.drawable.question_button);
		mQuestionButton.setLayoutParams(new LayoutParams((int) dpToPx(48),
				(int) dpToPx(48)));
		mQuestionButton.setBackgroundResource(R.drawable.darkblue_circle_wrapped);
		mQuestionButton.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.drawable.darkblue_circle_wrapped_fired_g);
					break;
				case MotionEvent.ACTION_CANCEL:
					v.setBackgroundResource(R.drawable.darkblue_circle_wrapped);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(R.drawable.darkblue_circle_wrapped);
					v.performClick();
					break;
				}
				return false;
			}
			
		});
		mQuestionButton.setOnClickListener(this);
		mMainView.addView(mQuestionButton);
		
		mMainView.addView(mCircleDescription, textParams);
		mBackground.setLayoutParams(new LayoutParams(mLayoutParams.getWidth(),
				mLayoutParams.getHeight()));
		mMainView.addView(mBackground);

		TypedArray drawable = G.context.getResources().obtainTypedArray(
				R.array.main_menu_icons);
		mCircleItems = new ImageView[G.settings.mCircleSection.length];
		RelativeLayout.LayoutParams[] params = new RelativeLayout.LayoutParams[G.settings.mCircleSection.length];
		for (int i = 0; i < G.settings.mCircleSection.length; i++) {
			mCircleItems[i] = new ImageView(G.context);
			params[i] = new RelativeLayout.LayoutParams(
					(int) dpToPx(ICON_WIDTH), (int) dpToPx(ICON_WIDTH));
			params[i].leftMargin = 0;
			params[i].topMargin = 0;
			mCircleItems[i].setImageDrawable(drawable
					.getDrawable(G.settings.mCircleSection[i]));
			mMainView.addView(mCircleItems[i], params[i]);
		}
		drawable.recycle();
		
		final GestureDetector gestureDetector = new GestureDetector(G.context, new MainCircleGestureListener());
		mMainView.setOnTouchListener(new OnTouchListener(){

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP){
					clearHover();
				}
				return gestureDetector.onTouchEvent(event);
			}
			
		});
	}
	
	@Override
	protected void onDestroy() {
		mMainView = null;
		mBackground = null;
		mCircleDescription = null;
		for (int i = 0; i < mCircleItems.length; i++) {
			mCircleItems[i].setImageBitmap(null);
			mCircleItems[i] = null;
		}
		mCircleItems = null;
		mCloseButton.setImageBitmap(null);
		mCloseButton = null;
		mQuestionButton.setImageBitmap(null);
		mQuestionButton = null;
		super.onDestroy();
	}

	@Override
	protected void onCreate() {
		addView(mMainView, mLayoutParams);
		mCircleDescription.setText("");
		mCircleDescription.setBackgroundColor(C.COLOR_TRANSPARENT);
		Animation animation = AnimationUtils.loadAnimation(G.context,
				R.anim.zoom);
		mBackground.startAnimation(animation);
		for (int i = 0; i < mCircleItems.length; i++) {
			//Each x from center = radius * sin(a)
			//Each y from center = radius * cos(a)
			float tox = (float) (mLayoutParams.getWidth()
					/ 2
					+ dpToPx(C.INNER_CIRCLE_RADIUS)
					* Math.sin(((360 / mCircleItems.length) * (i + C.ITEM_POSITION_OFFSET))
							* Math.PI / 180.0 + G.settings.dCircleAngle) - dpToPx(ICON_WIDTH) / 2);
			float toy = (float) (mLayoutParams.getHeight()
					/ 2
					+ dpToPx(C.INNER_CIRCLE_RADIUS)
					* Math.cos(((360 / mCircleItems.length) * (i + C.ITEM_POSITION_OFFSET))
							* Math.PI / 180.0 + G.settings.dCircleAngle) - dpToPx(ICON_WIDTH) / 2);
			float fromx = (float) (mLayoutParams.getWidth()
					/ 2
					+ dpToPx(C.INNER_CIRCLE_RADIUS)
					* Math.sin(((360 / mCircleItems.length) * (i + C.ITEM_POSITION_OFFSET))
							* Math.PI / 180.0 + G.settings.dCircleAngle) / 4 - dpToPx(ICON_WIDTH) / 2);
			float fromy = (float) (mLayoutParams.getHeight()
					/ 2
					+ dpToPx(C.INNER_CIRCLE_RADIUS)
					* Math.cos(((360 / mCircleItems.length) * (i + C.ITEM_POSITION_OFFSET))
							* Math.PI / 180.0 + G.settings.dCircleAngle) / 4 - dpToPx(ICON_WIDTH) / 2);
			ValueAnimator aCircleItemsY = ObjectAnimator.ofFloat(
					mCircleItems[i], "y", fromy, toy);
			aCircleItemsY.setDuration(ANIMATION_DURATION);
			ValueAnimator aCircleItemsX = ObjectAnimator.ofFloat(
					mCircleItems[i], "x", fromx, tox);
			aCircleItemsX.setDuration(ANIMATION_DURATION);
			ValueAnimator aCircleItemsAlpha = ObjectAnimator.ofFloat(
					mCircleItems[i], "alpha", 0, 1.5f);
			aCircleItemsAlpha.setDuration(ANIMATION_DURATION);
			aCircleItemsY.start();
			aCircleItemsX.start();
			aCircleItemsAlpha.start();
		}
		
		ValueAnimator aCircleItemsY = ObjectAnimator.ofFloat(
				mCloseButton, "y", mLayoutParams.getHeight()
				/ 2, 0);
		aCircleItemsY.setDuration(ANIMATION_DURATION);
		ValueAnimator aCircleItemsX = ObjectAnimator.ofFloat(
				mCloseButton, "x", mLayoutParams.getWidth()
				/ 2, mLayoutParams.getWidth() - dpToPx(48));
		aCircleItemsX.setDuration(ANIMATION_DURATION);
		ValueAnimator aCircleItemsAlpha = ObjectAnimator.ofFloat(
				mCloseButton, "alpha", 0, 1.5f);
		aCircleItemsAlpha.setDuration(ANIMATION_DURATION);
		aCircleItemsAlpha.start();
		aCircleItemsX.start();
		aCircleItemsY.start();
		
		aCircleItemsY = ObjectAnimator.ofFloat(
				mQuestionButton, "y", mLayoutParams.getHeight()
				/ 2, 0);
		aCircleItemsY.setDuration(ANIMATION_DURATION);
		aCircleItemsX = ObjectAnimator.ofFloat(
				mQuestionButton, "x", mLayoutParams.getWidth()
				/ 2, 0);
		aCircleItemsX.setDuration(ANIMATION_DURATION);
		aCircleItemsAlpha = ObjectAnimator.ofFloat(
				mQuestionButton, "alpha", 0, 1.5f);
		aCircleItemsAlpha.setDuration(ANIMATION_DURATION);
		aCircleItemsAlpha.start();
		aCircleItemsX.start();
		aCircleItemsY.start();

	}

	@Override
	protected void onRemove() {
		removeView(mMainView);
	}

	@Override
	protected void onUpdateLayout() {
		super.onUpdateLayout();
		mLayoutParams.setWidth((int) dpToPx(240));
		mLayoutParams.setHeight((int) dpToPx(240));
		mLayoutParams.setX((getScreenWidth() - mLayoutParams.getWidth()) / 2);
		mLayoutParams.setY((getScreenHeight() - mLayoutParams.getHeight()) / 2);
	}

	public int getX() {
		return mLayoutParams.getX();
	}

	public int getY() {
		return mLayoutParams.getY();
	}

	public void Hover(int i) {
		if (G.mCharmy.mBubble.isCreated()){
			G.mCharmy.mBubble.remove();
		}
		if (iHoveringItem != i + 1) {
			iHoveringItem = i + 1;
		} else {
			return;
		}
		String[] namesOnCircle = G.context.getResources().getStringArray(
				R.array.main_menu_names_on_circle);
		mCircleDescription.setText(namesOnCircle[G.settings.mCircleSection[i]]);
		updateOldHoverItem();
		mCircleItems[i].setBackgroundResource(R.drawable.light_circle_wrapper);
		iOldHover = i + 1;
	}

	public void updateOldHoverItem() {

		if (iOldHover > 0) {
			int i = iOldHover - 1;
			mCircleItems[i].setBackgroundResource(0);
		}
		iOldHover = 0;
	}

	public boolean isPointInsideItem(float pointX, float pointY, int item) {
		final int RADIUS_COMPLEMENT = 8;
		return isPointInsideCircle(
				(int) pointX,
				(int) pointY,
				(int) (getX() + mCircleItems[item].getX() + mCircleItems[item]
						.getWidth() / 2),
				(int) (getY() + mCircleItems[item].getY() + mCircleItems[item]
						.getHeight() / 2),
				(int) (mCircleItems[item].getWidth() / 2 + dpToPx(RADIUS_COMPLEMENT)));
	}

	private void clearHover() {
		mCircleDescription.setText("");
		mCircleDescription.setBackgroundColor(C.COLOR_TRANSPARENT);
		updateOldHoverItem();
		iHoveringItem = 0;
	}
	
	class MainCircleGestureListener implements OnGestureListener{

		@Override
		public boolean onDown(MotionEvent e) {
			for (int i = 0; i < mCircleItems.length; i++) {
				if (isPointInsideItem(e.getRawX(),
						e.getRawY(), i)) {
					Hover(i);
					mCircleDescription.setBackgroundColor(C.COLOR_DARKBLUE_TRANSLUCENT);
					break;
				} else {
					if (i == mCircleItems.length - 1) {
						clearHover();
					}

				}
			}
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			for (int i = 0; i < mCircleItems.length; i++) {
				if (isPointInsideItem(e.getRawX(),
						e.getRawY(), i)) {
					if (i < G.settings.mCircleSection.length){
						String[] names = G.context.getResources().getStringArray(
								R.array.main_menu_names);
						String[] descriptions = G.context.getResources().getStringArray(
								R.array.main_menu_descriptions);
						Helper.pushText(String.format(
								descriptions[G.settings.mCircleSection[i]],
								names[G.settings.mCircleSection[i]]));
					}
				}
			}
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			onDown(e2);
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			for (int i = 0; i < mCircleItems.length; i++) {
				if (isPointInsideItem(e.getRawX(),
						e.getRawY(), i)) {
					if (i < G.settings.mCircleSection.length)
						remove();
						switch (G.settings.mCircleSection[i]) {
						case 0: {
							Timer1 timer1 = new Timer1();
							timer1.create();
							break;
						}
						case 1: {
							Timer2 timer2 = new Timer2();
							timer2.create();
							break;
						}
						case 2: {
							Timer3 timer3 = new Timer3();
							timer3.create();
							break;
						}
						case 3: {
							Timer4 timer4 = new Timer4();
							timer4.create();
							break;
						}
						case 4:
							G.goToActivity(SettingsActivity.class);
							break;
						case 5:
							G.goToActivity(ReminderListActivity.class);
							break;
						case 6: {
							G.exit();
							break;
						}
						}
					break;
				}
			}
			return true;
		}
		
	}

	@Override
	public void onClick(View v) {
		if(v == mQuestionButton){
			Helper.pushText(G.context.getString(R.string.maincircle_help));
		}
		if(v == mCloseButton){
			remove();
		}
	}

}
