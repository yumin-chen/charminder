package com.pujoy.charminder.activities.layout;

import com.pujoy.charminder.R;
import com.pujoy.charminder.other.C;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class LightListView extends ScrollView {
	public LightListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.fragment_light_listview,
				this, true);

		this.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction()) {
				case MotionEvent.ACTION_UP:
					LightListView view = (LightListView) v;
					for (int i = 0; i < view.getChildCount(); i++) {
						View childView = view.getChildAt(i);
						if (childView != null) {
							ViewGroup childViewGroup = (ViewGroup) childView;
							for (int j = 0; j < childViewGroup.getChildCount(); j++) {
								View grandchildView = childViewGroup
										.getChildAt(j);
								if (grandchildView != null
										&& grandchildView instanceof LightListItem) {
									grandchildView
											.setBackgroundColor(C.COLOR_LIGHTBLUE);
								}

							}
						}

					}
					break;
				}
				return false;
			}
		});

	}
}
