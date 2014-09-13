package com.pujoy.charminder.views;

import static com.pujoy.charminder.MainActivity.con;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;
import com.pujoy.charminder.base.FloatingBase;

public class FloatingText extends FloatingBase{
	TextView mainView;
	public int iTimer;
	
	@Override
	protected void onInitialize() {
		onUpdateLayout();
    	mainView = new TextView(con);
    	mainView.setGravity(Gravity.CENTER);
    	mainView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    	mainView.setTextColor(Constants.COLOR_DARKBLUE);
    	mainView.setBackgroundResource(R.drawable.light_back);
    	mainView.setLayoutParams(new LayoutParams((int)(layoutParams.getWidth() - dpToPx(32)),
    			LayoutParams.WRAP_CONTENT));
    	mainView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				iTimer = 0;
				remove();
			}
    		
    	});
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
		mainView.setText(text);
	}

}
