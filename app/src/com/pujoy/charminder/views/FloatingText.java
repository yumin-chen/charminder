package com.pujoy.charminder.views;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pujoy.charminder.R;
import com.pujoy.charminder.base.WindowBase;
import com.pujoy.charminder.other.C;
import com.pujoy.charminder.other.G;

public class FloatingText extends WindowBase {
	RelativeLayout mMainView;
	TextView mTextView;
	public int iTimer;

	@Override
	protected void onInitialize() {
		onUpdateLayout();
		mMainView = new RelativeLayout(G.context);
		mTextView = new TextView(G.context);
		mTextView.setGravity(Gravity.CENTER);
		mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mTextView.setTextColor(C.COLOR_DARKBLUE);
		mTextView.setBackgroundResource(R.drawable.light_back);
		mTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		mTextView.setMaxWidth(mLayoutParams.getWidth());
		mTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iTimer = 0;
				remove();
			}

		});

		mMainView.addView(mTextView);
		mMainView.setGravity(Gravity.CENTER);
	}

	@Override
	protected void onCreate() {
		addView(mMainView, mLayoutParams);
	}

	@Override
	protected void onRemove() {
		removeView(mMainView);
	}

	@Override
	protected void onUpdateLayout() {
		mLayoutParams.setWidth((int) dpToPx(240));
		mLayoutParams.setHeight(LayoutParams.WRAP_CONTENT);
		mLayoutParams.setX((getScreenWidth() - mLayoutParams.getWidth()) / 2);
		mLayoutParams.setY((int) ((getScreenHeight() - mLayoutParams
				.getHeight()) / 2 + dpToPx(240) / 2 + dpToPx(48)));
		mLayoutParams.alpha = 0.85f;
	}

	public void setText(CharSequence text) {
		mTextView.setText(text);
	}

}