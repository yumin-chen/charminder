package com.pujoy.charminder.views;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.FloatingBase;
import com.pujoy.charminder.base.FloatingLayoutParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import static com.pujoy.charminder.MainActivity.con;
import static com.pujoy.charminder.MainActivity.iLang;

public class Bubble extends FloatingBase implements OnClickListener{
	ImageView mainView;
	TextView textView;
	public int iTimer;
	public int iIconPositionX; 
	public int iIconPositionY;
	private FloatingLayoutParams floatingTextLayoutParams;
	
	@Override
	protected void onInitialize(){
		mainView = new ImageView(con);
		mainView.setOnClickListener(this);
        textView = new TextView(con);
        textView.setTextColor(android.graphics.Color.rgb(228, 242, 254));
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(this);
        floatingTextLayoutParams = new FloatingLayoutParams();
	}
	
	public void setText(String text){
		float fSize = (float) (22 - iLang*2 - (Math.sqrt(text.length())/(1.4-iLang*0.2)));
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fSize < 8? 8: fSize);
		textView.setText(text);
		onUpdateLayout();
	}
	
	@Override
	protected void onCreate(){
		addView(mainView, layoutParams); 
		bViewAdded = false;
		addView(textView, floatingTextLayoutParams); 
		iTimer = 0;
	}
	
	@Override
	protected void onRemove(){
		removeView(mainView);
		bViewAdded = true;
		removeView(textView);
	}
	
	@Override
	protected void onUpdateLayout() {
		layoutParams.setWidth((int) dpToPx(280));
		layoutParams.setHeight((int) dpToPx(148.75f));  
		float fHorizontal;
		float fVertical;
		if (iIconPositionX + (int) dpToPx(24) > getScreenWidth()/2){
			layoutParams.setX(iIconPositionX - layoutParams.getWidth());
			fHorizontal = 1;
		}else{
			layoutParams.setX(iIconPositionX + (int) dpToPx(48));
			fHorizontal = -1;
		}
		if (iIconPositionY + (int) dpToPx(24) > getScreenHeight()/2){
			layoutParams.setY(iIconPositionY - layoutParams.getHeight());  
			fVertical = 1;
		}else{
			layoutParams.setY(iIconPositionY + (int) dpToPx(48));  
			fVertical = -1;
		}
		
		
		Matrix matrix = new Matrix();
		matrix.preScale(fHorizontal, fVertical);
		Bitmap src = BitmapFactory.decodeResource(con.getResources(), R.drawable.bubble);
		mainView.setImageBitmap(Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true));

        floatingTextLayoutParams.setWidth(layoutParams.getWidth() - (int) dpToPx(35));
        floatingTextLayoutParams.setHeight(layoutParams.getHeight() - (int) dpToPx(28));  
        floatingTextLayoutParams.setX(layoutParams.getX() + (int) dpToPx(17.5f));
        floatingTextLayoutParams.setY(layoutParams.getY() + (int) dpToPx(17.5f));  
	}
	
	@Override
	public void onClick(View v) {
		remove();
	}

	public void Update() {
		if(bCreated)
		{
		updateViewLayout(mainView, layoutParams);
		updateViewLayout(textView, floatingTextLayoutParams);
		}
	}
}
