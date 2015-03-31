/*
**  Class WindowDialogWithStars
**  src/com/CharmySoft/charminder/base/WindowDialogWithStars.java
*/
package com.CharmySoft.charminder.base;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.activities.ReminderEditorActivity;
import com.CharmySoft.charminder.helper.FunctionWrapper;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public abstract class WindowDialogWithStars extends WindowDialog {

	private TextView mPriority;
	private ImageView[] mStar;
	protected int iPriority = 3;
	
	abstract protected void addReminder(boolean pushBubble);

	@Override
	public void create() {
		super.create();
		ValueAnimator[] aStarX = new ValueAnimator[5];
		ValueAnimator[] aStarY = new ValueAnimator[5];
		for (int i = 0; i < 5; i++) {
			float toX = ((float) (mLayoutParams.getWidth() / 2
					+ Math.sin(((120 / 5) * (i - 2)) * Math.PI / 180.0)
					* (mLayoutParams.getWidth() * 0.4) - dpToPx(32) / 2));
			float toY = ((float) (mLayoutParams.getHeight()
					- mLayoutParams.getWidth() / 2
					+ Math.cos(((120 / 5) * (i - 2)) * Math.PI / 180.0)
					* (mLayoutParams.getWidth() * 0.4) - dpToPx(32) / 2));

			aStarX[i] = ObjectAnimator.ofFloat(mStar[i], "x",
					mLayoutParams.getWidth() / 2 - dpToPx(32) / 2, toX);
			aStarX[i].setDuration(ANIMATION_DURATION);
			aStarY[i] = ObjectAnimator.ofFloat(mStar[i], "y",
					mLayoutParams.getHeight() - mLayoutParams.getWidth() / 2
							- dpToPx(32) / 2, toY);
			aStarY[i].setDuration(ANIMATION_DURATION);
			aStarX[i].start();
			aStarY[i].start();
		}

		ValueAnimator aLevelY = ObjectAnimator
				.ofFloat(
						mPriority,
						"y",
						mLayoutParams.getHeight()
								- mLayoutParams.getWidth()
								/ 2
								+ mLayoutParams.getWidth()
								* CENTER_PROPORTION
								/ 2
								+ dpToPx(4)
								- (mLayoutParams.getHeight() - mLayoutParams
										.getWidth()) / 2,
						mLayoutParams.getHeight() - mLayoutParams.getWidth()
								/ 2 + mLayoutParams.getWidth()
								* CENTER_PROPORTION / 2 + dpToPx(4));
		aLevelY.setDuration(ANIMATION_DURATION);
		aLevelY.start();

	}

	@Override
	public void remove() {
		super.remove();
		ValueAnimator[] aStarX = new ValueAnimator[5];
		ValueAnimator[] aStarY = new ValueAnimator[5];
		for (int i = 0; i < 5; i++) {
			aStarX[i] = ObjectAnimator.ofFloat(mStar[i], "x", mStar[i].getX(),
					mLayoutParams.getWidth() / 2);
			aStarX[i].setDuration(ANIMATION_DURATION);
			aStarY[i] = ObjectAnimator.ofFloat(mStar[i], "y", mStar[i].getY(),
					mLayoutParams.getHeight() - mLayoutParams.getWidth() / 2);
			aStarY[i].setDuration(ANIMATION_DURATION);
			aStarX[i].start();
			aStarY[i].start();
		}

		ValueAnimator aLevelY = ObjectAnimator
				.ofFloat(
						mPriority,
						"y",
						mLayoutParams.getHeight() - mLayoutParams.getWidth()
								/ 2 + mLayoutParams.getWidth()
								* CENTER_PROPORTION / 2 + dpToPx(4),
						mLayoutParams.getHeight()
								- mLayoutParams.getWidth()
								/ 2
								+ mLayoutParams.getWidth()
								* CENTER_PROPORTION
								/ 2
								+ dpToPx(4)
								- (mLayoutParams.getHeight() - mLayoutParams
										.getWidth()) / 2);
		aLevelY.setDuration(ANIMATION_DURATION);
		aLevelY.start();

	}

	@Override
	protected void onInitialize(boolean bFullUpperCircle) {
		super.onInitialize(bFullUpperCircle);
		mStar = new ImageView[5];
		iPriority = 3;
		View.OnTouchListener StarsTouchListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction()) {
				case MotionEvent.ACTION_MOVE:
					for (int i = 0; i < 5; i++) {
						if (motionEvent.getRawX() >= mLayoutParams.x
								+ mStar[i].getX()
								&& motionEvent.getRawX() < mLayoutParams.x
										+ mStar[i].getX() + (int) dpToPx(32)) {
							iPriority = i + 1;
							UpdateStarImage();
						}
					}
					break;
				case MotionEvent.ACTION_UP:
				{
					v.performClick();
					return true;
				}
				}  
				return false;
			}
		};
		for (int i = 0; i < 5; i++) {
			mStar[i] = new ImageView(G.context);
			mStar[i].setLayoutParams(new LayoutParams((int) dpToPx(32),
					(int) dpToPx(32)));
			mStar[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < 5; i++) {
						if (v == mStar[i]) {
							iPriority = i + 1;
							UpdateStarImage();
						}
					}
				}

			});
			mStar[i].setOnTouchListener(StarsTouchListener);
			addToMainView(mStar[i]);
		}
		UpdateStarImage();

		mPriority = new TextView(G.context);
		mPriority.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		mPriority.setGravity(Gravity.CENTER);
		mPriority.setTextColor(C.COLOR_LIGHTBLUE);
		mPriority.setText(G.context.getString(R.string.priority));
		mPriority.setLayoutParams(new LayoutParams((int) dpToPx(64),
				(int) dpToPx(32)));
		mPriority.setX(mLayoutParams.getWidth() / 2 - dpToPx(64) / 2);
		mPriority.setY(mLayoutParams.getHeight() - mLayoutParams.getWidth() / 2
				+ mLayoutParams.getWidth() * CENTER_PROPORTION / 2 + dpToPx(4));
		addToMainView(mPriority);

	}

	@Override
	protected void onInitialize() {
		onInitialize(false);
	}
	
	@Override
	protected void onDestroy() {
		for (int i = 0; i < mStar.length; i ++){
			mStar[i].getDrawingCache().recycle();
			removeFromMainView(mStar[i]);
			mStar[i] = null;
		}
		mStar = null;
		removeFromMainView(mPriority);
		mPriority = null;
		super.onDestroy();
	}
	
	@Override
	protected void onUpdateLayout(){
		super.onUpdateLayout();
	}
	
	protected void startEditing(){
		removeImmediate();
		addReminder(false);
		ReminderEditorActivity.iIndex = G.reminders.size() - 1;
		ReminderEditorActivity.mOnOkLisntener = null;
		ReminderEditorActivity.mOnCancelLisntener = new FunctionWrapper(){

			@Override
			public void function() {
				G.reminders.remove(G.reminders.size() - 1);
			}
			
		};
		G.context.startActivity(new Intent(G.context, ReminderEditorActivity.class));
	}

	private void UpdateStarImage() {
		for (int i = 0; i < 5; i++) {
			if (iPriority <= i) {
				mStar[i].setImageResource(R.drawable.star0);
			} else {
				mStar[i].setImageResource(R.drawable.star1);
			}
		}
	}

}
