package com.pujoy.charminder.views;

import static com.pujoy.charminder.MainActivity.con;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;
import com.pujoy.charminder.base.FloatingBase;

public class FloatingText extends FloatingBase{
	RelativeLayout mainView;
	TextView mTextView;
	public int iTimer;
	
	@Override
	protected void onInitialize() {
		onUpdateLayout();
		mainView = new RelativeLayout(con);
    	mTextView = new TextView(con);
    	mTextView.setGravity(Gravity.CENTER);
    	mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    	mTextView.setTextColor(Constants.COLOR_DARKBLUE);
    	mTextView.setBackgroundResource(R.drawable.light_back);
    	mTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
    			LayoutParams.WRAP_CONTENT));
    	mTextView.setMaxWidth(layoutParams.getWidth());
    	mTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				iTimer = 0;
				remove();
			}
    		
    	});
    	
    	mainView.addView(mTextView);
    	mainView.setGravity(Gravity.CENTER);
	}

	@Override
	protected void onCreate() {
		addView(mainView, layoutParams);
	}

	@Override
	protected void onRemove() {
		removeView(mainView);
	}

	@Override
	protected void onUpdateLayout() {
		layoutParams.setWidth((int) dpToPx(240));
		layoutParams.setHeight(LayoutParams.WRAP_CONTENT);  
		layoutParams.setX((getScreenWidth() - layoutParams.getWidth())/2);
		layoutParams.setY((int) ((getScreenHeight() - layoutParams.getHeight())/2 + dpToPx(240)/2 + dpToPx(48)));
		layoutParams.alpha = 0.85f;
	}
	
	public void setText(CharSequence text){
		mTextView.setText(text);
	}

}
