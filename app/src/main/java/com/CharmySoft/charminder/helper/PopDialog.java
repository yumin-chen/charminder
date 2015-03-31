/*
**  Class PopDialog
**  src/com/CharmySoft/charminder/helper/PopDialog.java
*/
package com.CharmySoft.charminder.helper;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.base.WindowDialog;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public class PopDialog extends WindowDialog {
	FunctionWrapper mOnOkListener;
	FunctionWrapper mOnCancelListener;
	TextView mTitle;
	String sContent;
	TextView mContent;

	public PopDialog() {
		super();
	}

	public PopDialog(String content) {
		super();
		checkInitialization();
		sContent = content;
		mContent.setText(content);
		mTitle.setText(G.context.getResources().getString(
				R.string.popDialog_defualtTitle));
	}

	public PopDialog(String content, String title) {
		super();
		checkInitialization();
		mTitle.setText(title);
		sContent = content;
		mContent.setText(content);
	}

	public PopDialog(String content, String title, FunctionWrapper onOK) {
		super();
		checkInitialization();
		mTitle.setText(title);
		sContent = content;
		mContent.setText(content);
		mOnOkListener = onOK;
	}

	public PopDialog(String content, String title, FunctionWrapper onOK,
			FunctionWrapper onCancel) {
		super();
		checkInitialization();
		mOnOkListener = onOK;
		mOnCancelListener = onCancel;
		mTitle.setText(title);
		sContent = content;
		mContent.setText(content);
	}

	public void setTitle(String title) {
		checkInitialization();
		mTitle.setText(title);
	}

	public void setContent(String content) {
		checkInitialization();
		sContent = content;
		mContent.setText(content);
	}

	public void setOnOkListener(FunctionWrapper onOK) {
		mOnOkListener = onOK;
	}

	public void setOnCancelListener(FunctionWrapper onCancel) {
		mOnCancelListener = onCancel;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		mTitle = new TextView(G.context);
		mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		mTitle.setGravity(Gravity.CENTER);
		mTitle.setTextColor(C.COLOR_LIGHTBLUE);
		mTitle.setLayoutParams(new LayoutParams((int) dpToPx(128),
				(int) dpToPx(28)));
		mTitle.setX(mLayoutParams.getWidth() / 2 - dpToPx(128) / 2);
		addToMainView(mTitle);

		mContent = new TextView(G.context);
		mContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		mContent.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
		mContent.setTextColor(C.COLOR_DARKBLUE);
		mContent.setLayoutParams(new LayoutParams((int) (mLayoutParams
				.getWidth() - dpToPx(24)), LayoutParams.WRAP_CONTENT));
		mContent.setX(dpToPx(12));
		mContent.setY((mLayoutParams.getWidth() * CENTER_PROPORTION));
		addToMainView(mContent);
	}
	
	@Override
	protected void onDestroy() {
		removeFromMainView(mTitle);
		mTitle = null;
		removeFromMainView(mContent);
		mContent = null;
		super.onDestroy();
	}

	@Override
	protected void onCreate() {
		ValueAnimator aTitleY = ObjectAnimator.ofFloat(mTitle, "y",
				mLayoutParams.getHeight() / 2 - mLayoutParams.getWidth() / 2
						+ dpToPx(24), dpToPx(24));
		aTitleY.setDuration(ANIMATION_DURATION);
		aTitleY.start();

	}

	@Override
	protected void onRemove() {
		ValueAnimator aTitleY = ObjectAnimator.ofFloat(mTitle, "y", dpToPx(24),
				mLayoutParams.getHeight() / 2 - mLayoutParams.getWidth() / 2
						+ dpToPx(24));
		aTitleY.setDuration(ANIMATION_DURATION);
		aTitleY.start();

	}

	@Override
	protected void onUpdateLayout() {
		super.onUpdateLayout();
		mLayoutParams.setWidth((int) dpToPx(256));
		int c = 0;
		if (sContent != null){
			if (G.getLanguage() == 0){
					c = sContent.length() - 72;
					if(c < 0)
					{
						c = 0;
					}
				
				c = ((c + 23) / 24);
			}else{
				c = sContent.length() - 12*3;
				if(c < 0)
				{
					c = 0;
				}
			
			c = ((c + 11) / 12);
			}
		}
		mLayoutParams.setHeight((int) dpToPx(256 + c * 18));
		mLayoutParams.x = (getScreenWidth() - mLayoutParams.getWidth()) / 2;
		mLayoutParams.y = (getScreenHeight() - mLayoutParams.getHeight()) / 2;
		mLayoutParams.alpha = 0.95f;
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
