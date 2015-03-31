/*
**  Class ActivityWrapper
**  src/com/CharmySoft/charminder/activities/layout/ActivityWrapper.java
*/
package com.CharmySoft.charminder.activities.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.CharmySoft.charminder.R;
import com.CharmySoft.charminder.base.ViewBase;
import com.CharmySoft.charminder.other.C;

public class ActivityWrapper extends LinearLayout {
	String sParentActivity;
	String sTitle;
	int iHeadMargin;
	int iBackgroundColor;

	public ActivityWrapper(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
		TypedArray array = getContext().obtainStyledAttributes(attrs,
				R.styleable.ActivityAttr, 0, 0);
		sTitle = array.getString(R.styleable.ActivityAttr_title_text);
		sParentActivity = array.getString(R.styleable.ActivityAttr_parent_activity);
		iHeadMargin = array.getDimensionPixelSize(R.styleable.ActivityAttr_head_margin,
				(int)ViewBase.dpToPx(4.5f));
		iBackgroundColor = array.getColor(R.styleable.ActivityAttr_background_color, C.COLOR_BLUE);
		array.recycle();
	}
	
	@Override
	protected void onFinishInflate() {
        View[] children = new View[getChildCount()];
		for(int i = 0; i < getChildCount(); i++){
            children[i] = getChildAt(i);
		}
        this.detachAllViewsFromParent();
        
		LayoutInflater.from(getContext()).inflate(R.layout.activity_wrapper, this,
				true);


		TitleBar tbar = ((TitleBar) findViewById(R.id.acvitity_title_bar));
		if(sTitle == null && sParentActivity == null){
			this.removeView(tbar);
		}else{
			if(sTitle != null)
				tbar.setTitleText(sTitle);
			if(sParentActivity != null){
				tbar.setParentActivity(sParentActivity);
			}
		}
		
		LinearLayout ll = ((LinearLayout) findViewById(R.id.acvitity_main_view));
		ll.setPadding(0, iHeadMargin, 0, 0);
		ll.setBackgroundColor(iBackgroundColor);
		for(int i = 0; i < children.length; i++){
            ll.addView(children[i]);
		}
	    super.onFinishInflate();

	}
}
