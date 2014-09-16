package com.pujoy.charminder.activities.layout;

import com.pujoy.charminder.R;
import com.pujoy.charminder.other.C;
import com.pujoy.charminder.other.G;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TitleBar extends LinearLayout {
	static String sParentActivity;

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER);
		setWeightSum(1.0f);

		new Intent();

		LayoutInflater.from(context).inflate(R.layout.fragment_title_bar, this,
				true);

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.TitleBar, 0, 0);
		String text = array.getString(R.styleable.TitleBar_title_text);
		if (text != null)
			((TextView) findViewById(R.id.fragment_title_bar_text))
					.setText(text);

		sParentActivity = array.getString(R.styleable.TitleBar_parent_activity);
		if (sParentActivity != null) {
			ImageView backButton = (ImageView) findViewById(R.id.fragment_title_bar_back_button);
			backButton.setVisibility(VISIBLE);
			backButton.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent motionEvent) {
					switch (motionEvent.getAction()) {
					case MotionEvent.ACTION_DOWN:
						v.setBackgroundColor(C.COLOR_DARKBLUE_WL);
						break;
					case MotionEvent.ACTION_UP:
						v.setBackgroundColor(C.COLOR_TRANSPARENT);
						break;
					}
					return false;
				}
			});
			backButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setComponent(new ComponentName(
							"com.pujoy.charminder", sParentActivity));
					G.context.startActivity(intent);
				}

			});

		}

		array.recycle();
	}
}
