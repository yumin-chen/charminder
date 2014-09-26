package com.pujoy.charminder.helper;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.WindowDialog;
import com.pujoy.charminder.helper.FunctionWrapper;
import com.pujoy.charminder.other.C;
import com.pujoy.charminder.other.G;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class PopDialog extends WindowDialog {
	FunctionWrapper mOnOkListener;
	FunctionWrapper mOnCancelListener;
	TextView mTitle;
	TextView mContent;

	public PopDialog() {
		super();
	}

	public PopDialog(String content) {
		super();
		mContent.setText(content);
		mTitle.setText(G.context.getResources().getString(
				R.string.popDialog_defualtTitle));
	}

	public PopDialog(String content, String title) {
		super();
		mTitle.setText(title);
		mContent.setText(content);
	}

	public PopDialog(String content, String title, FunctionWrapper onOK) {
		super();
		mTitle.setText(title);
		mContent.setText(content);
		mOnOkListener = onOK;
	}

	public PopDialog(String content, String title, FunctionWrapper onOK,
			FunctionWrapper onCancel) {
		super();
		mOnOkListener = onOK;
		mOnCancelListener = onCancel;
		mTitle.setText(title);
		mContent.setText(content);
	}

	public void setTitle(String title) {
		mTitle.setText(title);
	}

	public void setContent(String content) {
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
		mLayoutParams.setWidth((int) dpToPx(256));
		mLayoutParams.setHeight((int) dpToPx(256));
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
