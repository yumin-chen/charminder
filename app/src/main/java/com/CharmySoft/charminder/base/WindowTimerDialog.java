/*
**  Class WindowTimerDialog
**  src/com/CharmySoft/charminder/base/WindowTimerDialog.java
*/
package com.CharmySoft.charminder.base;

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

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public abstract class WindowTimerDialog extends WindowDialogWithStars {

	protected TextView[] mNumKeys;
	private ImageView mAddNote;
	
	protected abstract void onKeyDown(int key);

	@Override
	public void create() {
		super.create();
		ValueAnimator[] aNumKeysAlpha = new ValueAnimator[10];
		for (int i = 0; i < 10; i++) {
			aNumKeysAlpha[i] = ObjectAnimator.ofFloat(mNumKeys[i], "alpha", 0,
					1);
			aNumKeysAlpha[i].setDuration(ANIMATION_DURATION);
			aNumKeysAlpha[i].start();
		}

	}

	@Override
	public void remove() {
		super.remove();

		ValueAnimator[] aNumKeysAlpha = new ValueAnimator[10];
		for (int i = 0; i < 10; i++) {
			aNumKeysAlpha[i] = ObjectAnimator.ofFloat(mNumKeys[i], "alpha", 1,
					0);
			aNumKeysAlpha[i].setDuration(ANIMATION_DURATION);
			aNumKeysAlpha[i].start();
		}

	}

	@Override
	protected void onInitialize(boolean bFullUpperCircle) {
		super.onInitialize(bFullUpperCircle);
		View.OnTouchListener NumKeysTouchListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(C.COLOR_GREENBLUE);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(C.COLOR_TRANSPARENT);
					v.performClick();
					return true;
				}
				return false;
			}
		};

		mNumKeys = new TextView[10];
		for (int i = 0; i < 10; i++) {
			mNumKeys[i] = new TextView(G.context);
			mNumKeys[i].setLayoutParams(new LayoutParams((int) (mLayoutParams
					.getWidth() * NON_BORDER_SCALE / 3) + 2,
					bFullUpperCircle ? (int) ((mLayoutParams.getHeight()
							- mLayoutParams.getWidth() + mLayoutParams
							.getWidth() / 2 * CENTER_PROPORTION) / 4 + 2)
							: (int) ((mLayoutParams.getHeight()
									- mLayoutParams.getWidth() + mLayoutParams
									.getWidth() * CENTER_PROPORTION) / 4 + 2)));
			mNumKeys[i].setX((int) (mLayoutParams.getWidth()
					* BORDER_SCALE
					+ (mLayoutParams.getWidth() * NON_BORDER_SCALE / 3)
					* (i % 3) - 1));
			if (bFullUpperCircle) {
				mNumKeys[i]
						.setY((int) (mLayoutParams.getWidth()
								/ 2
								+ (mLayoutParams.getHeight()
										- mLayoutParams.getWidth() + mLayoutParams
										.getWidth() / 2 * CENTER_PROPORTION)
								/ 4 * Math.floor(i / 3) - 1));
			} else {
				mNumKeys[i]
						.setY((int) (mLayoutParams.getWidth()
								/ 2
								- mLayoutParams.getWidth()
								* CENTER_PROPORTION
								/ 2
								+ (mLayoutParams.getHeight()
										- mLayoutParams.getWidth() + mLayoutParams
										.getWidth() * CENTER_PROPORTION) / 4
								* Math.floor(i / 3) - 1));
			}
			mNumKeys[i].setText((Integer.valueOf(i + 1)).toString());
			mNumKeys[i].setGravity(Gravity.CENTER);
			mNumKeys[i].setTextColor(C.COLOR_DARKBLUE);
			mNumKeys[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
			mNumKeys[i].setOnTouchListener(NumKeysTouchListener);
			mNumKeys[i].setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < 10; i++) {
						if (v == mNumKeys[i]) {
							onKeyDown(i);
						}
					}
				}

			});
			addToMainView(mNumKeys[i]);
		}
		mNumKeys[9].setX(mLayoutParams.getWidth() * BORDER_SCALE
				+ (mLayoutParams.getWidth() * NON_BORDER_SCALE / 3) * (10 % 3));
		mNumKeys[9].setText("0");

		mAddNote = new ImageView(G.context);
		mAddNote.setImageResource(R.drawable.edit);
		int height = bFullUpperCircle ? (int) ((mLayoutParams.getHeight()
				- mLayoutParams.getWidth() + mLayoutParams.getWidth() / 2
				* CENTER_PROPORTION) / 4 + 2) : (int) ((mLayoutParams
				.getHeight() - mLayoutParams.getWidth() + mLayoutParams
				.getWidth() * CENTER_PROPORTION) / 4 + 2);
		mAddNote.setLayoutParams(new LayoutParams(height - 2, height - 2));
		mAddNote.setX(mLayoutParams.getWidth() * BORDER_SCALE
				+ (mLayoutParams.getWidth() * NON_BORDER_SCALE / 3) * (11 % 3)
				+ (mLayoutParams.getWidth() * NON_BORDER_SCALE / 3) / 2
				- height / 2);
		if (bFullUpperCircle) {
			mAddNote.setY((int) (mLayoutParams.getWidth()
					/ 2
					+ (mLayoutParams.getHeight() - mLayoutParams.getWidth() + mLayoutParams
							.getWidth() / 2 * CENTER_PROPORTION) / 4
					* Math.floor(11 / 3) - 1));
		} else {
			mAddNote.setY((int) (mLayoutParams.getWidth()
					/ 2
					- mLayoutParams.getWidth()
					* CENTER_PROPORTION
					/ 2
					+ (mLayoutParams.getHeight() - mLayoutParams.getWidth() + mLayoutParams
							.getWidth() * CENTER_PROPORTION) / 4
					* Math.floor(11 / 3) - 1));
		}
		mAddNote.setOnTouchListener(NumKeysTouchListener);
		mAddNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startEditing();
			}

		});
		addToMainView(mAddNote);

	}

	@Override
	protected void onInitialize() {
		onInitialize(false);
	}
	
	@Override
	protected void onUpdateLayout(){
		super.onUpdateLayout();
	}
	
	@Override
	protected void onDestroy() {
		for (int i = 0; i < mNumKeys.length; i ++){
			removeFromMainView(mNumKeys[i]);
			mNumKeys[i] = null;
		}
		mNumKeys = null;
		mAddNote.setImageBitmap(null);
		removeFromMainView(mAddNote);
		mAddNote = null;
		super.onDestroy();
	}

}
