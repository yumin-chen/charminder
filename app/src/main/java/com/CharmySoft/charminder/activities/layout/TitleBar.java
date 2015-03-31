/*
**  Class TitleBar
**  src/com/CharmySoft/charminder/activities/layout/TitleBar.java
*/
package com.CharmySoft.charminder.activities.layout;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public class TitleBar extends LinearLayout {
	static String sParentActivity;

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(HORIZONTAL);
		LayoutInflater.from(context).inflate(R.layout.fragment_title_bar, this,
				true);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.fragment_title_bar, this,
				true);
	}
	
	public void setTitleText(String text){
		((TextView) findViewById(R.id.fragment_title_bar_text))
				.setText(text);
	}
	
	public void setParentActivity(final String parentActivity){
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
						"com.CharmySoft.charminder", parentActivity));
				G.context.startActivity(intent);
			}

		});

	}
}
