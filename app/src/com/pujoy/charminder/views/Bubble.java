package com.pujoy.charminder.views;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.WindowBase;
import com.pujoy.charminder.base.WindowLayoutParams;
import com.pujoy.charminder.other.C;
import com.pujoy.charminder.other.G;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class Bubble extends WindowBase implements OnClickListener {
	ImageView mMainView;
	TextView mTextView;
	Bitmap[] mBubbleBitmap;
	public int iTimer;
	public int iIconPositionX;
	public int iIconPositionY;
	private WindowLayoutParams mTextLayoutParams;

	@Override
	protected void onInitialize() {
		mMainView = new ImageView(G.context);
		mMainView.setOnClickListener(this);
		mTextView = new TextView(G.context);
		mTextView.setTextColor(C.COLOR_LIGHTBLUE);
		mTextView.setGravity(Gravity.CENTER);
		mTextView.setOnClickListener(this);
		mTextLayoutParams = new WindowLayoutParams();
		mBubbleBitmap = new Bitmap[4];
		mBubbleBitmap[0] = BitmapFactory.decodeResource(G.context.getResources(),
				R.drawable.bubble);
		Matrix matrix = new Matrix();
		matrix.preScale(-1, 1);
		mBubbleBitmap[1] = Bitmap.createBitmap(mBubbleBitmap[0], 0, 0, mBubbleBitmap[0].getWidth(),
				mBubbleBitmap[0].getHeight(), matrix, true);
		matrix.preScale(-1, -1);
		mBubbleBitmap[2] = Bitmap.createBitmap(mBubbleBitmap[0], 0, 0, mBubbleBitmap[0].getWidth(),
				mBubbleBitmap[0].getHeight(), matrix, true);
		matrix.preScale(-1, 1);
		mBubbleBitmap[3] = Bitmap.createBitmap(mBubbleBitmap[0], 0, 0, mBubbleBitmap[0].getWidth(),
				mBubbleBitmap[0].getHeight(), matrix, true);
	}

	public void setText(String text) {
		float fSize = (float) (22 - G.getLanguage() * 2 - (Math.sqrt(text
				.length()) / (1.4 - G.getLanguage() * 0.2)));
		mTextView
				.setTextSize(TypedValue.COMPLEX_UNIT_SP, fSize < 8 ? 8 : fSize);
		mTextView.setText(text);
		onUpdateLayout();
	}

	@Override
	protected void onCreate() {
		addView(mMainView, mLayoutParams);
		bViewAdded = false;
		addView(mTextView, mTextLayoutParams);
		iTimer = 0;
	}

	@Override
	protected void onRemove() {
		removeView(mMainView);
		bViewAdded = true;
		removeView(mTextView);
	}

	@Override
	protected void onUpdateLayout() {
		mLayoutParams.setWidth((int) dpToPx(280));
		mLayoutParams.setHeight((int) dpToPx(148.75f));
		int bitmapIndex = 0;

		if (iIconPositionX + (int) dpToPx(24) > getScreenWidth() / 2) {
			mLayoutParams.setX(iIconPositionX - mLayoutParams.getWidth());
		} else {
			mLayoutParams.setX(iIconPositionX + (int) dpToPx(48));
			bitmapIndex++;
		}
		if (iIconPositionY + (int) dpToPx(24) > getScreenHeight() / 2) {
			mLayoutParams.setY(iIconPositionY - mLayoutParams.getHeight());
		} else {
			mLayoutParams.setY(iIconPositionY + (int) dpToPx(48));
			bitmapIndex +=2;
		}

		mMainView.setImageBitmap(mBubbleBitmap[bitmapIndex]);

		mTextLayoutParams.setWidth(mLayoutParams.getWidth() - (int) dpToPx(35));
		mTextLayoutParams.setHeight(mLayoutParams.getHeight()
				- (int) dpToPx(28));
		mTextLayoutParams.setX(mLayoutParams.getX() + (int) dpToPx(17.5f));
		mTextLayoutParams.setY(mLayoutParams.getY() + (int) dpToPx(17.5f));
	}

	@Override
	public void onClick(View v) {
		remove();
	}

	public void update() {
		if (bCreated) {
			updateViewLayout(mMainView, mLayoutParams);
			updateViewLayout(mTextView, mTextLayoutParams);
		}
	}
}
