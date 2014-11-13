package com.pujoy.charminder.views;

import java.util.Random;

import com.pujoy.charminder.R;
import com.pujoy.charminder.activities.ReminderListActivity;
import com.pujoy.charminder.activities.SettingsActivity;
import com.pujoy.charminder.base.WindowBase;
import com.pujoy.charminder.other.C;
import com.pujoy.charminder.other.G;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Charmy extends WindowBase implements OnTouchListener,
		OnClickListener {
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
		mMainView.setOnClickListener(this);
		mMainView.setOnTouchListener(this);
		mIcon = new ImageView(G.context);
		mIcon.setImageResource(R.drawable.charmy);
		mIcon.setOnClickListener(this);
		mIcon.setOnTouchListener(this);
		mMainView.addView(mIcon);
		mIconCenter = new ImageView(G.context);
		mIconCenter.setImageResource(R.drawable.charmy_center);
		mIconCenter.setOnClickListener(this);
		mIconCenter.setOnTouchListener(this);
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

	@Override
	public void onClick(View v) {
		if (bBeingRemoved)
			return;
		if (mBubble.isCreated()) {
			mBubble.remove();
		}
		Babble();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (bBeingRemoved)
			return false;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mBubble.isCreated()) {
				mBubble.remove();
			}
			mMainCircle = new MainCircle();
			mMainCircle.create();
			fOldMouseX = event.getRawX();
			fOldMouseY = event.getRawY();
			fX = mLayoutParams.getX();
			fY = mLayoutParams.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			fX += event.getRawX() - fOldMouseX;
			fY += event.getRawY() - fOldMouseY;
			mLayoutParams.setX((int) (fX));
			mLayoutParams.setY((int) (fY));
			fOldMouseX = event.getRawX();
			fOldMouseY = event.getRawY();
			onUpdateLayout();
			update();
			for (int i = 0; i < mMainCircle.mCircleItems.length; i++) {
				if (mMainCircle.isPointInsideItem(event.getRawX(),
						event.getRawY(), i)) {
					mMainCircle.Hover(i);
					mMainCircle.mCircleDescription
							.setBackgroundColor(C.COLOR_DARKBLUE_TRANSLUCENT);
					break;
				} else {
					if (i == mMainCircle.mCircleItems.length - 1) {
						mMainCircle.mCircleDescription.setText("");
						mMainCircle.mCircleDescription
								.setBackgroundColor(C.COLOR_TRANSPARENT);
						mMainCircle.updateOldHoverItem();
						mMainCircle.iHoveringItem = 0;
					}

				}
			}
			break;
		case MotionEvent.ACTION_UP:
			G.mTimerThread.moveIconToCorner();
			for (int i = 0; i < mMainCircle.mCircleItems.length; i++) {
				if (mMainCircle.isPointInsideItem(event.getRawX(),
						event.getRawY(), i)) {
					if (i < G.settings.mCircleSection.length)
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
			mMainCircle.remove();
			mMainCircle = null;
			break;
		}
		return false;
	}

	public void Babble() {
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
}
