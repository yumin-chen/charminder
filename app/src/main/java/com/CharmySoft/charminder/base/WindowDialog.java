/*
**  Class WindowDialog
**  src/com/CharmySoft/charminder/base/WindowDialog.java
*/
package com.CharmySoft.charminder.base;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public abstract class WindowDialog extends WindowBase {

	private RelativeLayout mMainView;
	private ImageView mUpperCircle;
	private ImageView mMiddleFiller;
	private ImageView mLowerCircle;
	private TextView mOkCircle;
	private TextView mCancelCircle;

	protected static final float NON_BORDER_SCALE = 0.960548885077187f;
	protected static final float BORDER_SCALE = 0.0221843003412969f;
	protected static final float CENTER_PROPORTION = 0.3720136518771331f;

	protected static final int ANIMATION_DURATION = 500;

	@Override
	protected abstract void onCreate();

	@Override
	protected abstract void onRemove();

	protected abstract void onOk();

	protected abstract void onCancel();

	public WindowDialog() {
		super();
	}

	@Override
	public void create() {
		super.create();
		if (mMiddleFiller != null){
			mMiddleFiller.setLayoutParams(new RelativeLayout.LayoutParams(
					mLayoutParams.getWidth(), mLayoutParams.getHeight()
							- mLayoutParams.getWidth()));
		}
		addView(mMainView, mLayoutParams);
		ValueAnimator aUpperCicleY = ObjectAnimator
				.ofFloat(mUpperCircle, "y", mLayoutParams.getHeight() / 2
						- mLayoutParams.getWidth() / 2, 0);
		aUpperCicleY.setDuration(ANIMATION_DURATION);
		ValueAnimator aLowerCicleY = ObjectAnimator.ofFloat(mLowerCircle, "y",
				mLayoutParams.getHeight() / 2, mLayoutParams.getHeight()
						- mLayoutParams.getWidth() / 2);
		aLowerCicleY.setDuration(ANIMATION_DURATION);
		ValueAnimator aMiddleFillerHeight = ObjectAnimator.ofFloat(
				mMiddleFiller, "scaleY", 0, 1);
		aMiddleFillerHeight.setDuration(ANIMATION_DURATION);
		ValueAnimator aOkCircleY = ObjectAnimator.ofFloat(mOkCircle, "y",
				mLayoutParams.getHeight() / 2
						- mOkCircle.getLayoutParams().height, 0);
		aOkCircleY.setDuration(ANIMATION_DURATION);

		ValueAnimator aCancelCircleY = ObjectAnimator.ofFloat(mCancelCircle,
				"y",
				mLayoutParams.getHeight() / 2
						- mCancelCircle.getLayoutParams().height, 0);
		aCancelCircleY.setDuration(ANIMATION_DURATION);
		aCancelCircleY.start();

		aUpperCicleY.start();
		aMiddleFillerHeight.start();
		aLowerCicleY.start();
		aOkCircleY.start();
	}
	
	public void removeImmediate(){
		removeView(mMainView);
		bCreated = false;
	}

	@Override
	public void remove() {
		if (!bCreated) {
			return;
		}

		ValueAnimator aUpperCicleY = ObjectAnimator
				.ofFloat(mUpperCircle, "y", 0, mLayoutParams.getHeight() / 2
						- mLayoutParams.getWidth() / 2);
		aUpperCicleY.setDuration(ANIMATION_DURATION);
		ValueAnimator aLowerCicleY = ObjectAnimator.ofFloat(mLowerCircle, "y",
				mLayoutParams.getHeight() - mLayoutParams.getWidth() / 2,
				mLayoutParams.getHeight() / 2);
		aLowerCicleY.setDuration(ANIMATION_DURATION);
		ValueAnimator aMiddleFillerHeight = ObjectAnimator.ofFloat(
				mMiddleFiller, "scaleY", 1, 0);
		aMiddleFillerHeight.setDuration(ANIMATION_DURATION);
		ValueAnimator aOkCircleY = ObjectAnimator.ofFloat(mOkCircle, "y", 0,
				mLayoutParams.getHeight() / 2
						- mOkCircle.getLayoutParams().height);
		aOkCircleY.setDuration(ANIMATION_DURATION);

		ValueAnimator aCancelCircleY = ObjectAnimator.ofFloat(mCancelCircle,
				"y", 0,
				mLayoutParams.getHeight() / 2
						- mCancelCircle.getLayoutParams().height);
		aCancelCircleY.setDuration(ANIMATION_DURATION);
		aCancelCircleY.start();

		aUpperCicleY.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				removeImmediate();
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				removeImmediate();
			}
		});

		aUpperCicleY.start();
		aMiddleFillerHeight.start();
		aLowerCicleY.start();
		aOkCircleY.start();

		onRemove();
	}

	public void addToMainView(View child) {
		mMainView.addView(child);
		onInitSortView();
	}

	public void removeFromMainView(View child) {
		mMainView.removeView(child);
	}

	protected void onInitialize(boolean bFullUpperCircle) {
		onUpdateLayout();
		mMainView = new RelativeLayout(G.context);
		mUpperCircle = new ImageView(G.context);
		mLowerCircle = new ImageView(G.context);
		mMiddleFiller = new ImageView(G.context);
		mMiddleFiller.setY(mLayoutParams.getWidth() / 2);
		mMiddleFiller.setLayoutParams(new LayoutParams(
				mLayoutParams.getWidth(), mLayoutParams.getHeight()
						- mLayoutParams.getWidth()));
		mMiddleFiller.setImageResource(R.drawable.semi_circle_fill);
		mMiddleFiller.setScaleType(ScaleType.FIT_XY);
		mMainView.layout(0, 0, mLayoutParams.getWidth(),
				mLayoutParams.getHeight());
		mUpperCircle.setLayoutParams(new LayoutParams(mLayoutParams.getWidth(),
				mLayoutParams.getWidth() / 2));
		mLowerCircle.setLayoutParams(new LayoutParams(mLayoutParams.getWidth(),
				mLayoutParams.getWidth() / 2));
		mUpperCircle
				.setImageResource(bFullUpperCircle ? R.drawable.semi_circle_full
						: R.drawable.semi_circle);
		mLowerCircle.setImageResource(R.drawable.semi_circle_bottom);
		mMainView.addView(mMiddleFiller);
		mMainView.addView(mUpperCircle);
		mMainView.addView(mLowerCircle);

		View.OnTouchListener OkCancelTouchListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundResource(R.drawable.small_circle_d);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(R.drawable.small_circle);
			        v.performClick();
					return true;
				}
				return false;
			}
		};

		mOkCircle = new TextView(G.context);
		mOkCircle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		mOkCircle.setGravity(Gravity.CENTER);
		mOkCircle.setTextColor(C.COLOR_DARKBLUE);
		mOkCircle.setText(G.context.getString(R.string.ok));
		mOkCircle.setBackgroundResource(R.drawable.small_circle);
		mOkCircle.setLayoutParams(new LayoutParams(
				(int) dpToPx(73.39249146757679f),
				(int) dpToPx(73.39249146757679f)));
		mOkCircle.setX(0);
		mOkCircle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onOk();
				remove();
			}
		});
		mOkCircle.setOnTouchListener(OkCancelTouchListener);
		mMainView.addView(mOkCircle);

		mCancelCircle = new TextView(G.context);
		mCancelCircle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		mCancelCircle.setGravity(Gravity.CENTER);
		mCancelCircle.setTextColor(C.COLOR_DARKBLUE);
		mCancelCircle.setText(G.context.getString(R.string.cancel));
		mCancelCircle.setBackgroundResource(R.drawable.small_circle);
		mCancelCircle.setLayoutParams(new LayoutParams(
				(int) dpToPx(73.39249146757679f),
				(int) dpToPx(73.39249146757679f)));
		mCancelCircle.setX(mLayoutParams.getWidth()
				- mCancelCircle.getLayoutParams().width);
		mCancelCircle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onCancel();
				remove();
			}
		});
		mCancelCircle.setOnTouchListener(OkCancelTouchListener);
		mMainView.addView(mCancelCircle);

	}

	@Override
	protected void onInitialize() {
		onInitialize(false);
	}
	
	@Override 
	protected void onDestroy(){
		mUpperCircle.setImageBitmap(null);
		removeFromMainView(mUpperCircle);
		mUpperCircle = null;
		mMiddleFiller.setImageBitmap(null);
		removeFromMainView(mMiddleFiller);
		mMiddleFiller = null;
		mLowerCircle.setImageBitmap(null);
		removeFromMainView(mLowerCircle);
		mLowerCircle = null;
		removeFromMainView(mOkCircle);
		mOkCircle = null;
		removeFromMainView(mCancelCircle);
		mCancelCircle = null;
		super.onDestroy();
	}
	
	@Override
	protected void onUpdateLayout(){
		super.onUpdateLayout();
	}

	protected void onInitSortView() {
		mOkCircle.bringToFront();
		if (mCancelCircle != null)
			mCancelCircle.bringToFront();
	}

	public void setOkText(String text) {
		mOkCircle.setText(text);
	}

	public void setCancelText(String text) {
		mCancelCircle.setText(text);
	}

}
