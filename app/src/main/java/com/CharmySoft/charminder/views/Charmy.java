/*
**  Class Charmy
**  src/com/CharmySoft/charminder/view/Charmy.java
*/
package com.CharmySoft.charminder.views;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.base.WindowBase;
import com.CharmySoft.charminder.other.G;

import java.util.Random;

public class Charmy extends WindowBase{
	RelativeLayout mMainView;
	ImageView mIcon;
	ImageView mIconCenter;
	public Bubble mBubble;
	MainCircle mMainCircle;
	private float fOldMouseX;
	private float fOldMouseY;
	private float fX;
	private float fY;
	private boolean bBeingRemoved;

	@Override
	protected void onInitialize() {
		mLayoutParams.setWidth((int) dpToPx(48));
		mLayoutParams.setHeight((int) dpToPx(48));
		mLayoutParams.setX(getScreenWidth() - mLayoutParams.getWidth());
		mLayoutParams.setY((int) (getScreenHeight() * 0.75 - mLayoutParams
				.getHeight()));
		mMainView = new RelativeLayout(G.context);
		final GestureDetector gestureDetector = new GestureDetector(G.context, new CharmyGestureListener());
		mMainView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (bBeingRemoved)
					return false;
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					G.mTimerThread.moveIconToCorner();
					v.performClick();
				}
				return gestureDetector.onTouchEvent(event);
			}
			
		});
		mIcon = new ImageView(G.context);
		mIcon.setDrawingCacheEnabled(true);
		mIcon.setImageResource(R.drawable.charmy);
		mMainView.addView(mIcon);
		mIconCenter = new ImageView(G.context);
		mIconCenter.setImageResource(R.drawable.charmy_center);
		mMainView.addView(mIconCenter);
		mBubble = new Bubble();
		
	}
	
	@Override
	protected void onDestroy() {
		mMainView = null;
		mIcon = null;
		mIconCenter = null;
		mBubble = null;
	}

	@Override
	protected void onCreate() {
		addView(mMainView, mLayoutParams);
		bBeingRemoved = false;
	}

	@Override
	public void remove() {
		if (!bCreated) {
			return;
		}
		bBeingRemoved = true;
		mIcon.setPivotX(mIcon.getWidth() / 2);
		mIcon.setPivotY(mIcon.getHeight() / 2);
		ValueAnimator aRotation = ObjectAnimator.ofFloat(mIcon, "rotation", 0,
				3600 / 2);
		aRotation.setDuration(3000);
		aRotation.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				onRemove();
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				onRemove();
			}
		});
		aRotation.start();

	}

	@Override
	protected void onRemove() {
		removeView(mMainView);
		if (mMainCircle != null) {
			mMainCircle.remove();
			mMainCircle = null;
		}
		if (mBubble.isCreated()) {
			mBubble.remove();
		}
		bCreated = false;
		super.onDestroy();
	}

	@Override
	protected void onUpdateLayout() {
		super.onUpdateLayout();
		mBubble.iIconPositionX = mLayoutParams.getX();
		mBubble.iIconPositionY = mLayoutParams.getY();
		mBubble.onUpdateLayout();
	}

	public void pushBubble(String bubbleText) {
		mBubble.setText(bubbleText);
		mBubble.create();
	}

	public void update() {
		updateViewLayout(mMainView, mLayoutParams);
		mBubble.update();
	}

	public void babble() {
		String[] babble_text = G.context.getResources().getStringArray(
				R.array.babble_bubble);
		Random r = new Random();
		pushBubble(babble_text[r.nextInt(babble_text.length)]);
	}

	public boolean MoveToCorner() {
		int toLeft = mLayoutParams.getX();
		int toRight = getScreenWidth() - mLayoutParams.getX()
				- mLayoutParams.getWidth();
		int toTop = mLayoutParams.getY();
		int toBottom = getScreenHeight() - mLayoutParams.getY()
				- mLayoutParams.getHeight();
		final int speed = (int) dpToPx(8);
		float retationSpeed = 12;
		boolean r = false;
		if (Math.min(toLeft, toRight) <= Math.min(toTop, toBottom)) {
			if (toLeft < toRight) {
				if (mLayoutParams.getX() != 0) {
					mLayoutParams.setX(mLayoutParams.getX() - speed);
					retationSpeed = -retationSpeed;
					r = true;
				}
			} else {
				if (mLayoutParams.getX() != getScreenWidth()
						- mLayoutParams.getWidth()) {
					mLayoutParams.setX(mLayoutParams.getX() + speed);
					r = true;
				}
			}
		} else {
			if (toTop < toBottom) {
				if (mLayoutParams.getY() != 0) {
					mLayoutParams.setY(mLayoutParams.getY() - speed);
					retationSpeed = -retationSpeed;
					r = true;
				}
			} else {
				if (mLayoutParams.getY() != getScreenHeight()
						- mLayoutParams.getHeight()) {
					mLayoutParams.setY(mLayoutParams.getY() + speed);
					r = true;
				}
			}
		}
		if (r) {
			mIcon.setPivotX(mIcon.getWidth() / 2);
			mIcon.setPivotY(mIcon.getHeight() / 2);
			mIcon.setRotation(mIcon.getRotation() + retationSpeed);
			onUpdateLayout();
			update();
		}
		return r;
	}
	
	class CharmyGestureListener implements OnGestureListener{
		@Override
		public boolean onDown(MotionEvent e) {
			if (mBubble.isCreated()) {
				mBubble.remove();
			}
			fOldMouseX = e.getRawX();
			fOldMouseY = e.getRawY();
			fX = mLayoutParams.getX();
			fY = mLayoutParams.getY();
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			babble();
			
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			fX += e2.getRawX() - fOldMouseX;
			fY += e2.getRawY() - fOldMouseY;
			mLayoutParams.setX((int) (fX));
			mLayoutParams.setY((int) (fY));
			fOldMouseX = e2.getRawX();
			fOldMouseY = e2.getRawY();
			onUpdateLayout();
			update();
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			if (mBubble.isCreated()) {
				mBubble.remove();
			}
			if (mMainCircle == null || !mMainCircle.isCreated())
			{
			mMainCircle = new MainCircle();
			mMainCircle.create();
			}else{
				babble();
			}
			return true;
		}
	}
}
