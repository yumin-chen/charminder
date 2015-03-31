/*
**  Class DarkButton
**  src/com/CharmySoft/charminder/activities/layout/DarkButton.java
*/
package com.CharmySoft.charminder.activities.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.other.C;
import com.CharmySoft.charminder.other.G;

public class DarkButton extends TextView {

	String sText;
	public DarkButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setBackgroundColor(C.COLOR_DARKBLUE);
		this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		this.setTextColor(C.COLOR_LIGHTBLUE);
		this.setGravity(Gravity.CENTER);
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.StringStyleable, 0, 0);
		sText = array.getString(R.styleable.StringStyleable_text);
		array.recycle();
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		this.setText(sText);
		this.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(C.COLOR_LIGHTBLUE_WD);
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(C.COLOR_DARKBLUE);
					break;
				}
				return false;
			}
			
		});
		this.setOnClickListener((OnClickListener) G.context);
	}
}
