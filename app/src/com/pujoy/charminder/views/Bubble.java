package com.pujoy.charminder.views;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.WindowBase;
import com.pujoy.charminder.base.WindowLayoutParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import static com.pujoy.charminder.MainActivity.mCon;
import static com.pujoy.charminder.MainActivity.iLang;

public class Bubble extends WindowBase implements OnClickListener{
	ImageView mMainView;
	TextView mTextView;
	public int iTimer;
	public int iIconPositionX; 
	public int iIconPositionY;
	private WindowLayoutParams mTextLayoutParams;
	
	@Override
	protected void onInitialize(){
		mMainView = new ImageView(mCon);
		mMainView.setOnClickListener(this);
        mTextView = new TextView(mCon);
        mTextView.setTextColor(android.graphics.Color.rgb(228, 242, 254));
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setOnClickListener(this);
        mTextLayoutParams = new WindowLayoutParams();
	}
	
	public void setText(String text){
		float fSize = (float) (22 - iLang*2 - (Math.sqrt(text.length())/(1.4-iLang*0.2)));
		mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fSize < 8? 8: fSize);
		mTextView.setText(text);
		onUpdateLayout();
	}
	
	@Override
	protected void onCreate(){
		addView(mMainView, mLayoutParams); 
		bViewAdded = false;
		addView(mTextView, mTextLayoutParams); 
		iTimer = 0;
	}
	
	@Override
	protected void onRemove(){
		removeView(mMainView);
		bViewAdded = true;
		removeView(mTextView);
	}
	
	@Override
	protected void onUpdateLayout() {
		mLayoutParams.setWidth((int) dpToPx(280));
		mLayoutParams.setHeight((int) dpToPx(148.75f));  
		float fHorizontal;
		float fVertical;
		if (iIconPositionX + (int) dpToPx(24) > getScreenWidth()/2){
			mLayoutParams.setX(iIconPositionX - mLayoutParams.getWidth());
			fHorizontal = 1;
		}else{
			mLayoutParams.setX(iIconPositionX + (int) dpToPx(48));
			fHorizontal = -1;
		}
		if (iIconPositionY + (int) dpToPx(24) > getScreenHeight()/2){
			mLayoutParams.setY(iIconPositionY - mLayoutParams.getHeight());  
			fVertical = 1;
		}else{
			mLayoutParams.setY(iIconPositionY + (int) dpToPx(48));  
			fVertical = -1;
		}
		
		
		Matrix matrix = new Matrix();
		matrix.preScale(fHorizontal, fVertical);
		Bitmap src = BitmapFactory.decodeResource(mCon.getResources(), R.drawable.bubble);
		mMainView.setImageBitmap(Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true));

        mTextLayoutParams.setWidth(mLayoutParams.getWidth() - (int) dpToPx(35));
        mTextLayoutParams.setHeight(mLayoutParams.getHeight() - (int) dpToPx(28));  
        mTextLayoutParams.setX(mLayoutParams.getX() + (int) dpToPx(17.5f));
        mTextLayoutParams.setY(mLayoutParams.getY() + (int) dpToPx(17.5f));  
	}
	
	@Override
	public void onClick(View v) {
		remove();
	}

	public void Update() {
		if(bCreated)
		{
		updateViewLayout(mMainView, mLayoutParams);
		updateViewLayout(mTextView, mTextLayoutParams);
		}
	}
}
