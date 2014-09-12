package com.pujoy.charminder.base;

import static com.pujoy.charminder.MainActivity.con;

import com.pujoy.charminder.Constants;
import com.pujoy.charminder.R;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class PopDialog extends FloatingDialog{
	
	FunctionWrapper mOnOkListener;
	FunctionWrapper mOnCancelListener;
	TextView tvTitle;
	TextView tvContent;
	
	public PopDialog(){
		super();
	}
	
	public PopDialog(String content){
		super();
		tvContent.setText(content);
		tvTitle.setText(con.getResources().getString(R.string.popDialog_defualtTitle));
	}
	
	public PopDialog(String content, String title){
		super();
		tvTitle.setText(title);
		tvContent.setText(content);
	}
	
	public PopDialog(String content, String title, FunctionWrapper onOK){
		super();
		tvTitle.setText(title);
		tvContent.setText(content);
		mOnOkListener = onOK;
	}
	
	public PopDialog(String content, String title, FunctionWrapper onOK, FunctionWrapper onCancel){
		super();
		mOnOkListener = onOK;
		mOnCancelListener = onCancel;
    	tvTitle.setText(title);
    	tvContent.setText(content);
	}
	
	
	public void setTitle(String title){
		tvTitle.setText(title);
	}
	
	public void setContent(String content){
		tvContent.setText(content);
	}
	
	public void setOnOkListener(FunctionWrapper onOK){
		mOnOkListener = onOK;
	}
	
	public void setOnCancelListener(FunctionWrapper onCancel){
		mOnCancelListener = onCancel;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		tvTitle = new TextView(con);
    	tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
    	tvTitle.setGravity(Gravity.CENTER);
    	tvTitle.setTextColor(Constants.COLOR_LIGHTBLUE);
    	tvTitle.setLayoutParams(new LayoutParams((int)dpToPx(128), (int)dpToPx(28)));
    	tvTitle.setX(layoutParams.getWidth()/2-dpToPx(128)/2);
    	addToMainView(tvTitle);

		tvContent = new TextView(con);
    	tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
    	tvContent.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP);
    	tvContent.setTextColor(Constants.COLOR_DARKBLUE);
    	tvContent.setLayoutParams(new LayoutParams((int)(layoutParams.getWidth() - dpToPx(24)),
    			LayoutParams.WRAP_CONTENT));
    	tvContent.setX(dpToPx(12));
    	tvContent.setY((layoutParams.getWidth() * NONCENTER_PROPORTION));
    	addToMainView(tvContent);
	}

	@Override
	protected void onCreate() {
		ValueAnimator aTitleY = ObjectAnimator.ofFloat(tvTitle, "y",
				layoutParams.getHeight()/2 - layoutParams.getWidth()/2 + dpToPx(24), dpToPx(24));
		aTitleY.setDuration(ANIMATION_DURATION);
		aTitleY.start();
		
	}

	@Override
	protected void onRemove() {
		ValueAnimator aTitleY = ObjectAnimator.ofFloat(tvTitle, "y",
				dpToPx(24), layoutParams.getHeight()/2 - layoutParams.getWidth()/2 + dpToPx(24));
		aTitleY.setDuration(ANIMATION_DURATION);
		aTitleY.start();
		
	}

	@Override
	protected void onUpdateLayout() {
    	layoutParams.setWidth((int) dpToPx(256));
    	layoutParams.setHeight((int) dpToPx(256));
        layoutParams.x = (getScreenWidth()-layoutParams.getWidth())/2;
        layoutParams.y = (getScreenHeight()-layoutParams.getHeight())/2;   
        layoutParams.alpha = 0.95f;
	}

	@Override
	protected void onOk() {
		if (mOnOkListener != null)
		mOnOkListener.function();
		
	}
	
	@Override
	protected void onCancel() {
		if (mOnCancelListener != null)
		mOnCancelListener.function();
		
	}
	

}
