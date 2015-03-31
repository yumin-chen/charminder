/*
**  Class Bubble
**  src/com/CharmySoft/charminder/view/Bubble.java
*/
package com.CharmySoft.charminder.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.base.WindowBase;
import com.CharmySoft.charminder.base.WindowLayoutParams;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public class Bubble extends WindowBase implements OnClickListener {
	ImageView mMainView;
	TextView mTextView;
	public int iTimer;
	public int iIconPositionX;
	public int iIconPositionY;
	private int iCachedIndex;
	private Bitmap mCachedBitmap;
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
	}
	
	private Bitmap initializeBubbleBitmap(int index){
		Bitmap b = BitmapFactory.decodeResource(G.context.getResources(),
				R.drawable.bubble);
		Matrix matrix = new Matrix();
		switch (index){
		case 0:
			return b;
		case 1:
			matrix.preScale(-1, 1);
			return Bitmap.createBitmap(b, 0, 0, b.getWidth(),
					b.getHeight(), matrix, true);
		case 2:
			matrix.preScale(1, -1);
			return Bitmap.createBitmap(b, 0, 0, b.getWidth(),
					b.getHeight(), matrix, true);
		case 3:
			matrix.preScale(-1, -1);
			return Bitmap.createBitmap(b, 0, 0, b.getWidth(),
					b.getHeight(), matrix, true);
		}
		return null;
	}
	
	@Override
	protected void onDestroy() {
		mTextLayoutParams = null;
		mMainView.setImageBitmap(null);
		mMainView = null;
		mTextView = null;
		mCachedBitmap.recycle();
		mCachedBitmap = null;
		iCachedIndex = 0;
		super.onDestroy();
	}
	

	public void setText(String text) {
		checkInitialization();
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
		super.onUpdateLayout();
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
		
		// If cache hits
		if(iCachedIndex == bitmapIndex + 1){
			mMainView.setImageBitmap(mCachedBitmap);
		}else{ // If cache misses
			iCachedIndex = bitmapIndex + 1;
			mCachedBitmap = initializeBubbleBitmap(bitmapIndex);
			mMainView.setImageBitmap(mCachedBitmap);
		}

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
