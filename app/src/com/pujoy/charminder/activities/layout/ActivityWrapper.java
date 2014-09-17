package com.pujoy.charminder.activities.layout;

import com.pujoy.charminder.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class ActivityWrapper extends LinearLayout {
	String sParentActivity;
	String sTitle;

	public ActivityWrapper(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
		TypedArray array = getContext().obtainStyledAttributes(attrs,
				R.styleable.ActivityAttr, 0, 0);
		sTitle = array.getString(R.styleable.ActivityAttr_title_text);
		sParentActivity = array.getString(R.styleable.ActivityAttr_parent_activity);
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
		for(int i = 0; i < children.length; i++){
            ll.addView(children[i]);
		}
	    super.onFinishInflate();

	}
}
