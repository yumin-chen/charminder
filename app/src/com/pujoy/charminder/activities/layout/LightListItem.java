package com.pujoy.charminder.activities.layout;

import com.pujoy.charminder.R;
import com.pujoy.charminder.other.C;
import com.pujoy.charminder.other.G;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LightListItem extends RelativeLayout {
	public LightListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.fragment_light_list_item,
				this, true);

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.StringStyleable, 0, 0);
		String text = array.getString(R.styleable.StringStyleable_text);
		if (text != null)
			((TextView) findViewById(R.id.light_list_item_text)).setText(text);
		array.recycle();

		this.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(C.COLOR_LIGHTBLUE_WD);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(C.COLOR_LIGHTBLUE);
					break;
				}
				return false;
			}
		});
		this.setOnClickListener((OnClickListener) G.context);

	}
}
